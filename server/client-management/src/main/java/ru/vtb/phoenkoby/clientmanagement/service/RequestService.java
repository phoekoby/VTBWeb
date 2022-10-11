package ru.vtb.phoenkoby.clientmanagement.service;

import ru.vtb.phoenkoby.clientmanagement.dto.create.CreateRequestDTO;
import ru.vtb.phoenkoby.clientmanagement.dto.response.ResponseRequestDTO;

import java.util.List;

public interface RequestService {
    ResponseRequestDTO createRequest(CreateRequestDTO createRequestDTO);
    ResponseRequestDTO rejectRequest(Long id);
    ResponseRequestDTO acceptRequest(Long id);
    ResponseRequestDTO getRequestById(Long id);
    List<ResponseRequestDTO> getAllOpenedRequest();

    List<ResponseRequestDTO> getAllUserRequest();

}
