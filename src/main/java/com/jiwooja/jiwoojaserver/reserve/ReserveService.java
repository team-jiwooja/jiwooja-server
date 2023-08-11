package com.jiwooja.jiwoojaserver.reserve;

import com.jiwooja.jiwoojaserver.pointLog.PointLogService;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveService {
    @Autowired
    private PointLogService pointLogService;

    @Transactional
    public boolean payPoint(PointLogDto reserveDto){
        /* ========================================================================
         * 현재 총합 포인트 조회
         * ======================================================================== */
        int preTotalPoint = pointLogService.preTotalPoint(reserveDto.getUserId());

        /* ========================================================================
         * 현재 총합 포인트가 결제 금액 보다 작을 경우,
         * return (예매 진행 안됨)
         * ======================================================================== */



        /* ========================================================================
         * 예매 데이터 저장
         * ======================================================================== */



        /* ====================================
         * 포인트 사용(U) 로그 insert
         * ==================================== */
        boolean logResult = pointLogService.pointLogging(
                reserveDto.getUserId(), "U", reserveDto.getPoint(), preTotalPoint);
        return logResult;
    }
}
