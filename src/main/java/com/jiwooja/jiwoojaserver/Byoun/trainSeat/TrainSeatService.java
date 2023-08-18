package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import com.jiwooja.jiwoojaserver.Byoun.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TrainSeatService {

    @Autowired
    private TrainSeatDao trainSeatDao;
    //private List<String> seatList = new ArrayList<>();
    @Transactional
    public void addTrainAndSeats(int carNum, List<String> seatList, int trainNum, int trainPrice) {

        String hoNum = String.valueOf(carNum);
        String hoType;
        String hoQty;

        // 열차 train_num + 호차번호(칸 번호)
        String trainCodeString = String.valueOf(trainNum) + String.valueOf(carNum);

        // 문자열을 int로 다시 변환
        int trainCode = Integer.parseInt(trainCodeString);

        if (carNum < 4) {
            hoType = "specialSeat";
            hoQty = "30";
        } else {
            hoType = "standardSeat";
            hoQty = "40";
        }

        Train existingTrain = trainSeatDao.chkTrainTable(trainNum, trainCode, hoNum, hoType);

        if (trainSeatDao.countByTrainCode(trainCode).compareTo(BigInteger.ZERO) == 0) {
            // train_code가 존재하지 않을 때
            trainSeatDao.setTrainHo(trainNum, trainCode, hoNum, hoType, hoQty);
        } else {
            // train_code가 이미 존재할 때
            int decrementValue = seatList.size() / 2;
            trainSeatDao.decreaseSeatQtyByTrainCode(trainCode, decrementValue);
        }

        String seatCodeString;
        for (String sn : seatList) {
            String seatType;
            if (Arrays.asList("1A", "1B", "1C", "1D").contains(sn) && (carNum == 1 || carNum == 4)) {
                seatType = "wheel";
            } else {
                seatType = "normal";
            }

            // 호차정보 + 좌석이름
            seatCodeString = trainCodeString + sn;

            trainSeatDao.setSeat(trainCode, seatCodeString, sn, seatType, trainPrice);
            trainSeatDao.setSeatDown(trainCode);
        }
    }

    public void cancelReservation(int trainNum, String seatCode) {
        // 좌석 수량을 증가
        trainSeatDao.increaseSeatQty(trainNum);
        // 해당 티켓 번호에 연결된 좌석을 삭제
        trainSeatDao.deleteSeatByTicketNum(seatCode);
    }
}
