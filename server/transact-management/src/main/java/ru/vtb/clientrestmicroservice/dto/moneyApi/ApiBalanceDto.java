package ru.vtb.clientrestmicroservice.dto.moneyApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiBalanceDto {
    private Double maticAmount;
    private Double coinsAmount;
}
