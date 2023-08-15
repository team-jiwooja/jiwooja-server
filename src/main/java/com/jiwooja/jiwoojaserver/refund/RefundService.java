package com.jiwooja.jiwoojaserver.refund;

import com.jiwooja.jiwoojaserver.domain.Ticket;
import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.pointLog.PointLogService;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import com.jiwooja.jiwoojaserver.repository.TicketRepository;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import com.jiwooja.jiwoojaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefundService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PointLogService pointLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean refundPoint (Long ticketId) throws Exception {
        Long userId = userService.findUserIdBySecurity();

        /* ========================================================================
         * 예매 데이터 확인 및 예매 취소 처리
         * ======================================================================== */
        Optional<Ticket> reserveTicket = ticketRepository.findByTicketIdAndUser_UserId(ticketId, userId);
        Ticket reserveTicketEntity = reserveTicket.orElseThrow(() -> new Exception("예매 내역이 없습니다."));
        TicketDto reserveTicketDto = new TicketDto(reserveTicketEntity);

        /* ========================================================================
         * 출발일(startTime) 기준, 환불 신청일에 따른 환불 금액 조정
         * ======================================================================== */
        int point = reserveTicketDto.getPrice();
        LocalDateTime today = LocalDateTime.now();
        // 임의의 출발일 기준(2023.10.10 Th10:10:10:70)
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 15, 3, 40, 10, 70);
//        // 출발일(startTime)
//        LocalDateTime startTime = reserveTicketDto.getTrainApi().getStartTime();
        Duration duration = Duration.between(today, startTime);
        Long durSecond = duration.getSeconds();

        if(startTime.getDayOfWeek().getValue() < 4){
            // 출발일이 "월, 화, 수, 목" 일 경우,
            if (durSecond <= 0) {
                // 출발 이후 => 반환금 없음.
                point = 0;
            } else if ((durSecond/3600) <= 3){
                // 3시간 전 경과 후 ~ 출발 시간 전 => 5% 위약금
                point = (int) Math.floor(point*0.95);
            }
            // ~ 3시간 전 => 위약금 없음
        } else {
            // 출발일이 "금,토,일" 일 경우,
            if (durSecond <= 0) {
                // 출발 이후 => 반환금 없음.
                point = 0;
            } else if ((durSecond/3600) <= 3){
                // 3시간 전 경과 후 ~ 출발 시간 전  => 10% 위약금
                point = (int) Math.floor(point*0.9);
            } else if ((durSecond/86400) <= 1){
                // 당일 ~ 3시간 전 => 5% 위약금
                point = (int) Math.floor(point*0.95);
            } else {
                // ~ 1일 전 => 위약금 400원
                point -= 400;
            }

        }

        /* ========================================================================
         * 예매 취소 처리
         * ======================================================================== */
        reserveTicketEntity.refundTicket();

        /* ====================================
         * 포인트 환불(R) 로그 insert
         * ==================================== */
        boolean logResult = pointLogService.pointLogging(userId, "R", point);

        return logResult;
    }

    // 포인트 충전(C) 로그 쌓기 용도
    public boolean chargePoint(PointLogDto pointLogDto){
        return pointLogService.pointLogging(
                pointLogDto.getUserId(), "C", pointLogDto.getPoint());
    }
}
