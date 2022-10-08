package ru.vtb.clientrestmicroservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "transaction", schema = "transaction_management")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
//TODO долго думали и решили что лучше хранить все транзакции в базе
public class Transaction extends BaseEntity {

    @Column(name = "transaction_hash")
    private String transactionHash;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id")
    private Wallet fromWallet;

    @ManyToOne
    @JoinColumn(name = "to_wallet_id")
    private Wallet toWallet;

    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "amount")
    private Double amount;
}
