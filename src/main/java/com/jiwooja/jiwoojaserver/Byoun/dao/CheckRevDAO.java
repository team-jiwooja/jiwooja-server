package com.jiwooja.jiwoojaserver.Byoun.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Vector;
import java.util.List;

@Repository
public class CheckRevDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Vector<Object>> chkTrain(String userCode, String date) {
        String query = "SELECT * FROM user_info us "
                + "RIGHT JOIN train_ticket tt ON us.usernum_pk = tt.usernum_pk "
                + "RIGHT JOIN seat_table seat ON tt.seat_code = seat.seat_code "
                + "RIGHT JOIN train_table train ON seat.train_code = train.train_code "
                + "RIGHT JOIN train_api api ON train.train_num = api.train_num "
                + "WHERE us.usernum_pk = ? AND api.train_date = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Vector<Object> list = new Vector<>();
            list.add(rs.getString("ticket_num_pk"));
            list.add(rs.getString("train_type"));
            list.add(rs.getString("train_num_name"));
            list.add(rs.getString("train_date"));
            list.add(rs.getString("starting_subway"));
            list.add(rs.getString("ending_subway"));
            list.add(rs.getString("start_time"));
            list.add(rs.getString("end_time"));
            list.add(rs.getString("train_ho_num"));
            list.add(rs.getString("seat_name"));
            return list;
        }, userCode, date);
    }

    public List<String> chkSeat(String trainType, String date, String startingSubway, String endingSubway, String startTime, String endTime) {
        String query = "SELECT * FROM seat_table seat "
                + "RIGHT JOIN train_table train ON seat.train_code = train.train_code "
                + "RIGHT JOIN train_api api ON train.train_num = api.train_num "
                + "WHERE api.train_type = ? AND api.train_date = ? AND "
                + "api.starting_subway = ? AND api.ending_subway = ? AND "
                + "api.start_time = ? AND api.end_time = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("seat_name"), trainType, date, startingSubway, endingSubway, startTime, endTime);
    }
}
