package com.jiwooja.jiwoojaserver.refund;

import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/refund")
public class RefundController {
    @Autowired
    private RefundService refundService;


    /*
     * 포인트 결제건에 대한 환불
     */
    @PostMapping(value="/point")
    public boolean refundPoint(@RequestBody TicketDto ticketDto) throws Exception {
        return refundService.refundPoint(ticketDto.getTicketId());
    }


    // 포인트 충전 로그 쌓기 용도
    @PostMapping(value = "/chargePoint")
    public boolean chargePoint(@RequestBody PointLogDto pointLogDto){
        return refundService.chargePoint(pointLogDto);
    }
}
