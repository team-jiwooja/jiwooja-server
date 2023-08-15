package com.jiwooja.jiwoojaserver.Byoun.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatDAO {

    private final JdbcTemplate jdbcTemplate;

    public SeatDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getBookedSeatsByTrainTypeAndTime(String trainType, String trainDate, String startingSubway,
                                                         String endingSubway, String startTime, String endTime) {
        String query = "SELECT seat.seatName " +
                "FROM train_Seat seat " +
                "RIGHT JOIN train_table train ON seat.train_code = train.train_code " +
                "RIGHT JOIN train_api api ON train.train_num = api.train_num " +
                "WHERE api.train_type = ? AND api.train_date = ? AND " +
                "api.starting_subway = ? AND api.ending_subway = ? AND " +
                "api.start_time = ? AND api.end_time = ?";

        return jdbcTemplate.queryForList(query, String.class, trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
    }

    public String[] getRemainingSeatsByTrainInfo(String trainDate, String startingSubway, String endingSubway,
                                                 String startTime, String endTime) {
        String query = "SELECT train_seat_qty, train_ho_num " +
                "FROM train_table t, train_api a " +
                "WHERE t.train_num = a.train_num " +
                "AND a.train_date = ? AND a.starting_subway = ? AND a.ending_subway = ? " +
                "AND a.start_time = ? AND a.end_time = ?";

        String[] seatNums = new String[10];
        for (int i = 0; i < seatNums.length; i++) {
            seatNums[i] = null;
        }

        jdbcTemplate.query(query, resultSet -> {
            while (resultSet.next()) {
                int trainHoNum = resultSet.getInt("train_ho_num");
                int remainingSeats = resultSet.getInt("train_seat_qty");
                seatNums[trainHoNum - 1] = String.valueOf(remainingSeats);
            }
        }, trainDate, startingSubway, endingSubway, startTime, endTime);

        return seatNums;
    }

    public void reserveSeat(String seatNumber) {
        String query = "UPDATE train_Seat SET status = 'RESERVED' WHERE seatName = ?";
        jdbcTemplate.update(query, seatNumber);
    }

    public void releaseSeat(String seatNumber) {
        String query = "UPDATE train_Seat SET status = 'AVAILABLE' WHERE seatName = ?";
        jdbcTemplate.update(query, seatNumber);
    }

    public List<String> getBookedSeatsByCarNumber(String carNumber) {
        String query = "SELECT seatName FROM train_Seat WHERE train_code = ? AND status = 'RESERVED'";
        return jdbcTemplate.queryForList(query, String.class, carNumber);
    }
}
