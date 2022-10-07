package ru.vtb.phoekoby.client.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractUserDTO {
    @NotEmpty
//    @Pattern("")
    private String login;
    @Email
    private String email;
    private String firstName;
    private String lastName;
}
