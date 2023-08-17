package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainAPIService {

    private static final Logger logger = LoggerFactory.getLogger(TrainAPIService.class);

    private final RestTemplate restTemplate;
    private final TrainAPIRepository trainAPIRepository;
    @Autowired
    public TrainAPIService(TrainAPIRepository trainAPIRepository) {
        this.trainAPIRepository = trainAPIRepository;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
    }

    @Value("${trainapi.key}")
    private String apiKey;

    @Value("${trainapi.base-url}")
    private String baseUrl;


    public JSONArray getTrainAPI(String stSub, String enSub, String date, String trainNm) {
        //해당 url로 api요청
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "100")
                .queryParam("_type", "json")
                .queryParam("depPlaceId", stSub)
                .queryParam("arrPlaceId", enSub)
                .queryParam("depPlandTime", date)
                .queryParam("trainGradeCode", trainNm)
                .build(true); 

        String url = uriComponents.toUriString();
        logger.info("Sending request to URL: " + url);
        //{"response":{"header":{"resultCode":"00","resultMsg":"NORMAL SERVICE."},"body":{"items":{"item":[{
        try {
            String responseStr = restTemplate.getForObject(uriComponents.toUri(), String.class);
            logger.info("API Response: " + responseStr);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray array = (JSONArray) items.get("item");

            // train_API table에 저장
            saveToDatabase(array);

            return array;
        } catch (RestClientException e) {
            logger.error("Error trainAPI", e);
        } catch (ParseException e) {
            logger.error("Error parsing JSON", e);
        }

        return new JSONArray();
    }

    private void saveToDatabase(JSONArray array) {
        List<TrainAPI> trainAPIList = new ArrayList<>();
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            TrainAPI trainAPI = new TrainAPI();

            Long trainNumLong = (Long) jsonObject.get("trainno");
            int trainNumInt = Integer.parseInt(trainNumLong.toString());
            trainAPI.setTrainNum(trainNumInt);

            trainAPI.setTrainType((String) jsonObject.get("traingradename"));
            trainAPI.setStartingSubway((String) jsonObject.get("depplacename"));
            trainAPI.setEndingSubway((String) jsonObject.get("arrplacename"));

            String depDateTime = String.valueOf(jsonObject.get("depplandtime"));
            String arrDateTime = String.valueOf(jsonObject.get("arrplandtime"));

            String depTime = extractTimeFromDateTime(depDateTime);
            String arrTime = extractTimeFromDateTime(arrDateTime);
            String depDateStr = extractDateFromDateTimeAsString(depDateTime);

            trainAPI.setStartTime(depTime);
            trainAPI.setEndTime(arrTime);
            trainAPI.setTrainDate(depDateStr);


            Long trainpriceLong = (Long) jsonObject.get("adultcharge");
            int trainPriceInt = Integer.parseInt(trainpriceLong.toString());
            trainAPI.setPrice(trainPriceInt);

            trainAPIList.add(trainAPI);
        }

        trainAPIRepository.saveAll(trainAPIList);
    }
    private static String extractTimeFromDateTime(String fullDateTime) {
        if (fullDateTime == null || fullDateTime.length() != 14) {
            return null; // 또는 적절한 기본값 또는 예외 처리
        }

        String timePart = fullDateTime.substring(8, 14);
        return timePart.substring(0, 2) + timePart.substring(2, 4) + timePart.substring(4, 6);
    }

    // 날짜와 시간 포맷의 문자열에서 날짜 부분만 추출하여 String 객체로 변환하여 반환하는 메서드
    private static String extractDateFromDateTimeAsString(String fullDateTime) {
        if (fullDateTime == null || fullDateTime.length() != 14) {
            return null; // 또는 적절한 기본값 또는 예외 처리
        }

        return fullDateTime.substring(0, 8);
    }
}
