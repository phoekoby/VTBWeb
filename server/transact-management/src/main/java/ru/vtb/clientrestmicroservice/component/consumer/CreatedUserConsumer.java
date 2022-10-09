package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.CreateUserMessage;
import ru.vtb.clientrestmicroservice.service.UserAccountService;

@Component
@RequiredArgsConstructor
public class CreatedUserConsumer {
    private final UserAccountService accountService;

    @RabbitListener(
            id="VtbUserResponseListener",
            queues = "ru.vtb.qu.user",
            containerFactory = "proactiveMessageListenerContainer"
    )
    public void onMessage(CreateUserMessage createUserMessage){
        accountService.createUserAccount(createUserMessage.getUserId());
    }
}
