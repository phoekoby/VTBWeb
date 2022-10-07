package ru.vtb.clientrestmicroservice.dto.input;

import lombok.Data;
import ru.vtb.clientrestmicroservice.dto.Currency;

@Data
public class TransferDto {
    private Long fromId;
    private Long toId;
    private Currency currency;
    private Double amount;
}
