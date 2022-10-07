package ru.vtb.phoekoby.client.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


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
