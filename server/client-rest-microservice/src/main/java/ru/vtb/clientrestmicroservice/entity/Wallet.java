package ru.vtb.clientrestmicroservice.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Wallet extends BaseEntity{

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "fromWallet")
    private List<Transaction> outputTransactions;

    @OneToMany(mappedBy = "toWallet")
    private List<Transaction> inputTransaction;


}
