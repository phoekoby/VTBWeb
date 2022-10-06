package ru.vtb.serverrpcmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(value="ru.vtb.integrationmodule.repo")
@EntityScan(basePackages = "ru.vtb.integrationmodule.entity")
@ComponentScan(basePackages = {"ru.vtb.serverrpcmicroservice", "ru.vtb.integrationmodule"})
public class ServerRpcMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRpcMicroserviceApplication.class, args);
    }

}
