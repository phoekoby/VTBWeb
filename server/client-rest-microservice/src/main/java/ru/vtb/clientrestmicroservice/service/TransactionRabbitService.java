package ru.vtb.clientrestmicroservice.service;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;

@Service
public interface TransactionRabbitService {
    void sendToControl(TransactionMessageEventDto transactionMessageEventDto);
}
