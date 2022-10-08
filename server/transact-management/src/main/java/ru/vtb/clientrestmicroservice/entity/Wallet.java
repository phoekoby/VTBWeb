package ru.vtb.clientrestmicroservice.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wallet", schema = "transaction_management")
@Getter
@Setter
@RequiredArgsConstructor
public class Wallet extends BaseEntity{

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "private_key")
    private String privateKey;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "fromWallet")
    private List<Transaction> outputTransactions;

    @OneToMany(mappedBy = "toWallet")
    private List<Transaction> inputTransaction;


}
