package ru.vtb.phoekoby.client.domain;

import lombok.Getter;
import lombok.Setter;
import ru.vtb.phoekoby.client.domain.enumiration.RequestStatus;
import ru.vtb.phoekoby.client.domain.enumiration.RequestType;

import javax.persistence.*;

@Entity
@Table(name = "request", schema = "user_management")
@Getter
@Setter
public class Request extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_from")
    private User from;
    @ManyToOne
    @JoinColumn(name = "user_to")
    private User to;

    @Column(name = "request_type")
    private RequestType requestType;

    @ManyToOne
    @JoinColumn(name = "requesting_role_id")
    private Role requestingRole;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

}
