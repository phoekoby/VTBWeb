package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;
import ru.vtb.clientrestmicroservice.entity.UserAccount;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user_course", schema = "transaction_management")
@Getter
@Setter
@RequiredArgsConstructor
public class UserCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userId;

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
