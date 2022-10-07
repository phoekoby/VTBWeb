package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.TransferEventDto;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;

@Component
@RequiredArgsConstructor
public class TransferConsumer {
    private final EventService eventService;
    private final CustomISet<Long> userSet;

    @SneakyThrows
    @RabbitListener(
            id = "TransferEventMessageListener",
            queues = "ru.vtb.qu.rpc.event.transfer",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(TransferEventDto transferEventDto){
        if(!userSet.add(transferEventDto.getFromUserId())){
            //подождать
        }
        if(!userSet.add(transferEventDto.getToUserId())){
            //подождать
            userSet.delete(transferEventDto.getFromUserId());
        }

        try{
            eventService.doTransfer(transferEventDto.getTransactionId());
        }catch (Throwable e){
            //отправить в очередь ошибок
        }finally {
            userSet.delete(transferEventDto.getFromUserId());
            userSet.delete(transferEventDto.getToUserId());
        }
    }
}
