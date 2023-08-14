package com.jiwooja.jiwoojaserver.Byoun.service;

import com.jiwooja.jiwoojaserver.Byoun.dao.SeatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainReservationService {

    private final SeatDAO seatDAO;

    @Autowired
    public TrainReservationService(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    public List<String> getStationList() {
        List<String> stations = new ArrayList<>(SubwayCode.sub_map.keySet());
        return stations;
    }

    public List<String> getBookedSeats(String trainType, String trainDate, String startingSubway, String endingSubway, String startTime, String endTime) {
        // SeatDAO를 통해 예약된 좌석 정보를 가져옴
        return seatDAO.getBookedSeatsByTrainTypeAndTime(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
    }
    public static class SubwayCode {
        public static Map<String, String> sub_map = new HashMap<>();

        static {
            //역 이름 다 들어간다
            sub_map.put("상봉", "NAT020040");
            sub_map.put("서빙고", "NAT130036");
            sub_map.put("진해", "NAT810195");
        }
    }
}
