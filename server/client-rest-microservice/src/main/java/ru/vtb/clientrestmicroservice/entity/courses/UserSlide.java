package ru.vtb.clientrestmicroservice.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.clientrestmicroservice.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserSlide extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

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
