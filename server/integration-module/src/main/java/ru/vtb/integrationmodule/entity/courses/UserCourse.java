package ru.vtb.integrationmodule.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.integrationmodule.entity.BaseEntity;
import ru.vtb.integrationmodule.entity.user.User;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserCourse extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "course_user_status")
    private CourseStatus taskStatus;

    @Column
    @OneToMany(mappedBy = "userCourse")
    private List<UserSlide> userSlideList;
}
