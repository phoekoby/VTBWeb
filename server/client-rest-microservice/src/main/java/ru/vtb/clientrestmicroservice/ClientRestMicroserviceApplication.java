package ru.vtb.clientrestmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@RefreshScope
public class ClientRestMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientRestMicroserviceApplication.class, args);
    }

}
