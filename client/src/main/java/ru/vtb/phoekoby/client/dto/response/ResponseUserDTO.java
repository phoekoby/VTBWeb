package ru.vtb.phoekoby.client.dto.response;

import lombok.*;
import ru.vtb.phoekoby.client.dto.AbstractUserDTO;

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
