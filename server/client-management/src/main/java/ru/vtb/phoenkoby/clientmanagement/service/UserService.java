package ru.vtb.phoenkoby.clientmanagement.service;


import ru.vtb.phoenkoby.clientmanagement.domain.User;
import ru.vtb.phoenkoby.clientmanagement.dto.create.CreateUserDTO;
import ru.vtb.phoenkoby.clientmanagement.dto.response.ResponseUserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    ResponseUserDTO createUser(CreateUserDTO createUserDTO);

    boolean exitingUser(Long id);

    boolean exitingUserByLogin(String login);

    String authorize(String login, String password);

    ResponseUserDTO authenticate(String jwt);

    ResponseUserDTO addRoleToUser(Long id, String role);

    List<ResponseUserDTO> getAllPublicUsers(Pageable pageable);

    ResponseUserDTO getPublicUser(Long id);

    User getUserById(Long id);

    User save(User user);

    ResponseUserDTO getCurrentUser();
}
