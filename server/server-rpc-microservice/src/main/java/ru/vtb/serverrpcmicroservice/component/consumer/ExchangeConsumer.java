package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.ExchangeEventDto;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;

@RequiredArgsConstructor
@Component
public class ExchangeConsumer {
    private final EventService eventService;
    private final CustomISet<Long> userSet;

    @SneakyThrows
    @RabbitListener(
            id = "ExchangeEventMessageListener",
            queues = "ru.vtb.qu.rpc.event.exchange",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(ExchangeEventDto exchangeEventDto){
        //подумать какой из юзеров не может обрабатываться одновременно
        if(!userSet.add(exchangeEventDto.getUserId())){
            //подождать
            return;
        }


        try {
            eventService.doExchange(exchangeEventDto.getExchangeId());
        }catch (Throwable e){
                //отправить в очередь ошибок
        }finally {
            userSet.delete(exchangeEventDto.getUserId());
        }

    }
}
