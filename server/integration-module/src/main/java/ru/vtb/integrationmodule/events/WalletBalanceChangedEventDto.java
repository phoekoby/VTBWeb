package ru.vtb.integrationmodule.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.integrationmodule.entity.TransactionType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletBalanceChangedEventDto implements Serializable {
    private Long walletId;
    private TransactionType transactionType;
    private Double changeAmount;
}
