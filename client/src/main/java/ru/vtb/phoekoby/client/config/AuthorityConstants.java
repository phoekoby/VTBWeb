package ru.vtb.phoekoby.client.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthorityConstants {
    private final String EXCHANGE = "exchange";
    private final String PURCHASE = "purchase";
    private final String ACCRUAL_TO_ALL = "accrual_to_all";
    private final String MANAGEMENT = "management";
    private final String MENTORING = "mentoring";
    private final String PUBLISH = "publish";

}
