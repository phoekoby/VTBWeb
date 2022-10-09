package ru.vtb.clientrestmicroservice.component.consumer;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vtb.clientrestmicroservice.dto.message.PurchaseMessageEventDto;
import ru.vtb.clientrestmicroservice.service.TransactionService;

@Component
@RequiredArgsConstructor
public class PurchaseConsumer {
    private final TransactionService transactionService;

    @RabbitListener(
            id="PurchaseRabbitListener",
            queues = "ru.vtb.qu.transfer.purchase",
            containerFactory = "transferMessageListener"
    )
    public void onMessage(PurchaseMessageEventDto purchaseMessageEventDto){
            transactionService.checkPurchase(purchaseMessageEventDto);
    }
}
