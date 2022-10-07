package ru.vtb.serverrpcmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.integrationmodule.events.UserCourseProcessingChangedEventDto;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;
import ru.vtb.serverrpcmicroservice.service.EventService;


@Component
@RequiredArgsConstructor
public class UserCourseProcessingConsumer {
    private final CustomISet<Long> userSet;
    private final EventService eventService;

    @SneakyThrows
    @RabbitListener(
            id = "UserCourseProcessingEventMessageListener",
            queues = "ru.vtb.qu.rpc.event.user.course.processing",
            containerFactory = "vtbMessageListenerContainer"
    )
    public void onMessage(UserCourseProcessingChangedEventDto userCourseProcessingChangedEventDto){
        if(!userSet.add(userCourseProcessingChangedEventDto.getUserId())){
            // подождать
        }
        try {
            eventService.analyseUserCourseProcessingChanged(userCourseProcessingChangedEventDto);
        }catch (Throwable e){
            //кинуть в очередь ошибок
        }finally {
            userSet.delete(userCourseProcessingChangedEventDto.getUserId());
        }

    }
}
