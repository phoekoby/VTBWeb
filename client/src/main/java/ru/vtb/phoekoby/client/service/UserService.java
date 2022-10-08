package ru.vtb.phoekoby.client.service;


import org.springframework.data.domain.Pageable;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;

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
