package com.jiwooja.jiwoojaserver.dto;

import com.jiwooja.jiwoojaserver.domain.Ticket;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TicketDto {
    private Long ticketId;

    private Long userId;
    private Long trainNum;
    private Long trainCode;
    private Long seatCode;

    private String ticketNum;
    private int price;
    private String paySep;  // (A: 포인트 결제, B: 현장결제, C:환불)
    private String refundYn;

    private LocalDateTime inpDate;
    private LocalDateTime modDate;

    /* ===========================================================================
     * entity -> dto 변환을 위한 생성자
     * =========================================================================== */
    public TicketDto(Ticket entity){
        this.ticketId = entity.getTicketId();

        this.userId = entity.getUser().getUserId();

        this.trainNum = entity.getTrainNum();
//        this.trainType = entity.getTrainApi().getTrainType();
//        this.trainNumName = entity.getTrainApi().getTrainNumName();
//        this.trainDate = entity.getTrainApi().getTrainDate();
//        this.startingSubway = entity.getTrainApi().getStartingSubway();
//        this.endingSubway = entity.getTrainApi().getEndingSubway();
//        this.startTime = entity.getTrainApi().getStartTime();
//        this.endTime = entity.getTrainApi().getEndTime();

        this.trainCode = entity.getTrainCode();
//        this.trainHoNum = entity.getTrainCode().getTrainHoNum();

        this.seatCode = entity.getSeatCode();
//        this.seatName = entity.getTrainSeat().getSeatName();

        this.ticketNum = entity.getTicketNum();
        this.price = entity.getPrice();
        this.paySep = entity.getPaySep();
        this.refundYn = entity.getRefundYn();

        this.inpDate = entity.getInpDate();
        this.modDate = entity.getModDate();
    }

    /* =======================================================
     * dto -> entity 변환
     * ======================================================= */
    public Ticket toEntity(){
        return Ticket.builder()
                .trainNum(trainNum)
                .trainCode(trainCode)
                .seatCode(seatCode)
                .ticketNum(ticketNum)
                .price(price)
                .paySep(paySep)
                .refundYn(refundYn)
                .build();
    }

    /* =======================================================
     * 예매 데이터 셋팅
     * ======================================================= */
    public void setReserve(String ticketNum, int price, String paySep){
        this.ticketNum = ticketNum;
        this.price = price;
        this.paySep = paySep;
    }
}
