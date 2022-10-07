package ru.vtb.phoekoby.client.service;


import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;

public interface UserService {
    ResponseUserDTO createUser(CreateUserDTO createUserDTO);

    boolean exitingUser(Long id);

    boolean exitingUserByLogin(String login);

    String authorize(String login, String password);

    ResponseUserDTO authenticate(String jwt);
}
