package ru.vtb.clientrestmicroservice.entity;


import lombok.*;
import org.hibernate.Hibernate;
import ru.vtb.clientrestmicroservice.entity.courses.UserSlide;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
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
    private Date date;


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
