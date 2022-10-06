package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.WalletBalanceChangedEventDto;

@Component
@RequiredArgsConstructor
public class TransactionStatusChangedConsumer {

    @SneakyThrows
    @RabbitListener(
            id = "WalletBalanceChangedMessageListener",
            queues = "ru.vtb.qu.event.transaction.status.changed",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(WalletBalanceChangedEventDto walletBalanceChangedEventDto){
        //как то уведомлять пользователя
    }

}
