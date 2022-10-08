package ru.vtb.clientrestmicroservice.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.clientrestmicroservice.entity.Currency;

@AllArgsConstructor
@Data
@Builder
public class PurchaseDto {
    private Long fromWalletId;
    private Long toWalletId;
    private Long productId;
    private Currency currency;
    private Double cost;
}
