package ru.vtb.serverrpcmicroservice.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthorityConstants {
    private final String EXCHANGE = "EXCHANGE";
    private final String SHOPPING = "SHOPPING";
    private final String COINS_ACCRUAL = "COINS_ACCRUAL";
    private final String STORE_MANAGEMENT = "STORE_MANAGEMENT";
    private final String USER_MANAGEMENT = "USER_MANAGEMENT";
    private final String PUBLICATION = "PUBLICATION";
}
