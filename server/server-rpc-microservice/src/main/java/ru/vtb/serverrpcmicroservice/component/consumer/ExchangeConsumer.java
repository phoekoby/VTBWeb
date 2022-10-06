package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.ExchangeEventDto;
import ru.vtb.integrationmodule.repo.ExchangeRepository;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;

@RequiredArgsConstructor
@Component
public class ExchangeConsumer {
    private final EventService eventService;
    private final CustomISet<Long> userMap;
    private final ExchangeRepository exchangeRepository;

    @SneakyThrows
    @RabbitListener(
            id = "ExchangeEventMessageListener",
            queues = "ru.vtb.qu.event.exchange",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(ExchangeEventDto exchangeEventDto){
        //подумать какой из юзеров не может обрабатываться одноврменно
        eventService.doExchange(exchangeRepository.findById(exchangeEventDto.getExchangeId()).orElse(null));
    }
}
