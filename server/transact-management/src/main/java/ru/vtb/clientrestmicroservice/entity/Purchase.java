package ru.vtb.clientrestmicroservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "purchase", schema = "transaction_management")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Purchase extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "prev_owner_user_account_id")
    private UserAccount prevOwnerUser;

    @ManyToOne
    @JoinColumn(name = "buyer_user_account_id")
    private UserAccount buyerUser;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "cost")
    private Double cost;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
