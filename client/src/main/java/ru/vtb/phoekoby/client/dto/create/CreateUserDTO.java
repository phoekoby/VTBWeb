package ru.vtb.phoekoby.client.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.vtb.phoekoby.client.dto.AbstractUserDTO;

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
