package com.jiwooja.jiwoojaserver.Byoun.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class TicketDTO {

    @Id
    private String ticketNum;
    private String trainName;
    private int trainNum;
    private int date;
    private String startingSubway;
    private String endingSubway;
    private String seatName;
    private int price;
    private String nickname;

    public TicketDTO(String ticketNum, String trainName, int trainNum, int date,
                     String startingSubway, String endingSubway, String seatName, int price, String nickname) {
        this.ticketNum = ticketNum;
        this.trainName = trainName;
        this.trainNum = trainNum;
        this.date = date;
        this.startingSubway = startingSubway;
        this.endingSubway = endingSubway;
        this.seatName = seatName;
        this.price = price;
        this.nickname = nickname;
    }
}
