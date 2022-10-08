package ru.vtb.serverrpcmicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VtbWebMQConfig {

    @Value("${ru.vtb.rabbit.hostname}")
    private String hostname;
    @Value("${ru.vtb.rabbit.username}")
    private String username;
    @Value("${ru.vtb.rabbit.password}")
    private String password;
    @Value("${ru.vtb.rabbit.virtual.host}")
    private String virtualHost;
    @Value("${ru.vtb.rabbit.concurrent.consumers:10}")
    private Integer proactiveConcurrentConsumers;
    @Value("${ru.vtb.rabbit.max.concurrent.consumers:15}")
    private Integer proactiveMaxConcurrentConsumers;
    @Value("${ru.vtb.rabbit.prefetch.count:250}")
    private Integer prefetchCount;

    @Bean(name = {"VtbMQConnectionFactory"})
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
        if (StringUtils.isNotBlank(username)) connectionFactory.setUsername(username);
        if (StringUtils.isNotBlank(password)) connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter getConverter (ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    @Bean(name = "VtbMessageListenerContainer")
    public RabbitListenerContainerFactory<?> vtbListenerContainer(Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory());
        container.setMessageConverter(converter);
        container.setDefaultRequeueRejected(false);
        container.setChannelTransacted(false);
        container.setConcurrentConsumers(proactiveConcurrentConsumers);
        container.setAutoStartup(true);
        container.setMaxConcurrentConsumers(proactiveMaxConcurrentConsumers);
        container.setPrefetchCount(prefetchCount);
        return container;
    }


    @Bean("vtbEventRabbitTemplate")
    public RabbitTemplate vtbEventRabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setExchange("ru.vtb.");
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }


}
