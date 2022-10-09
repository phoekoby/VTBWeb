package ru.vtb.serverrpcmicroservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BuyProductDto implements Serializable {
    private Long productId;
    private Long fromWalletId;
    private Currency currency;
}
