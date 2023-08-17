package com.jiwooja.jiwoojaserver.controller;

import com.jiwooja.jiwoojaserver.dto.Paging;
import com.jiwooja.jiwoojaserver.service.PointLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/point")
public class PointLogController {
    @Autowired
    private PointLogService pointLogService;
    @PostMapping("/getPointLogList")
    public Map<String, Object> getPointLogList(@RequestBody Paging paging){
        Map<String, Object> result = new HashMap<>();
        result.put("list", pointLogService.getPointLogList(paging));
        return result;
    }
}
