package com.jiwooja.jiwoojaserver.Byoun.controller;

import  com.jiwooja.jiwoojaserver.Byoun.service.SubwayCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubwayController {

    private final SubwayCodeService subwayCodeService;

    @Autowired
    public SubwayController(SubwayCodeService subwayCodeService) {
        this.subwayCodeService = subwayCodeService;
    }

    @GetMapping("/subway/code/{stationName}")
    //역 이름 반환
    public String getSubwayCode(@PathVariable String stationName) {
        return subwayCodeService.getSubwayCode(stationName);
        //
    }
}
