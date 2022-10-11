package ru.vtb.phoenkoby.clientmanagement.mapper;

import ru.vtb.phoenkoby.clientmanagement.dto.create.CreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.phoenkoby.clientmanagement.domain.Request;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestStatus;
import ru.vtb.phoenkoby.clientmanagement.repo.RoleRepository;
import ru.vtb.phoenkoby.clientmanagement.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateRequestMapper implements EntityMapper<Request, CreateRequestDTO> {
    private final UserService userService;


    private final RoleRepository roleRepository;
    @Override
    public Request toEntity(CreateRequestDTO createRequestDTO) {
        Request request = new Request();
        request.setRequestingRole(roleRepository.getRoleByName(createRequestDTO.getRequestingRole()).orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND)));
        request.setRequestType(createRequestDTO.getRequestType());
        request.setTo(userService.getUserById(createRequestDTO.getTo()));
        request.setFrom(userService.getUserById(userService.getCurrentUser().getId()));
        request.setRequestStatus(RequestStatus.OPENED);
        return null;
    }

    @Override
    public CreateRequestDTO toDto(Request request) {
        return CreateRequestDTO.builder()
                .to(request.getTo().getId())
                .requestingRole(request.getRequestingRole().getName())
                .requestType(request.getRequestType())
                .build();
    }

    @Override
    public List<Request> toEntity(List<CreateRequestDTO> createRequestDTOS) {
        return createRequestDTOS.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<CreateRequestDTO> toDto(List<Request> requests) {
        return requests.stream().map(this::toDto).collect(Collectors.toList());
    }
}
