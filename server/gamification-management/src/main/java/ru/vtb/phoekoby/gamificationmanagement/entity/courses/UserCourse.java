package ru.vtb.phoekoby.gamificationmanagement.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.phoekoby.gamificationmanagement.entity.BaseEntity;
import ru.vtb.phoekoby.gamificationmanagement.entity.PlayUserAccount;
import ru.vtb.phoekoby.gamificationmanagement.entity.enumiration.CourseStatus;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user_course", schema = "gamification_management")
@Getter
@Setter
@RequiredArgsConstructor
public class UserCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private PlayUserAccount userAccount;

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
