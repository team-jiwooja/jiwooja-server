package com.jiwooja.jiwoojaserver.Byoun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "train")
@Getter
@Setter
public class Train {

    @Id
    @Column(name = "train_code")
    private String trainCode;

    @Column(name = "train_num")
    private String trainNum;

    @Column(name = "train_ho_num")
    private Integer trainHoNum;

    @Column(name = "train_ho_type")
    private String trainHoType;

    @Column(name = "train_seat_qty")
    private Integer trainSeatQty;
}
