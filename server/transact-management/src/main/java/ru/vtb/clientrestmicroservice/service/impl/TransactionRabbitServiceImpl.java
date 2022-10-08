package ru.vtb.clientrestmicroservice.service.impl;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.service.TransactionRabbitService;

@Service
public class TransactionRabbitServiceImpl implements TransactionRabbitService {
    @Override
    public void sendToControl(TransactionMessageEventDto transactionMessageEventDto) {

    }
}
