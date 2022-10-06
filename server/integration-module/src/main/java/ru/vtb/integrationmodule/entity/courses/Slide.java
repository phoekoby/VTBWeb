package ru.vtb.integrationmodule.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.integrationmodule.entity.BaseEntity;
import ru.vtb.integrationmodule.entity.Picture;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Slide extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @ManyToMany
    @JoinTable(name = "slides_pictures",
    joinColumns = @JoinColumn(name = "slide_id"),
    inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private List<Picture> pictureList;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "order")
    private Integer order;

    @Column(name = "slide_type")
    @Enumerated(EnumType.STRING)
    private SlideType slideType;

    //нужно ли?
    @Column(name = "slide_prize")
    private Double slidePrize;
}
