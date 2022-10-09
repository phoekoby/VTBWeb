package ru.vtb.clientrestmicroservice.service.impl;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.PurchaseDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.ExchangeMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.PurchaseMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiResultHash;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiResultStatus;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.Direction;
import ru.vtb.clientrestmicroservice.dto.output.OutTransactionDto;
import ru.vtb.clientrestmicroservice.dto.output.TransactionDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.ExchangeCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.PurchaseCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.TransactionStatusChangedEventDto;
import ru.vtb.clientrestmicroservice.entity.*;
import ru.vtb.clientrestmicroservice.entity.enumiration.Currency;
import ru.vtb.clientrestmicroservice.repository.ExchangeRepository;
import ru.vtb.clientrestmicroservice.repository.PurchaseRepository;
import ru.vtb.clientrestmicroservice.repository.TransactionRepository;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.TransactionRabbitService;
import ru.vtb.clientrestmicroservice.service.TransactionService;
import ru.vtb.clientrestmicroservice.service.UserTransferRabbitService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestTemplate restTemplate;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionRabbitService transactionRabbitService;
    private final ExchangeRepository exchangeRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserTransferRabbitService userTransferRabbitService;
    @Value("${base.url}")
    private String baseUrl;
    private static final Long COMPANY_WALLET_ID = 1L;


    private Transaction doTransferPrivate(TransferDto transferDto){
        Wallet fromWallet = walletRepository.findById(transferDto.getFromWalletId()).orElse(null);
        Wallet toWallet = walletRepository.findById(transferDto.getToWalletId()).orElse(null);
        if(fromWallet != null && toWallet != null) {
            String fromPrivateKey = fromWallet.getPrivateKey();
            String toPublicKey = toWallet.getPublicKey();
            Double amount = transferDto.getAmount();

            ApiTransferDto apiTransferDto = ApiTransferDto.builder()
                    .amount(amount)
                    .fromPrivateKey(fromPrivateKey)
                    .toPublicKey(toPublicKey)
                    .build();
            String url = createUrl(transferDto.getCurrency());
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<ApiTransferDto> httpEntity = new HttpEntity<>(apiTransferDto, httpHeaders);
            try {
                ResponseEntity<ApiResultHash> stringResponseEntity = restTemplate.postForEntity(url, httpEntity, ApiResultHash.class);
                if(stringResponseEntity.hasBody() && stringResponseEntity.getBody() != null){
                    String hash = stringResponseEntity.getBody().getTransactionHash();
                    Transaction transaction = new Transaction();
                    transaction.setTransactionHash(hash);
                    transaction.setFromWallet(fromWallet);
                    transaction.setToWallet(toWallet);
                    transaction.setTransactionStatus(TransactionStatus.PROCESSING);
                    transaction.setTransactionType(TransactionType.TRANSFER);
                    transactionRepository.save(transaction);
                    transactionRabbitService.sendToControl(TransactionMessageEventDto.builder()
                            .transactionId(transaction.getId())
                            .hash(hash)
                            .build());
                    return transaction;
                }else{
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Response transaction hash is null");
                }
            }catch (RestClientException restClientException){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error while requesting for transfer");
            }
        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wallets were not found");
    }

    @Override
    public OutTransactionDto doTransfer(TransferDto transferDto) {
        Transaction transaction = doTransferPrivate(transferDto);
        return OutTransactionDto.builder()
                .hash(transaction.getTransactionHash())
                .id(transaction.getId())
                .build();
    }


    private String createUrl(Currency currency){
        switch (currency){
            case RUBLE:
                return baseUrl + "transfers/ruble";
            case MATIC:
                return baseUrl + "transfers/matic";
            default:
                return baseUrl + "transfers/nft";
        }
    }

    @Override
    public OutTransactionDto doExchange(ExchangeDto exchangeDto) {
        TransferDto firstTransfer = TransferDto.builder()
                .amount(exchangeDto.getAmount())
                .currency(exchangeDto.getFrom())
                .fromWalletId(exchangeDto.getFromWalletId())
                .toWalletId(COMPANY_WALLET_ID)
                .build();
        Transaction transaction = doTransferPrivate(firstTransfer);
        transaction.setTransactionType(TransactionType.EXCHANGE);
        Exchange exchange = new Exchange();
        exchange.setOutTransaction(transaction);
        exchange.setCurrencyFrom(exchangeDto.getFrom());
        exchange.setCurrencyTo(exchangeDto.getTo());
        exchangeRepository.save(exchange);
        transactionRabbitService.sendToControl(ExchangeMessageEventDto.builder()
                .exchangeId(exchange.getId())
                .transactionFrom(transaction.getId())
                .build());
        return OutTransactionDto.builder()
                .hash(transaction.getTransactionHash())
                .id(transaction.getId())
                .build();
    }

    @Override
    public OutTransactionDto doPurchase(PurchaseDto purchaseDto) {
        TransferDto transferDto = TransferDto.builder()
                .toWalletId(purchaseDto.getToWalletId())
                .fromWalletId(purchaseDto.getFromWalletId())
                .currency(purchaseDto.getCurrency())
                .amount(purchaseDto.getCost())
                .build();
        Transaction transaction = doTransferPrivate(transferDto);
        transaction.setTransactionType(TransactionType.PURCHASE);
        Purchase purchase = new Purchase();
        purchase.setTransaction(transaction);
        purchase.setBuyerUser(transaction.getFromWallet().getUserAccount());
        purchase.setProductId(purchase.getProductId());
        purchase.setPrevOwnerUser(transaction.getToWallet().getUserAccount());
        purchase.setCost(purchase.getCost());
        purchaseRepository.save(purchase);
        transactionRabbitService.sendToControl(PurchaseMessageEventDto.builder()
                .purchaseId(purchase.getId())
                .build());
        return OutTransactionDto.builder()
                .id(transaction.getId())
                .hash(transaction.getTransactionHash())
                .build();
    }

    @Override
    public String getStatus(String hash) {
        String needUrl = baseUrl + "transfers/status/" + hash;
        ResponseEntity<ApiResultStatus> forEntity = restTemplate.getForEntity(needUrl, ApiResultStatus.class);
        if(forEntity.getStatusCode().equals(HttpStatus.NOT_FOUND) || !forEntity.hasBody() || forEntity.getBody() == null || StringUtils.isBlank(forEntity.getBody().getStatus())){
            return "NOT_FOUND";
        }
        return forEntity.getBody().getStatus();
    }

    @Async
    @Override
    public void checkTransaction(TransactionMessageEventDto transactionMessageEventDto) {
        Long transactionId = transactionMessageEventDto.getTransactionId();
        Optional<Transaction> byId = transactionRepository.findById(transactionId);
        if(byId.isPresent()){
            Transaction transaction = byId.get();
            if(!transaction.getTransactionStatus().equals(TransactionStatus.PROCESSING)){
                String currentStatus = getStatus(transaction.getTransactionHash());
                if("Success".equals(currentStatus) || "NOT_FOUND".equals(currentStatus)){
                    transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                    userTransferRabbitService.sendToGamificationManagement(TransactionStatusChangedEventDto.builder()
                            .transactionId(transactionId)
                            .currTransactionStatus(TransactionStatus.COMPLETED)
                            .prevTransactionStatus(TransactionStatus.PROCESSING)
                            .build());
                }else if("Error".equals(currentStatus)){
                    transaction.setTransactionStatus(TransactionStatus.ERROR);
                    userTransferRabbitService.sendToGamificationManagement(TransactionStatusChangedEventDto.builder()
                            .transactionId(transactionId)
                            .currTransactionStatus(TransactionStatus.PROCESSING)
                            .prevTransactionStatus(TransactionStatus.ERROR)
                            .build());
                } else if ("Padding".equals(currentStatus)) {
                    LocalDateTime createDate = transaction.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if(createDate.plusDays(1).isBefore(LocalDateTime.now())){
                        transactionRabbitService.sendToControl(transactionMessageEventDto);
                    }else{
                        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                        userTransferRabbitService.sendToGamificationManagement(TransactionStatusChangedEventDto.builder()
                                .transactionId(transactionId)
                                .currTransactionStatus(TransactionStatus.COMPLETED)
                                .prevTransactionStatus(TransactionStatus.PROCESSING)
                                .build());
                    }
                }
                transactionRepository.save(transaction);
            }
            transactionRepository.save(transaction);
        }
    }

    @Async
    @Override
    public void checkExchange(ExchangeMessageEventDto exchangeMessageEventDto) {
        Long exchangeId = exchangeMessageEventDto.getExchangeId();
        Exchange exchange = exchangeRepository.getReferenceById(exchangeId);
        Long fromTransactionId = exchangeMessageEventDto.getTransactionFrom();
        Long toTransactionId = exchangeMessageEventDto.getTransactionTo();
        if (toTransactionId == null) {
            //check the first one
            Transaction fromTransaction = transactionRepository.getReferenceById(fromTransactionId);
            if (fromTransaction.getTransactionStatus().equals(TransactionStatus.PROCESSING)) {
                String currentStatus = getStatus(fromTransaction.getTransactionHash());
                if ("Success".equals(currentStatus) || "NOT_FOUND".equals(currentStatus)) {
                    fromTransaction.setTransactionStatus(TransactionStatus.COMPLETED);
                    userTransferRabbitService.sendToGamificationManagement(TransactionStatusChangedEventDto.builder()
                            .transactionId(fromTransactionId)
                            .currTransactionStatus(TransactionStatus.COMPLETED)
                            .prevTransactionStatus(TransactionStatus.PROCESSING)
                            .build());
                    Transaction transaction = doTransferPrivate(TransferDto.builder()
                            .amount(exchange.getInTransaction().getAmount())
                            .currency(exchange.getCurrencyTo())
                            .currency(exchange.getCurrencyTo())
                            .fromWalletId(COMPANY_WALLET_ID).build());
                    transaction.setTransactionType(TransactionType.EXCHANGE);
                    exchange.setOutTransaction(transaction);
                    exchangeRepository.save(exchange);
                } else {
                    LocalDateTime createDate = fromTransaction.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if (createDate.plusDays(1).isBefore(LocalDateTime.now())) {
                        transactionRabbitService.sendToControl(exchangeMessageEventDto);
                    }else {
                        fromTransaction.setTransactionStatus(TransactionStatus.ERROR);
                    }
                }
            }
        }else{
            Transaction transactionTo = transactionRepository.getReferenceById(toTransactionId);
            String currentStatus = getStatus(transactionTo.getTransactionHash());
            if(transactionTo.getTransactionStatus().equals(TransactionStatus.PROCESSING)){
                if ("Success".equals(currentStatus) || "NOT_FOUND".equals(currentStatus)) {
                    transactionTo.setTransactionStatus(TransactionStatus.COMPLETED);
                    userTransferRabbitService.sendToGamificationManagement(ExchangeCompletedEventDto.builder()
                            .fromCurrency(exchange.getCurrencyFrom())
                            .toCurrency(exchange.getCurrencyTo())
                                    .amount(exchange.getInTransaction().getAmount())
                                    .userId(exchange.getInTransaction().getFromWallet().getUserAccount().getUserId())
                            .build());
                }else{
                    LocalDateTime createDate = transactionTo.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if (createDate.plusDays(1).isBefore(LocalDateTime.now())) {
                        transactionRabbitService.sendToControl(exchangeMessageEventDto);
                    }else {
                        transactionTo.setTransactionStatus(TransactionStatus.ERROR);
                    }
                }
            }
        }
        exchangeRepository.save(exchange);
    }

    @Async
    @Override
    public void checkPurchase(PurchaseMessageEventDto purchaseMessageEventDto) {
        Long purchaseId = purchaseMessageEventDto.getPurchaseId();
        Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
        Transaction transaction = purchase.getTransaction();
        if(transaction.getTransactionStatus().equals(TransactionStatus.PROCESSING)){
            String currentStatus = getStatus(transaction.getTransactionHash());
            if ("Success".equals(currentStatus) || "NOT_FOUND".equals(currentStatus)) {
                transaction.setTransactionStatus(TransactionStatus.COMPLETED);
                userTransferRabbitService.sendToGamificationManagement(PurchaseCompletedEventDto.builder()
                        .purchaseCost(purchase.getCost())
                        .productId(purchase.getProductId())
                        .userId(purchase.getBuyerUser().getUserId())
                        .build());
            }else{
                LocalDateTime createDate = transaction.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (createDate.plusDays(1).isBefore(LocalDateTime.now())) {
                    transactionRabbitService.sendToControl(purchaseMessageEventDto);
                }else {
                    transaction.setTransactionStatus(TransactionStatus.ERROR);
                }
            }
        }
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getTransfersByWalletId(Long id, Pageable pageable) {
        return transactionRepository.getAllByWalletId(pageable, id).stream().map(pa -> TransactionDto.builder()
                .amount(pa.getAmount())
                .direction(pa.getToWallet().getId().equals(id) ? Direction.INCOMING : Direction.OUTGOING)
                .fromId(pa.getFromWallet().getId())
                .toId(pa.getToWallet().getId())
                .transactionHash(pa.getTransactionHash())
                .transactionType(pa.getTransactionType())
                .currency(pa.getCurrency())
                .build()).collect(Collectors.toList());
    }


}
