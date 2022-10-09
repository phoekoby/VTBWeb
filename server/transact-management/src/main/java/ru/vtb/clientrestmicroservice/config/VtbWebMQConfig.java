package ru.vtb.clientrestmicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
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

        @Value("${ru.vtb.rabbit.hostname}")
        private String hostname;
        @Value("${ru.vtb.rabbit.username}")
        private String username;
        @Value("${ru.vtb.rabbit.password}")
        private String password;
        @Value("${ru.vtb.rabbit.prefetch.count:250}")
        private Integer prefetchCount;


        @Bean
        public Jackson2JsonMessageConverter getConverter (ObjectMapper objectMapper) {
                return new Jackson2JsonMessageConverter(objectMapper);
        }


        @Bean(name = {"vtbMQConnectionFactory"})
        public CachingConnectionFactory connectionFactory() {
                CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
                if (StringUtils.isNotBlank(username)) connectionFactory.setUsername(username);
                if (StringUtils.isNotBlank(password)) connectionFactory.setPassword(password);
                return connectionFactory;
        }


        @Bean("transactionInspectRabbitTemplate")
        public RabbitTemplate transactionInspectRabbitTemplate(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
                RabbitTemplate rabbitTemplate = new RabbitTemplate();
                rabbitTemplate.setConnectionFactory(connectionFactory());
                rabbitTemplate.setExchange("ru.vtb.ex.transfer");
                rabbitTemplate.setChannelTransacted(true);
                rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
                return rabbitTemplate;
        }


        @Bean("userTransactionRabbitTemplate")
        public RabbitTemplate userTransactionRabbitTemplate(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
                RabbitTemplate rabbitTemplate = new RabbitTemplate();
                rabbitTemplate.setConnectionFactory(connectionFactory());
                rabbitTemplate.setExchange("ru.vtb.ex.user.transfer");
                rabbitTemplate.setChannelTransacted(true);
                rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
                return rabbitTemplate;
        }



        @Bean("eventListenerContainerFactory")
        public RabbitListenerContainerFactory<?> eventListenerContainerFactory(CachingConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
                SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
                container.setConnectionFactory(connectionFactory);
                container.setMessageConverter(converter);
                container.setChannelTransacted(true);
                container.setDefaultRequeueRejected(false);
                container.setPrefetchCount(prefetchCount);
                return container;
        }

}
