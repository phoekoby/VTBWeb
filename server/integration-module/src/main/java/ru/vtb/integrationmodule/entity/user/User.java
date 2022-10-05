package ru.vtb.integrationmodule.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.integrationmodule.entity.Exchange;
import ru.vtb.integrationmodule.entity.Product;
import ru.vtb.integrationmodule.entity.Wallet;
import ru.vtb.integrationmodule.entity.courses.UserCourse;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private Wallet wallet;


    @OneToMany(mappedBy = "user")
    private List<UserCourse> userCourses;

    @OneToMany(mappedBy = "user")
    private List<Exchange> exchanges;


}
