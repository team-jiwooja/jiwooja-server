package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrainSearchController {

    private final TrainSearchService trainSearchService;

    @Autowired
    public TrainSearchController(TrainSearchService trainSearchService) {
        this.trainSearchService = trainSearchService;
    }

    // 열차 검색 API
    //search/train?stSub=NAT010000&enSub=NAT011668&date=20230819&trainNm=00
    //http://localhost:8080/search/train?stSub=NAT010000&enSub=NAT011668&date=20230819&trainNm=00
    @GetMapping("/search/train")
    public ResponseEntity<List<TrainInfo>> searchTrain(
            @RequestParam String stSub,
            @RequestParam String enSub,
            @RequestParam String date,
            @RequestParam String trainNm) {

        List<TrainInfo> trainInfos = trainSearchService.searchTrain(stSub, enSub, date, trainNm);
        return ResponseEntity.ok(trainInfos);
    }

}
