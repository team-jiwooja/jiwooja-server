package com.jiwooja.jiwoojaserver.Byoun.controller;

import com.jiwooja.jiwoojaserver.Byoun.service.TrainReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Controller
public class TrainReservationController {
    private final TrainReservationService trainReservationService;
    private final Logger logger = LoggerFactory.getLogger(TrainReservationController.class);

    public TrainReservationController(TrainReservationService trainReservationService) {
        this.trainReservationService = trainReservationService;
    }

    @GetMapping("/train-reservation")
    //기차 예약페이지를 보여줌
    public String showSeatSelection(Model model,
                                    @RequestParam("trainType") String trainType,
                                    @RequestParam("trainDate") String trainDate,
                                    @RequestParam("startingSubway") String startingSubway,
                                    @RequestParam("endingSubway") String endingSubway,
                                    @RequestParam("startTime") String startTime,
                                    @RequestParam("endTime") String endTime) {

        List<String> stationList = trainReservationService.getStationList();
        //예약 정보를 가져옴
        List<List<String>> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);

        model.addAttribute("stationList", stationList);
        model.addAttribute("bookedSeats", bookedSeats);
        logger.info("showSeatSelection called with trainType: {}, trainDate: {}, startingSubway: {}, endingSubway: {}, startTime: {}, endTime: {}",
                trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
        logger.info("stationList: {}", stationList);
        logger.info("bookedSeats: {}", bookedSeats);
        return "train-reservation";
    }

    // 좌석 선택 처리
    @PostMapping("/train-reservation")
    public String processSeatSelection(@RequestParam("trainType") String trainType,
                                       @RequestParam("trainDate") String trainDate,
                                       @RequestParam("startingSubway") String startingSubway,
                                       @RequestParam("endingSubway") String endingSubway,
                                       @RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime,
                                       Model model) {

        List<List<String>> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
        // 선택된 좌석 데이터를 처리하고 및 예약 성공 페이지로 이동하거나 예외 처리
        model.addAttribute("reservationResult", "예약이 성공적으로 완료되었습니다.");
        logger.info("bookedSeats: {}", bookedSeats);
        return "reservation-success";
    }
}
