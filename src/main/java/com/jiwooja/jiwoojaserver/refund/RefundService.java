package com.jiwooja.jiwoojaserver.refund;

import com.jiwooja.jiwoojaserver.pointLog.PointLogService;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefundService {
    @Autowired
    private PointLogService pointLogService;

    @Transactional
    public boolean refundPoint (PointLogDto reserveDto){
        /* ========================================================================
         * 예매 데이터 확인 및 예매 취소 처리
         * ======================================================================== */


        /* ====================================
         * 포인트 환불(R) 로그 insert
         * ==================================== */
        boolean logResult = pointLogService.pointLogging(
                reserveDto.getUserId(), "R", reserveDto.getPoint());

        return logResult;
    }

    // 포인트 충전(C) 로그 쌓기 용도
    public boolean chargePoint(PointLogDto pointLogDto){
        return pointLogService.pointLogging(
                pointLogDto.getUserId(), "C", pointLogDto.getPoint());
    }
}
