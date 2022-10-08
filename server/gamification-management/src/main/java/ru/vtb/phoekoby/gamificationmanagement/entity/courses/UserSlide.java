package ru.vtb.phoekoby.gamificationmanagement.entity.courses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vtb.phoekoby.gamificationmanagement.entity.BaseEntity;
import ru.vtb.phoekoby.gamificationmanagement.entity.PlayUserAccount;

import javax.persistence.*;

@Entity
@Table(name = "user_slide", schema = "transaction_management")
@Getter
@Setter
@RequiredArgsConstructor
public class UserSlide extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private PlayUserAccount userAccount;

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
