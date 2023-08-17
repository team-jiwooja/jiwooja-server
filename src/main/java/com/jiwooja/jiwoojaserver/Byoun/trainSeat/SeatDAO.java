package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SeatDAO {

    private final JdbcTemplate jdbcTemplate;

    public SeatDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //예약된 정보 가져오기
   /* public List<List<String>> getBookedSeats(String trainType, String trainDate, String startingSubway,
                                             String endingSubway, String startTime, String endTime) {

        String query = "SELECT train_ho_num, seat_name FROM train_seat_table seat "
                + "RIGHT JOIN train_table train ON seat.train_code = train.train_code "
                + "RIGHT JOIN train_api api ON train.train_num = api.train_num "
                + "WHERE api.train_type = ? AND api.train_date = ? AND "
                + "api.starting_subway = ? AND api.ending_subway = ? AND "
                + "api.start_time = ? AND api.end_time = ?";

        List<List<String>> allBookedSeats = new ArrayList<>();

        jdbcTemplate.query(query, rs -> {
            String trainHoNum = rs.getString("train_ho_num");
            String seatName = rs.getString("seat_name");

            int hoNum = Integer.parseInt(trainHoNum);

            while (allBookedSeats.size() <= hoNum) {
                allBookedSeats.add(new ArrayList<>());
            }
            allBookedSeats.get(hoNum - 1).add(seatName);
        }, trainType, trainDate, startingSubway, endingSubway, startTime, endTime);

        return allBookedSeats;
    }*/

    //예약된 좌석 정보를 list로 train_code와 seat_name을 목록으로 가져옴
    public List<List<String>> getBookedSeats(String trainType, String trainDate, String startingSubway,
                                             String endingSubway, String startTime, String endTime) {

        String query = "SELECT seat.train_code, seat_name FROM train_seat_table seat "
                + "RIGHT JOIN train_table train ON seat.train_code = train.train_code "
                + "RIGHT JOIN train_api api ON train.train_num = api.train_num "
                + "WHERE api.train_type = ? AND api.train_date = ? AND "
                + "api.starting_subway = ? AND api.ending_subway = ? AND "
                + "api.start_time = ? AND api.end_time = ?";

        List<List<String>> allBookedSeats = new ArrayList<>();

        jdbcTemplate.query(query, rs -> {
            String trainCode = rs.getString("train_code");
            String seatName = rs.getString("seat_name");

            String trainHoNum;

            if (trainCode.charAt(trainCode.length() - 2) == '0') {
                trainHoNum = trainCode.substring(trainCode.length() - 2); // 맨 마지막 숫자만 추출
            } else {
                trainHoNum = trainCode.substring(trainCode.length() - 1); // 뒤에서 두 자리 추출
            }

            int hoNum = Integer.parseInt(trainHoNum);

            while (allBookedSeats.size() < 10) {
                allBookedSeats.add(new ArrayList<>());
            }
            allBookedSeats.get(hoNum - 1).add(seatName);
        }, trainType, trainDate, startingSubway, endingSubway, startTime, endTime);

        return allBookedSeats;
    }

    //조건에 맞는 train_seat_qty와 train_ho_num를 가져옴
    public String[] getTrainName(String trainDate, String startingSubway,
                                 String endingSubway, String startTime, String endTime) {

        String[] seatNum = new String[10];
        String query = "select train_seat_qty, train_ho_num from train_table t, train_api a "
                + "where t.train_num = a.train_num "
                + "and a.train_date = ? and a.starting_subway = ? and a.ending_subway = ? "
                + "and a.start_time = ? and a.end_time = ? ";

        jdbcTemplate.query(query, rs -> {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            for (int i = 0; i < seatNum.length; i++) {
                if (rs.getString("train_ho_num").equals("" + (i + 1))) {
                    seatNum[i] = rs.getString(1); // 잔여 좌석수
                }
            }
        }, trainDate, startingSubway, endingSubway, startTime, endTime);

        System.out.println("그만하자 이제 :" + Arrays.toString(seatNum));
        return seatNum;
    }
}
