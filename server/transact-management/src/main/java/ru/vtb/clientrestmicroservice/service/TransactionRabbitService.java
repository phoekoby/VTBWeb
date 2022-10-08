package ru.vtb.clientrestmicroservice.service;

import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;

public interface TransactionRabbitService {
    void sendToControl(TransactionMessageEventDto transactionMessageEventDto);
}
