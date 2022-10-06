package ru.vtb.integrationmodule.events;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.integrationmodule.entity.TransactionStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusChangedEventDto implements Serializable {
    private Long transactionId;
    private TransactionType transactionType;
    private TransactionStatus prevStatus;
    private TransactionStatus currStatus;
}
