package com.jiwooja.jiwoojaserver.refund;

import com.jiwooja.jiwoojaserver.jwt.JwtUtil;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import com.jiwooja.jiwoojaserver.service.impl.CustomUserDetailsServiceImpl;
import com.jiwooja.jiwoojaserver.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/refund")
public class RefundController {
    @Autowired
    private RefundService refundService;


    /*
     * 포인트 결제건에 대한 환불
     */
    @PostMapping(value="/point")
    public boolean refundPoint(@RequestBody PointLogDto pointLogDto){
        return refundService.refundPoint(pointLogDto);
    }


    // 포인트 충전 로그 쌓기 용도
    @PostMapping(value = "/chargePoint")
    public boolean chargePoint(@RequestBody PointLogDto pointLogDto){
        return refundService.chargePoint(pointLogDto);
    }
}
