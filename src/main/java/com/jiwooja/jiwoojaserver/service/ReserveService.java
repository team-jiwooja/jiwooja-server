package com.jiwooja.jiwoojaserver.service;

import com.jiwooja.jiwoojaserver.domain.Ticket;
import com.jiwooja.jiwoojaserver.domain.User;
import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.exception.NotFoundUserException;
import com.jiwooja.jiwoojaserver.service.PointLogService;
import com.jiwooja.jiwoojaserver.repository.TicketRepository;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import com.jiwooja.jiwoojaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PointLogService pointLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean payPoint(TicketDto ticketDto){
        org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userSecurity.getUsername())
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));

        /* ========================================================================
         * 현재 총합 포인트 조회
         * ======================================================================== */
        int preTotalPoint = pointLogService.preTotalPoint(user.getUserId());

        /* ========================================================================
         * 현재 총합 포인트가 결제 금액 보다 작을 경우,
         * return (예매 진행 안됨)
         * ======================================================================== */
        if (preTotalPoint < ticketDto.getPrice()){
            return false;
        }

        /* ========================================================================
         * 예매 데이터 생성 및 저장
         * ======================================================================== */
        ticketDto.setReserve("123-456-789", 50000, "A");    // A : 포인트 구매
        Ticket saveTicket = ticketDto.toEntity();
        saveTicket.setUser(user);
        ticketRepository.save(saveTicket);


        /* ====================================
         * 포인트 사용(U) 로그 insert
         * ==================================== */
        boolean logResult = pointLogService.pointLogging(
                user.getUserId(), "U", ticketDto.getPrice(), preTotalPoint, saveTicket);

        return logResult;
    }
}
