package com.jiwooja.jiwoojaserver.Byoun.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class TicketTrainDTO {

    @Id
    private String name;
    private String num;
    private String stSub;
    private String enSub;
    private String date;
    private String stTime;
    private String enTime;
    private String trainHo;
    private String seat;
}