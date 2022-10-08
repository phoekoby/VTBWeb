package ru.vtb.phoekoby.client.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RoleConstants {
    private final String USER = "user";
    private final String ADMIN = "admin";
    private final String SUPERVISOR = "supervisor";
    private final String REDACTOR = "redactor";
}
