package ru.vtb.clientrestmicroservice.entity;

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

    @Column(name = "transaction_hash")
    private String transactionHash;

    @ManyToOne
    private Wallet wallet;
}
