package ru.vtb.integrationmodule.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletBalanceChangedEventDto implements Serializable {
    private Long walletBalanceChangedId;
}
