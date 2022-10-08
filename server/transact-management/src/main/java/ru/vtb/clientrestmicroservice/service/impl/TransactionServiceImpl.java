package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.OutExchangeDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.entity.Currency;
import ru.vtb.clientrestmicroservice.entity.Transaction;
import ru.vtb.clientrestmicroservice.entity.Wallet;
import ru.vtb.clientrestmicroservice.repository.TransactionRepository;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.TransactionRabbitService;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestTemplate restTemplate;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionRabbitService transactionRabbitService;
    @Value("${base.url}")
    private String baseUrl;



    @Override
    public OutTransferDto doTransfer(TransferDto transferDto) {
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
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ApiTransferDto> httpEntity = new HttpEntity<>(apiTransferDto, httpHeaders);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
            String hash = stringResponseEntity.getBody();
            Transaction transaction = new Transaction();
            transaction.setTransactionHash(hash);
            transaction.setFromWallet(fromWallet);
            transaction.setToWallet(toWallet);
            transactionRepository.save(transaction);
            transactionRabbitService.sendToControl(TransactionMessageEventDto.builder()
                    .transactionId(transaction.getId())
                    .hash(hash)
                    .build());
            return OutTransferDto.builder()
                    .id(transaction.getId())
                    .hash(hash)
                    .build();
        }
        return null;
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
    public OutExchangeDto doExchange(ExchangeDto exchangeDto) {
        return null;
    }

    @Override
    public String getStatus(String hash) {
        return null;
    }

    @Override
    public void analyseTransaction(TransactionMessageEventDto transactionMessageEventDto) {

    }

    @Override
    public void analyseExchange() {

    }


}
