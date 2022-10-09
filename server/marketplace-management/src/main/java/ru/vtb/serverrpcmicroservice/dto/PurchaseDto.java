package ru.vtb.serverrpcmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


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