package ru.vtb.clientrestmicroservice.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;
import ru.vtb.clientrestmicroservice.entity.Product;
import ru.vtb.clientrestmicroservice.entity.Purchase;
import ru.vtb.clientrestmicroservice.entity.Wallet;
import ru.vtb.clientrestmicroservice.entity.courses.UserCourse;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Product> products;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets;


    @OneToMany(mappedBy = "user")
    private List<UserCourse> myCourses;

    @OneToMany(mappedBy = "buyerUser")
    private List<Purchase> myPurchases;

    @OneToMany(mappedBy = "prevOwnerUser")
    private List<Purchase> mySales;

    @ManyToMany
    @JoinTable(name = "user_mentors",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "mentor_id"))
    private List<User> myMentors;

    @ManyToMany
    @JoinTable(name = "user_mentors",
            joinColumns = @JoinColumn(name = "mentor_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> myMentorings;

    @ManyToOne
    @JoinColumn(name = "supervisor_user_id")
    private User supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<User> subordinates;


}
