package com.jiwooja.jiwoojaserver.Byoun.controller;

import com.jiwooja.jiwoojaserver.Byoun.service.TrainReservationService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController  // RestController로 변경
public class TrainReservationController {
    private final TrainReservationService trainReservationService;
    private final Logger logger = LoggerFactory.getLogger(TrainReservationController.class);

    public TrainReservationController(TrainReservationService trainReservationService) {
        this.trainReservationService = trainReservationService;
    }

    @GetMapping("/train-reservation")
    public List<List<String>> showSeatSelection(
            @RequestParam("trainType") String trainType,
            @RequestParam("trainDate") String trainDate,
            @RequestParam("startingSubway") String startingSubway,
            @RequestParam("endingSubway") String endingSubway,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {

        List<List<String>> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);

        logger.info("showSeatSelection called with trainType: {}, trainDate: {}, startingSubway: {}, endingSubway: {}, startTime: {}, endTime: {}",
                trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
        logger.info("bookedSeats: {}", bookedSeats);
        return bookedSeats;
    }

    @PostMapping("/train-reservation")
    public List<List<String>> processSeatSelection(@RequestParam("trainType") String trainType,
                                                   @RequestParam("trainDate") String trainDate,
                                                   @RequestParam("startingSubway") String startingSubway,
                                                   @RequestParam("endingSubway") String endingSubway,
                                                   @RequestParam("startTime") String startTime,
                                                   @RequestParam("endTime") String endTime) {

        List<List<String>> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
        logger.info("bookedSeats: {}", bookedSeats);
        return bookedSeats;
    }
}
