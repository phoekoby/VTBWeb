package ru.vtb.integrationmodule.entity.courses;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.integrationmodule.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Course extends BaseEntity {

    @OneToMany(mappedBy = "content")
    private List<Slide> slides;

    @Column(name = "order")
    private Integer order;

    @Column(name = "course_prize")
    private Double coursePrise;
}
