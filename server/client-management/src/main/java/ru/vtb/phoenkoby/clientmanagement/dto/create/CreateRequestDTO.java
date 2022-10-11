package ru.vtb.phoenkoby.clientmanagement.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestType;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestDTO implements Serializable {
    private Long to;

    private RequestType requestType;

    private String requestingRole;

}
