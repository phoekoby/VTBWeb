package ru.vtb.serverrpcmicroservice.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product", schema = "market_management")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity implements Serializable {
    @Column(name = "owner_user_id")
    private Long ownerUserId;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Cost cost;
}
