package ru.vtb.integrationmodule.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class WalletBalanceChanged extends BaseEntity{

    @Column(name = "changed_amount")
    private Double changedAmount;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
