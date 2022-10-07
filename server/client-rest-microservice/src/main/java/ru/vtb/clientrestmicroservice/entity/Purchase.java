package ru.vtb.clientrestmicroservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.vtb.clientrestmicroservice.entity.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Purchase extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "prev_owner_user_id")
    private User prevOwnerUser;

    @ManyToOne
    @JoinColumn(name = "buyer_user_id")
    private User buyerUser;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "cost")
    private Double cost;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
