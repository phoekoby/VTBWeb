package ru.vtb.serverrpcmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.vtb.serverrpcmicroservice", "ru.vtb.integrationmodule"})
public class ServerRpcMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRpcMicroserviceApplication.class, args);
    }

}
