package ru.vtb.clientrestmicroservice.service.impl;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.outputmessages.ExchangeCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.PurchaseCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.TransactionStatusChangedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.TransferCompletedEventDto;
import ru.vtb.clientrestmicroservice.service.UserTransferRabbitService;

@Service
public class UserTransferRabbitServiceImpl implements UserTransferRabbitService {
    @Override
    public void sendToGamificationManagement(ExchangeCompletedEventDto exchangeCompletedEventDto) {

    }

    @Override
    public void sendToGamificationManagement(PurchaseCompletedEventDto purchaseCompletedEventDto) {

    }

    @Override
    public void sendToGamificationManagement(TransactionStatusChangedEventDto transactionStatusChangedEventDto) {

    }

    @Override
    public void sendToGamificationManagement(TransferCompletedEventDto transferCompletedEventDto) {

    }
}
