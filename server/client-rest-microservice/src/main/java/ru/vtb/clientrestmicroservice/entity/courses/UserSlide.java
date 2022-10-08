package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;
import ru.vtb.clientrestmicroservice.entity.UserAccount;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserSlide extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OneToOne
    @JoinColumn(name = "slide_id")
    private Slide slide;

    @Column(name = "slide_user_status")
    @Enumerated(EnumType.STRING)
    private SlideStatus slideStatus;

    @ManyToOne
    @JoinColumn(name = "user_course_id")
    private UserCourse userCourse;

}
