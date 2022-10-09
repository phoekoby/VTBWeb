package ru.vtb.clientrestmicroservice.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.enumiration.Currency;
import ru.vtb.clientrestmicroservice.entity.enumiration.TransactionType;

@Data
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long transactionId;
    private String transactionHash;
    private Long fromId;
    private Long toId;
    private Direction direction;
    private Double amount;
    private Currency currency;
    private TransactionType transactionType;
}
