package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.PurchaseMessageEventDto;

@Component
@RequiredArgsConstructor
public class PurchaseConsumer {

    @RabbitListener
    public void onMessage(PurchaseMessageEventDto purchaseMessageEventDto){
       // String status = transactionService.getStatus(transactionMessageEventDto.getHash());
      /*  switch (status){
            case "Success":{

            }
        }*/
    }
}
