package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.PurchaseEventDto;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;

@Component
@RequiredArgsConstructor
public class PurchaseConsumer {
    private final CustomISet<Long> userSet;
    private final CustomISet<Long> productSet;
    private final EventService eventService;

    @SneakyThrows
    @RabbitListener(
            id = "PurchaseEventMessageListener",
            queues = "ru.vtb.qu.rpc.event.purchase",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(PurchaseEventDto purchaseEventDto){
        //подумать какой из юзеров не может обрабатываться одновременно
        if(!userSet.add(purchaseEventDto.getBuyerUserId())){
            //не можем обрабатывать асинхронно, подождать
            return;
        }
        if(!userSet.add(purchaseEventDto.getOwnerUserId())){
            //НАВЕРНОЕ тоже не можем обрабатывать асинхронно
            userSet.delete(purchaseEventDto.getBuyerUserId());
            return;
        }
        if(!productSet.add(purchaseEventDto.getProductId())){
            //не можем обрабатывать асинхронно, подождать
            userSet.delete(purchaseEventDto.getBuyerUserId());
            userSet.delete(purchaseEventDto.getOwnerUserId());
            return;
        }

        try{
            eventService.doPurchase(purchaseEventDto.getPurchaseId());
        }catch (Throwable e){
            //отправить в очередь ошибок
        }finally {
            userSet.delete(purchaseEventDto.getBuyerUserId());
            userSet.delete(purchaseEventDto.getOwnerUserId());
            productSet.delete(purchaseEventDto.getProductId());
        }
    }
}
