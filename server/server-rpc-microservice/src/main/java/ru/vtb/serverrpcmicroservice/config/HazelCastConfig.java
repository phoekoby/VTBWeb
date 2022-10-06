package ru.vtb.serverrpcmicroservice.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vtb.serverrpcmicroservice.pojo.CustomISet;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class HazelCastConfig {

    @Bean
    public HazelcastInstance hazelcastClientInstance(){
        return  Hazelcast.newHazelcastInstance(new Config().setInstanceName(UUID.randomUUID().toString()));
    }


    @Bean
    public CustomISet<Long> userSet(HazelcastInstance hazelcastInstance){
        IMap<Long, Long> userMap = hazelcastInstance.getMap("userMap");
        return new CustomISet<>(userMap, 1, TimeUnit.MINUTES);
    }

    @Bean
    public CustomISet<Long> productSet(HazelcastInstance hazelcastInstance){
        IMap<Long, Long> productMap = hazelcastInstance.getMap("productMap");
        return new CustomISet<>(productMap, 1, TimeUnit.MINUTES);
    }
}
