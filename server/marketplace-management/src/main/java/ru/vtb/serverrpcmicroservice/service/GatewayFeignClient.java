package ru.vtb.serverrpcmicroservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.vtb.serverrpcmicroservice.dto.UserDTO;

@FeignClient(name = "client-gateway")
public interface GatewayFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/authenticate", consumes = "application/json")
    UserDTO authenticate(String token);
}
