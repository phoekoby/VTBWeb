package ru.vtb.phoekoby.client.mapper;

import org.springframework.stereotype.Component;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.dto.AbstractUserDTO;
import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateUserMapper implements EntityMapper<User, CreateUserDTO>{
    @Override
    public User toEntity(CreateUserDTO createUserDTO) {
        return User
                .builder()
                .login(createUserDTO.getLogin())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .build();
    }

    @Override
    public CreateUserDTO toDto(User user) {
        return (CreateUserDTO) AbstractUserDTO
                .builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build();
    }

    @Override
    public List<User> toEntity(List<CreateUserDTO> createUserDTOS) {
        return createUserDTOS
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreateUserDTO> toDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}