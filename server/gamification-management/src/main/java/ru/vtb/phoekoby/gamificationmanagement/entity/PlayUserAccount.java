package ru.vtb.phoekoby.gamificationmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PlayUserAccount extends BaseEntity{
    @Column(name = "daily_multiply")
    private Double dailyMultiply;
}
