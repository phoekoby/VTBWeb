package ru.vtb.clientrestmicroservice.dto.moneyApi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiWalletDto {
    private String publicKey;
    private String privateKey;
}
