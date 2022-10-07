package ru.vtb.integrationmodule.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.integrationmodule.entity.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Wallet extends BaseEntity{

    @Column(name = "wallet_type")
    private WalletType walletType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "uuid")
    private String uuid;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<WalletBalanceChanged> walletBalanceChanges;
}
