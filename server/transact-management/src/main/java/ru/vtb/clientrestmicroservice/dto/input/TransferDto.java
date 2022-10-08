package ru.vtb.clientrestmicroservice.dto.input;

import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.Currency;

@Data
public class TransferDto {
    private Long fromWalletId;
    private Long toWalletId;
    private Currency currency;
    private Double amount;
}
