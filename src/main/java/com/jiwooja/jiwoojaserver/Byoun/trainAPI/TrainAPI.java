package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//API에서 받아온 데이터

@Getter
@Setter
@Entity
@Table(name = "train_API")
public class TrainAPI {
    @Id
    @Column(name = "train_num")
    private int trainNum;


    @Column(name = "train_type")
    private String trainType;

    @Column(name = "train_date")
    private String trainDate;

    @Column(name = "starting_subway")
    private String startingSubway;

    @Column(name = "ending_subway")
    private String endingSubway;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "price")
    private int price;

}
