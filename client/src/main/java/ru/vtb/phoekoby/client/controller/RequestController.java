package ru.vtb.phoekoby.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.phoekoby.client.dto.create.CreateRequestDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseRequestDTO;
import ru.vtb.phoekoby.client.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;


    @GetMapping("/v1/requests")
    public ResponseEntity<List<ResponseRequestDTO>> getAllOpenedRequest() {
        log.debug("REST request for getting all opened requests");
        List<ResponseRequestDTO> result = requestService.getAllOpenedRequest();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/requests/{id}")
    public ResponseEntity<ResponseRequestDTO> getRequestById(
            @PathVariable Long id
    ) {
        log.debug("REST request for getting request by id: {}", id);
        ResponseRequestDTO result = requestService.getRequestById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/v1/requests/{id}/accept")
    public ResponseEntity<ResponseRequestDTO> acceptRequest(
            @PathVariable Long id
    ) {
        log.debug("REST request for accepting request by id: {}", id);
        ResponseRequestDTO result = requestService.acceptRequest(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/v1/requests/{id}/reject")
    public ResponseEntity<ResponseRequestDTO> rejectRequest(
            @PathVariable Long id
    ) {
        log.debug("REST request for rejecting request by id: {}", id);
        ResponseRequestDTO result = requestService.rejectRequest(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/requests/get-my-requests")
    public ResponseEntity<List<ResponseRequestDTO>> getAllMyRequests() {
        log.debug("REST request for getting all users requests");
        List<ResponseRequestDTO> result = requestService.getAllUserRequest();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/requests/create")
    public ResponseEntity<ResponseRequestDTO> createRequest(
            @RequestBody CreateRequestDTO createRequestDTO
    ) {
        log.debug("REST request for creating request: {}", createRequestDTO);
        ResponseRequestDTO result = requestService.createRequest(createRequestDTO);
        return ResponseEntity.ok(result);
    }

}
