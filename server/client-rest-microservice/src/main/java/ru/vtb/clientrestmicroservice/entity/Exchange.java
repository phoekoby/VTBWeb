package ru.vtb.clientrestmicroservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
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
