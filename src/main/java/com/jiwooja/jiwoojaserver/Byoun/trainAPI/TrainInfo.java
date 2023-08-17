package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Train_API_Info")
@Getter
@Setter
public class TrainInfo {
    @Id
    private int train_code;

    //열차 종류 ex)ktx
    private String trainGradeName;

    //ex) 420번 ktx
    private String trainNo;
    //출발시각, 도착시각
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    
    //소요시간
    private String elapsedTime;
    
    //가격
    private int specialSeatPrice;
    private int regularSeatPrice;
}
