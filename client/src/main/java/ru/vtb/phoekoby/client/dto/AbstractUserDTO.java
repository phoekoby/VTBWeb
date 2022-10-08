package ru.vtb.phoekoby.client.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUserDTO {
    @NotEmpty
//    @Pattern("")
    protected String login;
    @Email
    protected String email;
    protected String firstName;
    protected String lastName;
}
