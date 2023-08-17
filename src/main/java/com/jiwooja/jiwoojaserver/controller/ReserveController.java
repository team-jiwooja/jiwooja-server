package com.jiwooja.jiwoojaserver.controller;

import com.jiwooja.jiwoojaserver.Byoun.service.TicketService;
import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;
    @Autowired
    private TicketService ticketService;

    /**
     * 기차 예매 - 포인트 결제
     * @param  ticketDto
     * @return Long
     */

    @PostMapping("/insert")
    public ResponseEntity<Void> insertTicket(
            @RequestParam String trainName,
            @RequestParam int trainNum,
            @RequestParam int trainHoNum,
            @RequestParam int date,
            @RequestParam String startingSubway,
            @RequestParam String endingSubway,
            @RequestParam String seatName,
            @RequestParam int price,
            @RequestParam String nickname) {

        ticketService.insertTicket(trainName, trainNum, trainHoNum, date, startingSubway, endingSubway, seatName, price, nickname);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value="/pay/point")
    public boolean payPoint(@RequestBody TicketDto ticketDto){
        return reserveService.payPoint(ticketDto);
    }


}
