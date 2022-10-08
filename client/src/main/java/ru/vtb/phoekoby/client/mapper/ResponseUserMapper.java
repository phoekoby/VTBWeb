package ru.vtb.phoekoby.client.mapper;

import org.springframework.stereotype.Component;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseUserMapper implements EntityMapper<User, ResponseUserDTO> {
    @Override
    public User toEntity(ResponseUserDTO responseUserDTO) {
        return User
                .builder()
                .login(responseUserDTO.getLogin())
                .firstName(responseUserDTO.getFirstName())
                .lastName(responseUserDTO.getLastName())
                .build();
    }

    @Override
    public ResponseUserDTO toDto(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setEmail(user.getEmail());
        responseUserDTO.setLastName(user.getLastName());
        responseUserDTO.setFirstName(user.getFirstName());
        responseUserDTO.setId(user.getId());
        return responseUserDTO;
    }

    @Override
    public List<User> toEntity(List<ResponseUserDTO> responseUserDTOS) {
        return responseUserDTOS
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseUserDTO> toDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
