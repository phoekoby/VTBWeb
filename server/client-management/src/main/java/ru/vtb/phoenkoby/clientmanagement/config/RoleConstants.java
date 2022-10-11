package ru.vtb.phoenkoby.clientmanagement.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RoleConstants {
    private final String USER = "USER";
    private final String ADMIN = "ADMINISTRATOR";
    private final String SUPERVISOR = "SUPERVISOR";
    private final String REDACTOR = "EDITOR";
}
