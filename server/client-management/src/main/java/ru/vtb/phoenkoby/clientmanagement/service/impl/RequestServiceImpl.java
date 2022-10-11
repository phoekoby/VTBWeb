package ru.vtb.phoenkoby.clientmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.phoenkoby.clientmanagement.config.RoleConstants;
import ru.vtb.phoenkoby.clientmanagement.domain.Request;
import ru.vtb.phoenkoby.clientmanagement.domain.Role;
import ru.vtb.phoenkoby.clientmanagement.domain.User;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestStatus;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestType;
import ru.vtb.phoenkoby.clientmanagement.dto.create.CreateRequestDTO;
import ru.vtb.phoenkoby.clientmanagement.dto.response.ResponseRequestDTO;
import ru.vtb.phoenkoby.clientmanagement.mapper.CreateRequestMapper;
import ru.vtb.phoenkoby.clientmanagement.mapper.ResponseRequestMapper;
import ru.vtb.phoenkoby.clientmanagement.repo.RequestRepository;
import ru.vtb.phoenkoby.clientmanagement.repo.RoleRepository;
import ru.vtb.phoenkoby.clientmanagement.service.RequestService;
import ru.vtb.phoenkoby.clientmanagement.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RequestServiceImpl implements RequestService {
    private final UserService userService;

    private final RoleConstants roleConstants;

    private final RoleRepository roleRepository;

    private final RequestRepository requestRepository;

    private final CreateRequestMapper createRequestMapper;
    private final ResponseRequestMapper responseRequestMapper;

    @Override
    public ResponseRequestDTO createRequest(CreateRequestDTO createRequestDTO) {
        Request request = createRequestMapper.toEntity(createRequestDTO);
        Optional<Role> role = roleRepository.getRoleByName(createRequestDTO.getRequestingRole());
        Role roleAdmin = roleRepository.getRoleByName(roleConstants.getADMIN()).get();
        Role roleSupervisor = roleRepository.getRoleByName(roleConstants.getSUPERVISOR()).get();
        if (createRequestDTO.getRequestingRole() != null && role.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "This role is not found");
        }
        if (createRequestDTO.getRequestingRole().equals(roleConstants.getADMIN()) ||
                (!request.getTo().getRoles().contains(roleAdmin) && !request.getTo().getRoles().contains(roleSupervisor))) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        request = requestRepository.save(request);
        return responseRequestMapper.toDto(request);
    }

    @Override
    public ResponseRequestDTO rejectRequest(Long id) {
        Request request = findRequestById(id);
        checkModifiableRequest(request);
        request.setRequestStatus(RequestStatus.REJECTED);
        request = requestRepository.save(request);
        return responseRequestMapper.toDto(request);
    }

    private void checkModifiableRequest(Request request) {
        Long currentUserId = userService.getCurrentUser().getId();
        if (!request.getTo().getId().equals(currentUserId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        if (request.getRequestStatus() != RequestStatus.OPENED) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Request Is closed");
        }
    }

    @Override
    public ResponseRequestDTO acceptRequest(Long id) {
        Request request = findRequestById(id);
        checkModifiableRequest(request);
        User from = request.getFrom();
        if (request.getRequestType() == RequestType.ADD_ROLE) {
            from.getRoles().add(request.getRequestingRole());
        } else if (request.getRequestType() == RequestType.INVITE_TO_MENTOR) {
            User to = request.getTo();
            from.getMyMentorings().add(to);
        } else if (request.getRequestType() == RequestType.INVITE_FROM_MENTORING) {
            User to = request.getTo();
            from.getMyMentors().add(to);
        }
        from = userService.save(from);
        request.setRequestStatus(RequestStatus.CONFIRMED);
        request = requestRepository.save(request);
        return responseRequestMapper.toDto(request);
    }

    @Override
    public ResponseRequestDTO getRequestById(Long id) {
        Request request = findRequestById(id);
        Long currentUserId = userService.getCurrentUser().getId();
        if (!request.getFrom().getId().equals(currentUserId) && !request.getTo().getId().equals(currentUserId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        return responseRequestMapper.toDto(request);
    }

    @Override
    public List<ResponseRequestDTO> getAllOpenedRequest() {
        User user = userService.getUserById(userService.getCurrentUser().getId());
        List<Request> requests = requestRepository.findAllByToAndRequestStatus(user, RequestStatus.OPENED);
        return requests.stream().map(responseRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ResponseRequestDTO> getAllUserRequest() {
        User user = userService.getUserById(userService.getCurrentUser().getId());
        List<Request> requests = requestRepository.findAllByToOrFrom(user, user);
        return requests.stream().map(responseRequestMapper::toDto).collect(Collectors.toList());
    }

    public Request findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Request Not Found"));
    }
}
