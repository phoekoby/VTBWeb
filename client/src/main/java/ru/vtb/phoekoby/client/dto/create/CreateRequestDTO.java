package ru.vtb.phoekoby.client.dto.create;

import lombok.*;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.domain.enumiration.RequestStatus;
import ru.vtb.phoekoby.client.domain.enumiration.RequestType;

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
