package ru.vtb.serverrpcmicroservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "product", schema = "market_management")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "cost_id")
    private Cost cost;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            schema = "market_management",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"))
    private Collection<Category> categories;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "product_pictures",
            schema = "market_management",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id")
    )
    private Collection<Picture> pictures;
}
