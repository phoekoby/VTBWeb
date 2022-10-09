package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {
    private final TransactionService transactionService;

    @SneakyThrows
    @RabbitListener(
            id="TransactionMessageListener",
            queues = "ru.vtb.qu.transaction.transaction",
            containerFactory = "transactionMessageListener"
    )
    public void onMessage(TransactionMessageEventDto transactionMessageEventDto){
        transactionService.checkTransaction(transactionMessageEventDto);
    }
}
