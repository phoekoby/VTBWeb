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
public class Product extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

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
