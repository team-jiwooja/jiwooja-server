package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train-seats")
//열차 예약 등록
public class TrainSeatController {

    private final TrainSeatService trainSeatService;

    @Autowired
    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }

    @PostMapping("/add")
    //열차 예약
    //(int carNum, int trainNum, int chk, int trainPrice)
    public ResponseEntity<String> addTrainAndSeats(@RequestBody TrainSeatRequest request) {
        trainSeatService.addTrainAndSeats(request.getCarNum(), request.getSeatList(), request.getTrainNum(), request.getTrainPrice());
        return ResponseEntity.ok("열차정보 및 좌석 등록 성공");
    }

    @PostMapping("/cancel-reservation")
    //열차 예약 취소
    public String cancelReservation(@RequestParam int trainNum, @RequestParam String seatCode) {
        try {
            trainSeatService.cancelReservation(trainNum, seatCode);
            return "{\"status\": \"success\", \"message\": \"예약이 성공적으로 취소되었습니다.\"}";
        } catch (Exception e) {
            return "{\"status\": \"error\", \"message\": \"예약 취소 중 오류가 발생했습니다: " + e.getMessage() + "\"}";
        }
    }

    public static class TrainSeatRequest {
        //carNum(호차 = 칸)
        private int carNum;

        //train_num(420번 ktx)
        private int trainNum;

        //선택한 좌석 이름들 ex)A1, A2
        private List<String> seatList;

        //가격
        private int trainPrice;

        public int getCarNum() {
            return carNum;
        }

        public void setCarNum(int carNum) {
            this.carNum = carNum;
        }

        public int getTrainNum() {
            return trainNum;
        }

        public void setTrainNum(int trainNum) {
            this.trainNum = trainNum;
        }

        public List<String> getSeatList() {
            return seatList;
        }

        public void setSeatList(List<String> seatList) {
            this.seatList = seatList;
        }

        public int getTrainPrice() {
            return trainPrice;
        }

        public void setTrainPrice(int trainPrice) {
            this.trainPrice = trainPrice;
        }
    }
}
