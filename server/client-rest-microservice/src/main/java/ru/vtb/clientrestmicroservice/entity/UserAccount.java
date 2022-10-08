package ru.vtb.clientrestmicroservice.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_account", schema = "transaction_management")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserAccount extends BaseEntity{

    @Column(name = "daily_multiply")
    private Double dailyMultiply;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "last_activity_date")
    private Date lastActivityDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAccount that = (UserAccount) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
