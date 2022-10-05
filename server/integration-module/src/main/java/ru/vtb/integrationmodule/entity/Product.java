package ru.vtb.integrationmodule.entity;


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

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @Column(name = "name")
    private String name;

    @Column(name = "pictureUrl")
    private String pictureUrl;

    @Column(name = "cost")
    private Double cost;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;
}