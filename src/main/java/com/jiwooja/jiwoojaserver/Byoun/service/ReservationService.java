package com.jiwooja.jiwoojaserver.Byoun.service;

import com.jiwooja.jiwoojaserver.Byoun.trainSeat.SeatDAO;
import com.jiwooja.jiwoojaserver.Byoun.trainAPI.TrainAPI;
import com.jiwooja.jiwoojaserver.Byoun.trainAPI.TrainAPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private SeatDAO seatDAO;
    @Autowired
    private TrainAPIRepository trainAPIRepository;
    public TrainAPI getTrainByNum(String trainNum) {
        Optional<TrainAPI> optionalTrain = trainAPIRepository.findByTrainNum(trainNum);
        if (optionalTrain.isPresent()) {
            return optionalTrain.get();
        } else {
            throw new RuntimeException("Train with number " + trainNum + " not found");
        }
    }


    public List<List<String>> reserveSpecialSeat(String trainType, String trainDate, String startingSubway,
                                           String endingSubway, String startTime, String endTime) {
        // 예약된 좌석 정보 가져오기
        List<List<String>> bookedSeats = seatDAO.getBookedSeats(trainType, trainDate, startingSubway,
                endingSubway, startTime, endTime);

        return bookedSeats;
    }

    public List<List<String>> reserveStandardSeat(String trainType, String trainDate, String startingSubway,
                                            String endingSubway, String startTime, String endTime) {
        // 예약된 좌석 정보 가져오기
        List<List<String>> bookedSeats = seatDAO.getBookedSeats(trainType, trainDate, startingSubway,
                endingSubway, startTime, endTime);

        return bookedSeats;
    }
}
