package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "train_seat_table")
public class TrainSeat {

    @Id
    @Column(name = "seat_code", unique = true)
    //열차 train_num + 호차 번호(칸 번호)
    private String seatCode;

    @Column(name = "train_code")
    //train_num과 동일
    private Integer trainCode;

    @Column(name = "seat_name")
    //호 번호(1~10)
    private String seatName;

    @Column(name = "seat_type")
    //특별석, 일반석
    private String seatType;

    @Column(name = "seat_price")
    //남은 좌석 수
    private Integer seatPrice;
}
