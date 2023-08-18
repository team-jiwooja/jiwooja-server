package com.jiwooja.jiwoojaserver.Byoun.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class TicketDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketNum;
    private String trainName; // ex) ktx
    private int trainNum; // ex) 420번 ktx
    private int trainHoNum;
    private int date;
    private String startingSubway;
    private String endingSubway;
    private String seatName;
    private int price;
    //사용자 이름
    private String nickname;

    public TicketDTO(int ticketNum, String trainName, int trainNum, int trainHoNum, int date,
                     String startingSubway, String endingSubway, String seatName, int price, String nickname) {
        this.ticketNum = ticketNum;
        this.trainName = trainName;
        this.trainNum = trainNum;
        this.trainHoNum = trainHoNum;
        this.date = date;
        this.startingSubway = startingSubway;
        this.endingSubway = endingSubway;
        this.seatName = seatName;
        this.price = price;
        this.nickname = nickname;
    }
}
