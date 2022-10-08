package ru.vtb.phoekoby.client.domain;

import lombok.Getter;
import lombok.Setter;
import ru.vtb.phoekoby.client.domain.enumiration.RequestStatus;
import ru.vtb.phoekoby.client.domain.enumiration.RequestType;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Request extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_from_id")
    private User from;
    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private User to;

    @Column(name = "request_type")
    private RequestType requestType;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

}
