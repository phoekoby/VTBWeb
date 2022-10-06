package ru.vtb.clientrestmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.TransactionStatusChangedEventDto;

@Component
@RequiredArgsConstructor
public class WalletBalanceChangedConsumer {


    @SneakyThrows
    @RabbitListener(
            id = "WalletBalanceChangedMessageListener",
            queues = "ru.vtb.qu.transaction.status.changed",
            containerFactory = "proactiveMessageListenerContainer"
    )
    public void onMessage(TransactionStatusChangedEventDto transactionStatusChangedEventDto){
        //как то уведомлять пользователя
    }
}
