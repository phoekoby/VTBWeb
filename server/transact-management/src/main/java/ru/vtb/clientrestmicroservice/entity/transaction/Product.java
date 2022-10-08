package ru.vtb.clientrestmicroservice.entity.transaction;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;
import ru.vtb.clientrestmicroservice.entity.courses.Picture;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "product", schema = "transaction_management")
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "owner_user_id")
    private Long ownerUserId;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "product_pictures",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private List<Picture> pictures;

    @Column(name = "cost")
    private Double cost;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;
}
