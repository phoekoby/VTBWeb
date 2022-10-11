package ru.vtb.phoenkoby.clientmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthorizeUserDTO implements Serializable {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
