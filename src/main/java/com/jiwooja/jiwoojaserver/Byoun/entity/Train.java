package com.jiwooja.jiwoojaserver.Byoun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "train_table")
@Getter
@Setter
public class Train {
    @Id
    @Column(name = "train_code")
    //열차 train_num + 호차 번호(칸 번호)
    private int trainCode;

    @Column(name = "train_num")
    //ex) 420번 ktx
    private int trainNum;

    @Column(name = "train_ho_num")
    //ex) 1호차~10호차(열차 한 칸)`

    private Integer trainHoNum;

    @Column(name = "train_ho_type")
    //일반실, 특별실
    private String trainHoType;

    @Column(name = "train_seat_qty")
    //좌석수
    private Integer trainSeatQty;
}
