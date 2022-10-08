package ru.vtb.serverrpcmicroservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "picture", schema = "market_management")
@Getter
@Setter
@NoArgsConstructor
public class Picture extends BaseEntity implements Serializable {
    @Column(name = "url")
    private String url;

    @ManyToMany
    @JoinTable(
            name = "product_pictures",
            schema = "market_management",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id")
    )
    private Collection<Product> products;
}
