package ru.vtb.phoenkoby.clientmanagement.dto.response;

import ru.vtb.phoenkoby.clientmanagement.dto.AbstractUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseUserDTO extends AbstractUserDTO implements Serializable {
    private Long id;

    private List<String> roles;

    private List<String> privileges;
}
