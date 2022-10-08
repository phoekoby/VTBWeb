package ru.vtb.phoekoby.client.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.phoekoby.client.domain.Request;
import ru.vtb.phoekoby.client.dto.response.ResponseRequestDTO;
import ru.vtb.phoekoby.client.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResponseRequestMapper implements EntityMapper<Request, ResponseRequestDTO> {
    private final UserService userService;
    @Override
    public Request toEntity(ResponseRequestDTO responseRequestDTO) {
        Request request = new Request();
        request.setRequestStatus(responseRequestDTO.getRequestStatus());
        request.setTo(userService.getUserById(responseRequestDTO.getTo()));
        request.setFrom(userService.getUserById(responseRequestDTO.getFrom()));
        request.setRequestType(responseRequestDTO.getRequestType());
        request.setRequestStatus(responseRequestDTO.getRequestStatus());
        return request;
    }

    @Override
    public ResponseRequestDTO toDto(Request request) {
        return ResponseRequestDTO
                .builder()
                .to(request.getTo().getId())
                .id(request.getId())
                .from(request.getFrom().getId())
                .requestingRole(request.getRequestingRole().getName())
                .requestType(request.getRequestType())
                .requestStatus(request.getRequestStatus())
                .build();
    }

    @Override
    public List<Request> toEntity(List<ResponseRequestDTO> responseRequestDTOS) {
        return responseRequestDTOS.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<ResponseRequestDTO> toDto(List<Request> requests) {
        return requests.stream().map(this::toDto).collect(Collectors.toList());
    }
}
