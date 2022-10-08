package ru.vtb.clientrestmicroservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.output.OutExchangeDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.TransactionDto;

import java.util.List;

@Service
public interface TransactionService {
    OutTransferDto doTransfer(TransferDto transferDto);
    OutExchangeDto doExchange(ExchangeDto exchangeDto);
    String getStatus(String hash);
    void analyseTransaction(TransactionMessageEventDto transactionMessageEventDto);
    void analyseExchange();
    List<TransactionDto> getTransfersByWalletId(Long id, Pageable pageable);

}
