package ru.vtb.phoekoby.gamificationmanagement.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.phoekoby.gamificationmanagement.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "slide", schema = "transaction_management")
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
            schema = "transaction_management",
            joinColumns = @JoinColumn(name = "slide_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private List<Picture> pictureList;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "ord")
    private Integer order;
}
