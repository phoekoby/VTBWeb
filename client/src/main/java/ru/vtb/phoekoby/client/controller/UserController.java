package ru.vtb.phoekoby.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;
import ru.vtb.phoekoby.client.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
//                .header()
                .body(result);
    }

    @PostMapping("/v1/authorize")
    public ResponseEntity<String> authorize(
            @RequestParam @NotBlank String login,
            @RequestParam @NotBlank String password
    ) {
        log.debug("Request for authorize with {}, {}", login, password);
        String jwt = userService.authorize(login, password);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/v1/authenticate")
    public ResponseEntity<ResponseUserDTO> authorize(
            @RequestParam @NotBlank String token
    ) {
        log.debug("Request for authentication with Token: {}", token);

        ResponseUserDTO responseUserDTO = userService.authenticate(token);
        return ResponseEntity.ok(responseUserDTO);
    }
}
