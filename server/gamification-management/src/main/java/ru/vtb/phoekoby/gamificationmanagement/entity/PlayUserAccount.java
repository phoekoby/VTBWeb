package ru.vtb.phoekoby.gamificationmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "play_user_account", schema = "gamification_management")
@Entity
public class PlayUserAccount extends BaseEntity{
    @Column(name = "daily_multiply")
    private Double dailyMultiply;

    @Column(name = "day_streak")
    private Integer dayStreak;
}
