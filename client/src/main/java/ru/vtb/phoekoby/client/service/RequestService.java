package ru.vtb.phoekoby.client.service;

import ru.vtb.phoekoby.client.dto.create.CreateRequestDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseRequestDTO;

import java.util.List;

public interface RequestService {
    ResponseRequestDTO createRequest(CreateRequestDTO createRequestDTO);
    ResponseRequestDTO rejectRequest(Long id);
    ResponseRequestDTO acceptRequest(Long id);
    ResponseRequestDTO getRequestById(Long id);
    List<ResponseRequestDTO> getAllOpenedRequest();

    List<ResponseRequestDTO> getAllUserRequest();

}
