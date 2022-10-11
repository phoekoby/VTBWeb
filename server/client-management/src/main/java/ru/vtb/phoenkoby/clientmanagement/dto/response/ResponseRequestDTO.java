package ru.vtb.phoenkoby.clientmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestStatus;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestType;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequestDTO implements Serializable {
    private Long id;

    private Long from;

    private Long to;

    private RequestType requestType;

    private String requestingRole;

    private RequestStatus requestStatus;

}
