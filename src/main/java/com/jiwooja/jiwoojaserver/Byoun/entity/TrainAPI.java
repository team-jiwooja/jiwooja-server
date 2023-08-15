package com.jiwooja.jiwoojaserver.Byoun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "trainAPI")
@Getter
@Setter
public class TrainAPI {
    @Id
    @Column(name = "train_num")
    private String trainNum;

    @Column(name = "train_type")
    private String trainType;

    @Column(name = "train_date")
    private Date trainDate;

    @Column(name = "starting_subway")
    private String startingSubway;

    @Column(name = "ending_subway")
    private String endingSubway;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "price")
    private Double price;

}
