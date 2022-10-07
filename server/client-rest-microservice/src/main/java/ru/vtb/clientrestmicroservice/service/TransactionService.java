package ru.vtb.clientrestmicroservice.service;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiBalanceDto;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;

@Service
public interface TransactionService {
    String doTransfer(TransferDto transferDto);
    String doExchange(ExchangeDto exchangeDto);


}
