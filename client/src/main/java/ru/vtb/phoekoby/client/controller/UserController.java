package ru.vtb.phoekoby.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.phoekoby.client.config.AuthorityConstants;
import ru.vtb.phoekoby.client.dto.AuthorizeUserDTO;
import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;
import ru.vtb.phoekoby.client.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/create-account")
    public ResponseEntity<ResponseUserDTO> createUser(
            @RequestBody @NotNull @Valid CreateUserDTO createUserDTO
    ) {
        log.debug("Try to create new User: {}", createUserDTO);
        if (userService.exitingUserByLogin(createUserDTO.getLogin())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User with this login already exist");
        }
        ResponseUserDTO result = userService.createUser(createUserDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/v1/authorize")
    public ResponseEntity<String> authorize(
            @RequestBody @NotNull @Valid AuthorizeUserDTO authorizeUserDTO
    ) {
        log.debug("Request for authorize with {}, {}", authorizeUserDTO.getLogin(), authorizeUserDTO.getPassword());
        String jwt = userService.authorize(authorizeUserDTO.getLogin(), authorizeUserDTO.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/v1/authenticate")
    public ResponseEntity<ResponseUserDTO> authenticate(
            @RequestBody @NotBlank String token
    ) {
        log.debug("Request for authentication with Token: {}", token);

        ResponseUserDTO responseUserDTO = userService.authenticate(token);
        return ResponseEntity.ok(responseUserDTO);
    }

    @PreAuthorize("hasAuthority(#authorityConstants.STORE_MANAGEMENT)")
    @PutMapping("/v1/change-role/{userId}")
    public ResponseEntity<ResponseUserDTO> changeUserRole(
            @PathVariable Long userId,
            @RequestBody @NotBlank String addingRole
    ) {
        log.debug("REST Request for change User Role for User with Id: {}", userId);
        ResponseUserDTO result = userService.addRoleToUser(userId, addingRole);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/public-users")
    public ResponseEntity<List<ResponseUserDTO>> getAllPublicUsers(
            Pageable pageable
    ) {
        log.debug("REST request for getting all public users");
        List<ResponseUserDTO> result = userService.getAllPublicUsers(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/my-account")
    public ResponseEntity<ResponseUserDTO> getUser(){
        log.debug("REST request for getting user account");
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/v1/public-user/{id}")
    public ResponseEntity<ResponseUserDTO> getPublicUser(
            @PathVariable Long id
    ) {
        log.debug("REST request for getting public user by Id {}", id);
        ResponseUserDTO result = userService.getPublicUser(id);
        return ResponseEntity.ok(result);
    }
}
