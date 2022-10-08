package ru.vtb.clientrestmicroservice.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.vtb.clientrestmicroservice.dto.UserDto;

@FeignClient(name = "client-gateway")
public interface FeignClientService {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/authenticate", consumes = "application/json")
    UserDto authenticate(String token);
}
