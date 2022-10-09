package ru.vtb.clientrestmicroservice.service;

import ru.vtb.clientrestmicroservice.dto.outputmessages.ExchangeCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.PurchaseCompletedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.TransactionStatusChangedEventDto;
import ru.vtb.clientrestmicroservice.dto.outputmessages.TransferCompletedEventDto;


public interface UserTransferRabbitService {
    void sendToGamificationManagement(ExchangeCompletedEventDto exchangeCompletedEventDto);
    void sendToGamificationManagement(PurchaseCompletedEventDto purchaseCompletedEventDto);
    void sendToGamificationManagement(TransactionStatusChangedEventDto transactionStatusChangedEventDto);
    void sendToGamificationManagement(TransferCompletedEventDto transferCompletedEventDto);
}
