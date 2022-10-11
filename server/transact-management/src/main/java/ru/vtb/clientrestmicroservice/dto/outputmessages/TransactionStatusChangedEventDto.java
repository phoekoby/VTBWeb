package ru.vtb.clientrestmicroservice.dto.outputmessages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.enumiration.TransactionStatus;

@Data
@AllArgsConstructor
@Builder
public class TransactionStatusChangedEventDto {
    private Long transactionId;
    private TransactionStatus prevTransactionStatus;
    private TransactionStatus currTransactionStatus;
}
