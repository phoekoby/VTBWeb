package ru.vtb.serverrpcmicroservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "category", schema = "market_management")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            schema = "market_management",
            joinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"))
    private Collection<Product> products;
}
