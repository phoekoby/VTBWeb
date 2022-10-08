package ru.vtb.serverrpcmicroservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cost", schema = "market_management")
@Getter
@Setter
@NoArgsConstructor
public class Cost extends BaseEntity implements Serializable {

    @Column(name = "ruble")
    private Double ruble;

    @Column(name = "matic")
    private Double matic;

    @Column(name = "nft")
    private Integer nft;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public Cost(Double ruble, Double matic, Integer nft) {
        this.ruble = ruble;
        this.matic = matic;
        this.nft = nft;
    }
}
