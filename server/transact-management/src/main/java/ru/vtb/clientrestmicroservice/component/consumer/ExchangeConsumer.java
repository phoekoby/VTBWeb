package ru.vtb.clientrestmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Component
@RequiredArgsConstructor
public class ExchangeConsumer {
    private final TransactionService transactionService;

    @RabbitListener(
            id="ExchangeTransferListener",
            queues = "ru.vtb.qu.transfer.exchange",
            containerFactory = "transferMessageListener"
    )
    public void onMessage(TransactionMessageEventDto transactionMessageEventDto){
       transactionService.checkTransaction(transactionMessageEventDto);
    }
}
