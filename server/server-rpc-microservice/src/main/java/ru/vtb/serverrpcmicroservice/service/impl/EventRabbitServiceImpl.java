package ru.vtb.serverrpcmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.integrationmodule.events.TransactionStatusChangedEventDto;
import ru.vtb.integrationmodule.events.WalletBalanceChangedEventDto;
import ru.vtb.serverrpcmicroservice.service.EventRabbitService;

@Service
@RequiredArgsConstructor
public class EventRabbitServiceImpl implements EventRabbitService {
    @Override
    public void sendTransactionStatusChanged(TransactionStatusChangedEventDto transactionStatusChangedEventDto) {

    }

    @Override
    public void sendWalletBalanceChanged(WalletBalanceChangedEventDto walletBalanceChangedEventDto) {

    }
}
