package ru.vtb.clientrestmicroservice.component.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.TransactionMessageEventDto;

@Component
@RequiredArgsConstructor
public class ExchangeConsumer {

    //    @RabbitListener
    public void onMessage(TransactionMessageEventDto transactionMessageEventDto){
       // String status = transactionService.getStatus(transactionMessageEventDto.getHash());
    /*    switch (status){
            case "Success":{

            }
        }*/
    }
}
