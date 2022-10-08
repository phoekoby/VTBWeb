package ru.vtb.clientrestmicroservice.service;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;

@Service
public interface TransactionService {
    String doTransfer(TransferDto transferDto);
    String doExchange(ExchangeDto exchangeDto);
    String getStatus(String hash);
    void analyseTransaction(TransactionMessageEventDto transactionMessageEventDto);
    void analyseExchange();

}
