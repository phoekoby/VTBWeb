package ru.vtb.serverrpcmicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;


    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
}
