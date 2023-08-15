package com.jiwooja.jiwoojaserver.Byoun.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TrainAPIService {

    private static final Logger logger = LoggerFactory.getLogger(TrainAPIService.class);

    private final RestTemplate restTemplate;

    @Value("${trainapi.key}")
    private String apiKey;

    @Value("${trainapi.base-url}")
    private String baseUrl;

    public TrainAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JSONArray getTrainAPI(String stSub, String enSub, String date, String trainNm) {
        String url = baseUrl + "?serviceKey=" + apiKey;
        url += "&pageNo=1&numOfRows=100&_type=json";
        url += "&depPlaceId=" + stSub + "&arrPlaceId=" + enSub;
        url += "&depPlandTime=" + date + "&trainGradeCode=" + trainNm;

        try {
            JSONObject response = restTemplate.getForObject(url, JSONObject.class);
            if (response != null) {
                JSONObject body = (JSONObject) response.get("body");
                JSONObject items = (JSONObject) body.get("items");
                JSONArray array = (JSONArray) items.get("item");
                return array;
            }
        } catch (RestClientException e) {
            logger.error("Error fetching train data", e);
        }

        return null;
    }
}
