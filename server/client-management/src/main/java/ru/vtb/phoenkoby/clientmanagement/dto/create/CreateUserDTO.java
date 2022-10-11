package ru.vtb.phoenkoby.clientmanagement.dto.create;

import ru.vtb.phoenkoby.clientmanagement.dto.AbstractUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
