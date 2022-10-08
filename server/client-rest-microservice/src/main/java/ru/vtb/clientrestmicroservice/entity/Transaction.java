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
public class Transaction extends BaseEntity{

    @Column(name = "transaction_hash")
    private String transactionHash;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id")
    private Wallet fromWallet;

    @ManyToOne
    @JoinColumn(name = "to_wallet_id")
    private Wallet toWallet;
}
