package ru.vtb.phoekoby.gamificationmanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Collection;

@Table(name = "user_product", schema = "gamification_management")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserProduct extends BaseEntity{
    @Column(name = "product_id")
    private Long products;

    @Column(name = "play_user_account_id")
    private Long play_user_account;
}
