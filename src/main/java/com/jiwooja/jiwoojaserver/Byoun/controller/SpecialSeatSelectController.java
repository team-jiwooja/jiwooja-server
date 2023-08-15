package com.jiwooja.jiwoojaserver.Byoun.controller;

import com.jiwooja.jiwoojaserver.Byoun.entity.Train;
import com.jiwooja.jiwoojaserver.Byoun.repository.TrainRepository;
import com.jiwooja.jiwoojaserver.Byoun.service.SpecialSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SpecialSeatSelectController {

    private final SpecialSeatService specialSeatService;
    private final TrainRepository trainRepository;

    @Autowired
    public SpecialSeatSelectController(
            SpecialSeatService specialSeatService,
            TrainRepository trainRepository) {
        this.specialSeatService = specialSeatService;
        this.trainRepository = trainRepository;
    }

    @GetMapping("/special-seat")
    public String showSpecialSeatPage(Model model) {
        // SpecialSeatService를 통해 열차 정보를 가져옴
        List<String> trainList = specialSeatService.getTrainList();
        model.addAttribute("trainList", trainList);

        List<Train> trains = getTrains(); // 엔티티 조회
        model.addAttribute("trains", trains);

        return "special-seat";
    }

    // 열차 정보 조회 메서드
    private List<Train> getTrains() {
        return trainRepository.findAll();
    }
    @PostMapping("/select-seat")
    public String selectSeat(@RequestParam String seatNumber, Model model) {
        // 좌석 선택
        specialSeatService.selectSeat(seatNumber);
        return "redirect:/special-seat";
    }

    @PostMapping("/reset-seat")
    public String resetSeat(@RequestParam String carNumber, Model model) {
        // 좌석 초기화
        specialSeatService.resetSeat(carNumber);
        return "redirect:/special-seat";
    }
}
