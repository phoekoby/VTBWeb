package ru.vtb.clientrestmicroservice.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends BaseEntity{

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
    private Integer cost;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;
}
