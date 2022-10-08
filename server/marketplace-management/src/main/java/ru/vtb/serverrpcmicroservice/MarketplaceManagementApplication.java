package ru.vtb.serverrpcmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
//заставить прочитать заново конфигурацию в случае ее изменения
@RefreshScope
public class MarketplaceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceManagementApplication.class, args);
    }

}
