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
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.TransactionDto;
import ru.vtb.clientrestmicroservice.entity.*;
import ru.vtb.clientrestmicroservice.repository.ExchangeRepository;
import ru.vtb.clientrestmicroservice.repository.PurchaseRepository;
import ru.vtb.clientrestmicroservice.repository.TransactionRepository;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.TransactionRabbitService;
import ru.vtb.clientrestmicroservice.service.TransactionService;

import java.util.List;
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
        if(!forEntity.hasBody() || forEntity.getBody() == null || StringUtils.isBlank(forEntity.getBody().getStatus())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error while transaction status requesting");
        }
        return forEntity.getBody().getStatus();
    }

    @Async
    @Override
    public void checkTransaction(TransactionMessageEventDto transactionMessageEventDto) {

    }

    @Async
    @Override
    public void checkExchange(ExchangeMessageEventDto exchangeMessageEventDto) {

    }

    @Async
    @Override
    public void checkPurchase(PurchaseMessageEventDto purchaseMessageEventDto) {

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
