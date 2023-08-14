package com.jiwooja.jiwoojaserver.Byoun.subway;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class SubwayCategory {

    private HashMap<String, String> sub_category;

    @PostConstruct
    public void init() {
        sub_category = new HashMap<>();
        sub_category.put("KTX", "00");
        sub_category.put("새마을호", "01");
        sub_category.put("무궁화호", "02");
        sub_category.put("ITX-새마을", "08");
        sub_category.put("ITX-청춘", "09");
        sub_category.put("SRT", "17");
    }

    public HashMap<String, String> getSubCategory() {
        return sub_category;
    }
}
