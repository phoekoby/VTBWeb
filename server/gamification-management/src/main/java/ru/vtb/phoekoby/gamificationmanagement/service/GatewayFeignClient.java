package ru.vtb.phoekoby.gamificationmanagement.service;


import ru.vtb.phoekoby.gamificationmanagement.dto.UserDTO;

public interface GatewayFeignClient {
    UserDTO authenticate(String jwtToken);
}
