package ru.vtb.phoenkoby.clientmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class ClientManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientManagementApplication.class, args);
    }

}
