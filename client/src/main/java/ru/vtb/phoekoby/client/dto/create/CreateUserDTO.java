package ru.vtb.phoekoby.client.dto.create;

import lombok.*;
import ru.vtb.phoekoby.client.dto.AbstractUserDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateUserDTO extends AbstractUserDTO implements Serializable {
    @NotBlank
    private String password;
}
