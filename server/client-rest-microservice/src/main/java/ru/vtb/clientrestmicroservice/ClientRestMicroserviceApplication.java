package ru.vtb.clientrestmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.vtb.clientrestmicroservice", "ru.vtb.integrationmodule"})
@RefreshScope
public class ClientRestMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientRestMicroserviceApplication.class, args);
    }

}
