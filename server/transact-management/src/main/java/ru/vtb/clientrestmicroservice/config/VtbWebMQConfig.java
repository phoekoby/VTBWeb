package ru.vtb.clientrestmicroservice.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration(
        exclude={
                org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class
        })
@EnableRabbit
@EnableTransactionManagement
@EnableAsync
public class VtbWebMQConfig {

}
