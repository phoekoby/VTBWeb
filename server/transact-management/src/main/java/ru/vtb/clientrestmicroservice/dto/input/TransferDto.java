package ru.vtb.clientrestmicroservice.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.Currency;

@Data
@AllArgsConstructor
@Builder
public class TransferDto {
    private Long fromWalletId;
    private Long toWalletId;
    private Currency currency;
    private Double amount;
}
