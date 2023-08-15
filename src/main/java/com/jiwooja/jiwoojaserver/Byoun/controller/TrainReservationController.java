package com.jiwooja.jiwoojaserver.Byoun.controller;

import com.jiwooja.jiwoojaserver.Byoun.service.TrainReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrainReservationController {
    private final TrainReservationService trainReservationService;

    public TrainReservationController(TrainReservationService trainReservationService) {
        this.trainReservationService = trainReservationService;
    }

    @GetMapping("/train-reservation")
    public String showSeatSelection(Model model,
                                    @RequestParam("trainType") String trainType,
                                    @RequestParam("trainDate") String trainDate,
                                    @RequestParam("startingSubway") String startingSubway,
                                    @RequestParam("endingSubway") String endingSubway,
                                    @RequestParam("startTime") String startTime,
                                    @RequestParam("endTime") String endTime) {

        List<String> stationList = trainReservationService.getStationList();
        List<String> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);

        model.addAttribute("stationList", stationList);
        model.addAttribute("bookedSeats", bookedSeats);

        return "train-reservation";
    }

    // 좌석 선택 처리 엔드포인트
    @PostMapping("/train-reservation")
    public String processSeatSelection(@RequestParam("selectedSeats") List<String> selectedSeats,
                                       @RequestParam("trainType") String trainType,
                                       @RequestParam("trainDate") String trainDate,
                                       @RequestParam("startingSubway") String startingSubway,
                                       @RequestParam("endingSubway") String endingSubway,
                                       @RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime,
                                       Model model) {

        List<String> bookedSeats = trainReservationService.getBookedSeats(trainType, trainDate, startingSubway, endingSubway, startTime, endTime);
        // 선택된 좌석 데이터를 처리하고 및 예약 성공 페이지로 이동하거나 예외 처리
        model.addAttribute("reservationResult", "예약이 성공적으로 완료되었습니다.");
        return "reservation-success";
    }
}
