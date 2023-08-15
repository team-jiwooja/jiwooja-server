package com.jiwooja.jiwoojaserver.Byoun.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "train_seat")
@Getter
@Setter
@Entity
public class TrainSeat {
    @Id
    @Column(name = "seat_code")
    private String seatCode;

    @ManyToOne
    @JoinColumn(name = "train_code")
    private Train train;

    @Column(name = "seat_name")
    private String seatName;

    @Column(name = "seat_type")
    private String seatType;

    @Column(name = "seat_price")
    private Double seatPrice;
}
