package ru.vtb.clientrestmicroservice.dto.moneyApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResultHash {
    private String transactionHash;
}
