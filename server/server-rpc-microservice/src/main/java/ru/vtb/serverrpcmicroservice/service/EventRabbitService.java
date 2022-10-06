package ru.vtb.serverrpcmicroservice.service;

import org.springframework.stereotype.Service;
import ru.vtb.integrationmodule.events.TransactionStatusChangedEventDto;
import ru.vtb.integrationmodule.events.WalletBalanceChangedEventDto;

@Service
public interface EventRabbitService {
    void sendTransactionStatusChanged(TransactionStatusChangedEventDto transactionStatusChangedEventDto);
    void sendWalletBalanceChanged(WalletBalanceChangedEventDto walletBalanceChangedEventDto);
}
