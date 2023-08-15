package com.jiwooja.jiwoojaserver.Byoun.service;

import com.jiwooja.jiwoojaserver.Byoun.subway.SubwayCategory;
import org.springframework.stereotype.Service;

@Service
public class SubwayService {

    private final SubwayCategory subwayCategory;

    public SubwayService(SubwayCategory subwayCategory) {
        this.subwayCategory = subwayCategory;
    }

    public String getSubwayCode(String subwayName) {
        return subwayCategory.getSubCategory().get(subwayName);
    }
}
