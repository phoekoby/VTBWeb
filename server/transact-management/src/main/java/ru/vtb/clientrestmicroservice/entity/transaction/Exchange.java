package ru.vtb.clientrestmicroservice.entity.transaction;

import ru.vtb.clientrestmicroservice.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "exchange", schema = "transaction_management")
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
}
