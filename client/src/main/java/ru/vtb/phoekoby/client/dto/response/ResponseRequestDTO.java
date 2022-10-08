package ru.vtb.phoekoby.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.domain.enumiration.RequestStatus;
import ru.vtb.phoekoby.client.domain.enumiration.RequestType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
