package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;
import ru.vtb.clientrestmicroservice.entity.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserSlide extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "slide_id")
    private Slide slide;

    @Column(name = "slide_user_status")
    private SlideStatus slideStatus;

    @ManyToOne
    @JoinColumn(name = "user_course_id")
    private UserCourse userCourse;
}
