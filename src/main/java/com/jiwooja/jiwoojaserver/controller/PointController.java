package com.jiwooja.jiwoojaserver.controller;

import com.jiwooja.jiwoojaserver.dto.Paging;
import com.jiwooja.jiwoojaserver.dto.PointDto;
import com.jiwooja.jiwoojaserver.dto.UserViewDto;
import com.jiwooja.jiwoojaserver.service.PointService;
import com.jiwooja.jiwoojaserver.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    private final UserService userService;

    private static final String POINT_PURCHASE_REQUEST_MESSAGE  = "신한은행 110-436-785784, 예금주: 최보윤, 입금 부탁드립니다.";


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createPoint(@RequestBody PointDto pointDto) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pointService.savePurchasePrice(user.getUsername(), pointDto);
        return POINT_PURCHASE_REQUEST_MESSAGE;
    }

    @PostMapping("/{pointId}/approve")
    public Map<String, Object> approvePoint(@PathVariable Long pointId) {
        Map<String, Object> result = new HashMap<>();
        pointService.approvePoint(pointId);
        result.put("result", ResponseEntity.ok("포인트 입금 확인 및 승인 완료"));
        return result;
    }

    @PostMapping("/getPointList")
    public Map<String, Object> getPointList(@RequestBody Paging paging){
        return pointService.getPointList(paging);
    }


}
