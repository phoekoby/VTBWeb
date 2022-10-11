package ru.vtb.phoekoby.gamificationmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "play_user_account", schema = "gamification_management")
@Entity
public class PlayUserAccount extends BaseEntity{
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "daily_multiply")
    private Double dailyMultiply;

    @Column(name = "day_streak")
    private Integer dayStreak;

    @Column(name = "current_level")
    private Integer currentLevel;

    @Column(name = "has_experience")
    private Double currentExperience;

    @Column(name = "need_exp_for_next_level")
    private Double needExperienceForNextLevel;

    @OneToMany
    private List<UserProduct> userProducts;
}
