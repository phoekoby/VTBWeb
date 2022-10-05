package ru.vtb.integreationmodule.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Privilege extends BaseEntity{


    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
