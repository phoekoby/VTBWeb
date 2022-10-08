package ru.vtb.clientrestmicroservice.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_account", schema = "transaction_management")
@Getter
@Setter
@RequiredArgsConstructor
public class UserAccount extends BaseEntity{

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "buyerUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> myPurchases;

    @OneToMany(mappedBy = "prevOwnerUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> mySales;

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
