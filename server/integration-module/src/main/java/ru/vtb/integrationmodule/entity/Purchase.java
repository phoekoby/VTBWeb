package ru.vtb.integrationmodule.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Purchase extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "prev_owner_user_id")
    private User prevOwnerUser;

    @OneToOne
    @JoinColumn(name = "buyer_user_id")
    private User buyerUser;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
