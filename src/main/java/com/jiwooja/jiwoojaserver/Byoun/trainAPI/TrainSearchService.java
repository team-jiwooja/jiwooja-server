package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainSearchService {

    private final TrainAPIService trainAPIService;

    @Autowired
    public TrainSearchService(TrainAPIService trainAPIService) {
        this.trainAPIService = trainAPIService;
    }

    public List<TrainInfo> searchTrain(String stSub, String enSub, String date, String trainNm) {
        JSONArray responseArray = trainAPIService.getTrainAPI(stSub, enSub, date, trainNm);
        System.out.println("API 응답: " + responseArray.toJSONString());

        if (responseArray == null || responseArray.isEmpty()) {
            return new ArrayList<>();
        }

        List<TrainInfo> trainInfos = new ArrayList<>();

        for (int i = 0; i < responseArray.size(); i++) {
            JSONObject object = (JSONObject) responseArray.get(i);
            TrainInfo trainInfo = new TrainInfo();

            LocalDateTime st_date = LocalDateTime.parse(String.valueOf(object.get("depplandtime")), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            LocalDateTime en_date = LocalDateTime.parse(String.valueOf(object.get("arrplandtime")), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            Duration diff = Duration.between(st_date.toLocalTime(), en_date.toLocalTime());
            long el_hour;
            long el_min;

            if (diff.toHours() < 0 && diff.toMinutes() < 0) {
                el_hour = diff.toHours() + 24;
                el_min = Math.abs(diff.toMinutes() % 60);
            } else {
                el_hour = diff.toHours();
                el_min = Math.abs(diff.toMinutes() % 60 - 60);
            }

            String eltime = el_hour + "시간" + el_min + "분";
            int quality_up = (int) (Integer.parseInt(String.valueOf(object.get("adultcharge"))) * 1.2);

            trainInfo.setTrainGradeName((String) object.get("traingradename"));
            trainInfo.setTrainNo(String.valueOf(object.get("trainno")));
            trainInfo.setDepartureTime(st_date);
            trainInfo.setArrivalTime(en_date);
            trainInfo.setElapsedTime(eltime);
            trainInfo.setSpecialSeatPrice(quality_up);
            trainInfo.setRegularSeatPrice(Integer.parseInt(String.valueOf(object.get("adultcharge"))));

            trainInfos.add(trainInfo);
        }

        return trainInfos;
    }
}

