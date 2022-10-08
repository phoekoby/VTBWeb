package ru.vtb.clientrestmicroservice.service;

import ru.vtb.clientrestmicroservice.dto.message.ExchangeMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.PurchaseMessageEventDto;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;

public interface TransactionRabbitService {
    void sendToControl(TransactionMessageEventDto transactionMessageEventDto);
    void sendToControl(ExchangeMessageEventDto exchangeMessageEventDto);
    void sendToControl(PurchaseMessageEventDto purchaseMessageEventDto);
}
