package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiResultHash;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.Direction;
import ru.vtb.clientrestmicroservice.dto.output.OutExchangeDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.TransactionDto;
import ru.vtb.clientrestmicroservice.entity.transaction.Currency;
import ru.vtb.clientrestmicroservice.entity.transaction.Transaction;
import ru.vtb.clientrestmicroservice.entity.transaction.Wallet;
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
                    return OutTransferDto.builder()
                            .id(transaction.getId())
                            .hash(hash)
                            .build();
                }else{
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Response transaction hash is null");
                }
            }catch (RestClientException restClientException){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error while requesting for transfer");
            }
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
