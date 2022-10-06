package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.TransactionEventDto;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {
    private final EventService eventService;
    private final CustomISet<Long> userSet;

    @SneakyThrows
    @RabbitListener(
            id = "TransactionEventMessageListener",
            queues = "ru.vtb.qu.rpc.event.transaction",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(TransactionEventDto transactionEventDto){
        if(!userSet.add(transactionEventDto.getFromUserId())){
            //подождать
        }
        if(!userSet.add(transactionEventDto.getToUserId())){
            //подождать
            userSet.delete(transactionEventDto.getFromUserId());
        }

        try{
            eventService.doTransaction(transactionEventDto.getTransactionId());
        }catch (Throwable e){
            //отправить в очередь ошибок
        }finally {
            userSet.delete(transactionEventDto.getFromUserId());
            userSet.delete(transactionEventDto.getToUserId());
        }
    }
}
