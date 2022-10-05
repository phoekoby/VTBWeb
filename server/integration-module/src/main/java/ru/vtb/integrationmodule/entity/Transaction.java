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
public class Transaction extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @OneToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
