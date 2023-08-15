package com.jiwooja.jiwoojaserver.controller;

import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    /**
     * 기차 예매 - 포인트 결제
     * @param  ticketDto
     * @return Long
     */
    @PostMapping(value="/pay/point")
    public boolean payPoint(@RequestBody TicketDto ticketDto){
        return reserveService.payPoint(ticketDto);
    }


}
