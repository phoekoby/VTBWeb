package ru.vtb.clientrestmicroservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchange", schema = "transaction_management")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Exchange extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "in_transaction_id")
    private Transaction inTransaction;

    @OneToOne
    @JoinColumn(name = "out_transaction_id")
    private Transaction outTransaction;

    @Column(name = "hash")
    private String hashCode;

    @Column(name = "currency_from")
    private Currency currencyFrom;

    @Column(name = "currency_to")
    private Currency currencyTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Exchange exchange = (Exchange) o;
        return getId() != null && Objects.equals(getId(), exchange.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
