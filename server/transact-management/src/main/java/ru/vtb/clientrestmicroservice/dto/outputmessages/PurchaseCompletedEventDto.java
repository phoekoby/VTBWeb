package ru.vtb.clientrestmicroservice.dto.outputmessages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PurchaseCompletedEventDto {
    private Long userId;
    private Long productId;
    private Double purchaseCost;
}
