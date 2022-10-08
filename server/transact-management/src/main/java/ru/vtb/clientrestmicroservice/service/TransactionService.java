package ru.vtb.clientrestmicroservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.input.ExchangeDto;
import ru.vtb.clientrestmicroservice.dto.input.PurchaseDto;
import ru.vtb.clientrestmicroservice.dto.input.TransferDto;
import ru.vtb.clientrestmicroservice.dto.message.ExchangeMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.PurchaseMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransactionDto;
import ru.vtb.clientrestmicroservice.dto.output.OutTransferDto;
import ru.vtb.clientrestmicroservice.dto.output.TransactionDto;

import java.util.List;

@Service
public interface TransactionService {
    OutTransferDto doTransfer(TransferDto transferDto);
    OutTransactionDto doExchange(ExchangeDto exchangeDto);
    OutTransactionDto doPurchase(PurchaseDto purchaseDto);
    String getStatus(String hash);
    void checkTransaction(TransactionMessageEventDto transactionMessageEventDto);
    void checkExchange(ExchangeMessageEventDto exchangeMessageEventDto);
    void checkPurchase(PurchaseMessageEventDto purchaseMessageEventDto);
    List<TransactionDto> getTransfersByWalletId(Long id, Pageable pageable);

}
