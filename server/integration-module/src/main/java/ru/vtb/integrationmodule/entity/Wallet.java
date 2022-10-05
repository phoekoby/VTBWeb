package ru.vtb.integrationmodule.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Wallet extends BaseEntity{

    @Column(name = "amount")
    private Double amount;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
