package ru.vtb.clientrestmicroservice.entity;

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
}
