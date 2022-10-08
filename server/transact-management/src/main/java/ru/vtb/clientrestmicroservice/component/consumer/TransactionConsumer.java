package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {
    private final TransactionService transactionService;

//    @RabbitListener
    public void onMessage(TransactionMessageEventDto transactionMessageEventDto){
        String status = transactionService.getStatus(transactionMessageEventDto.getHash());
        switch (status){
            case "Success":{
                
            }
        }
    }
}
