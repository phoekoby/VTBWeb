package ru.vtb.clientrestmicroservice.dto.moneyApi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiTransferDto {
    private String fromPrivateKey;
    private String toPublicKey;
    private Double amount;
}
