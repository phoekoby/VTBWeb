package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserCourse extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "course_user_status")
    @Enumerated(EnumType.STRING)
    private CourseStatus taskStatus;

    @Column
    @OneToMany(mappedBy = "userCourse")
    private List<UserSlide> userSlideList;
}
