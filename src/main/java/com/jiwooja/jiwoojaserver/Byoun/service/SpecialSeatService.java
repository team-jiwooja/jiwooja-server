package com.jiwooja.jiwoojaserver.Byoun.service;

import com.jiwooja.jiwoojaserver.Byoun.dao.SeatDAO;
import com.jiwooja.jiwoojaserver.Byoun.entity.Train;
import com.jiwooja.jiwoojaserver.Byoun.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialSeatService {
    private final TrainRepository trainRepository;
    @Autowired
    private final SeatDAO seatDAO;

    public SpecialSeatService(TrainRepository trainRepository, SeatDAO seatDAO) {
        this.trainRepository = trainRepository;
        this.seatDAO = seatDAO;
    }

    public void selectSeat(String seatNumber) {
        // 선택한 좌석 정보를 DB에 저장
        seatDAO.reserveSeat(seatNumber);
    }

    public void resetSeat(String carNumber) {
        //예약된 좌석 목록을 바탕으로 좌석 초기화
        List<String> bookedSeats = seatDAO.getBookedSeatsByCarNumber(carNumber);
        for (String seatNumber : bookedSeats) {
            seatDAO.releaseSeat(seatNumber);
        }
    }

    public List<String> getTrainList() {
        List<Train> trains = trainRepository.findAll();
        List<String> trainList = new ArrayList<>();
        for (Train train : trains) {
            trainList.add(train.getTrainNum());
        }
        return trainList;
    }
}
