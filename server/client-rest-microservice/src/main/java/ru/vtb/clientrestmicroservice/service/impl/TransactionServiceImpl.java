package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.Currency;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionType;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiTransferDto;
import ru.vtb.clientrestmicroservice.entity.Transaction;
import ru.vtb.clientrestmicroservice.entity.Wallet;
import ru.vtb.clientrestmicroservice.repository.TransactionRepository;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.TransactionRabbitService;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestTemplate restTemplate;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionRabbitService transactionRabbitService;
    @Value("${base.url}")
    private String baseUrl;



    @Override
    public String doTransfer(TransferDto transferDto) {
        Wallet fromWallet = walletRepository.findById(transferDto.getFromId()).orElse(null);
        Wallet toWallet = walletRepository.findById(transferDto.getToId()).orElse(null);
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
            HttpEntity<String> httpEntity = new HttpEntity<>(apiTransferDto.toString(), httpHeaders);
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
                    .transactionType(TransactionType.TRANSFER)
                    .build());
            return hash;
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
                return baseUrl + "v1/transfers/nft";
        }
    }

    @Override
    public String doExchange(ExchangeDto exchangeDto) {
        return null;
    }

    @Override
    public String getStatus(String hash) {
        return null;
    }

    @Override
    public void analyseTransaction(TransactionMessageEventDto transactionMessageEventDto) {

    }


}
