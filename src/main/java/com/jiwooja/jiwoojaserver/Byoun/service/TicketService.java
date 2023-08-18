package com.jiwooja.jiwoojaserver.Byoun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class TicketService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertTicket(String trainName, int trainNum, int trainHoNum, int date,
                             String startingSubway, String endingSubway, String seatName,
                             int price, String nickname) {
        String query = "INSERT INTO tickets (train_name, train_num, train_ho_num, date, starting_subway, ending_subway, seat_name, price, nickname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(query, trainName, trainNum, trainHoNum, date, startingSubway, endingSubway, seatName, price, nickname);
    }
}
