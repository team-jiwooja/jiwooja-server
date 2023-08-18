package com.jiwooja.jiwoojaserver.service;

import com.jiwooja.jiwoojaserver.domain.*;
import com.jiwooja.jiwoojaserver.dto.Paging;
import com.jiwooja.jiwoojaserver.dto.PointDto;
import com.jiwooja.jiwoojaserver.dto.TicketDto;
import com.jiwooja.jiwoojaserver.exception.NotFoundUserException;
import com.jiwooja.jiwoojaserver.repository.*;
import com.jiwooja.jiwoojaserver.dto.PointLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointLogService {

    @Autowired
    private PointLogRepository pointLogRepository;
    @Autowired
    private PointLogTicketRepository pointLogTicketRepository;
    @Autowired
    private PointLogPointRepository pointLogPointRepository;

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * '현재 총합 포인트' 조회
     * @param userId 회원 구분자
     * @return int 로그데이터가 없을 경우, 0값을 리턴.
     */
    public int preTotalPoint(Long userId){
        int preTotalPoint = 0;

        /* ========================================================================
         * 해당 계정 조회
         * ======================================================================== */
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));

        /* ========================================================================
         * 가장 최근 포인트 로그 데이터가 존재할 경우, 해당 데이터의 총합 포인트 반환
         * 없을 경우, 0 값으로 반환
         * ======================================================================== */
        if (user.getPointsTotal() != null){
            preTotalPoint = user.getPointsTotal();
        }

        return preTotalPoint;
    }

    /**
     * T_POINT_LOG 테이블에 데이터 INSERT
     * @param userId 회원 구분자
     * @param point 충전/사용/환불하는 포인트 액수
     * @param useSep 사용구분 ( C:충전 / U:사용 / R:환불 )
     * @param entity join 테이블 entity
     * @return boolean
     */
    public boolean pointLogging(Long userId, String useSep, int point, Object entity){
        /* ========================================================================
         * 1. 총합 포인트 구하기
         * ======================================================================== */
        int preTotalPoint = preTotalPoint(userId);

        /* ========================================================================
         * 2. T_POINT_LOG 테이블에 데이터 INSERT
         * ======================================================================== */
        return pointLogging(userId, useSep, point, preTotalPoint, entity);
    }

    /**
     * T_POINT_LOG 테이블에 데이터 INSERT
     * ** '현재 총합 포인트' 조회 값을 선수행 하거나 추가로 사용해야하는 일이 있을 경우, 사용 **
     * @param userId 회원 구분자
     * @param point 충전/사용/환불하는 포인트 액수
     * @param useSep 사용구분 ( C:충전 / U:사용 / R:환불 )
     * @param preTotalPoint 현재 총합 포인트
     * @return boolean
     */
    @Transactional
    public boolean pointLogging(Long userId, String useSep, int point, int preTotalPoint, Object entity){
        /* ========================================================================
         * 1. 계정 존재 확인 및 셋팅
         * ======================================================================== */
        User thisUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));

        /* ========================================================================
         * 2. PointLogDto 생성
         * ======================================================================== */
        PointLogDto pointLogDto = new PointLogDto();
        pointLogDto.setPoint(point);                            // 사용 point
        pointLogDto.setUseSep(useSep);                          // 사용구분 (C:충전 / U:사용 / R:환불)
        pointLogDto.setTotalPointByPre(preTotalPoint);          // 총합 포인트

        /* ========================================================================
         * 3. pointLogDto entity 변환 및 데이터 취합 & T_POINT_LOG 테이블에 INSERT
         * ======================================================================== */
        PointLog pointLog = pointLogDto.toEntity();
        pointLog.setUser(thisUser);               // user
        pointLogRepository.save(pointLog);

        /* ========================================================================
         * 4. 사용 구분에 따른 테이블 컬럼 join
         * ======================================================================== */
        if ("U".equals(useSep) || "R".equals(useSep)){
            // U:사용 / R:환불 일 경우, 예매 테이블 id 조인
            PointLogTicket pointLogTicket
                    = PointLogTicket.builder()
                    .pointLogs(pointLog)
                    .tickets((Ticket) entity)
                    .build();
            pointLogTicketRepository.save(pointLogTicket);

        } else if("C".equals(useSep)){
            PointLogPoint pointLogPoint
                    = PointLogPoint.builder()
                    .pointLogs(pointLog)
                    .point((Point) entity)
                    .build();
            pointLogPointRepository.save(pointLogPoint);
        }

        /* ========================================================================
         * 5. User 총합 포인트 업데이트
         * ======================================================================== */
        thisUser.setTotalPoints(pointLogDto.getTotalPoint());
        userRepository.save(thisUser);

        return true;
    }

    /**
     * 포인트 로그 리스트
     * @return List<PointLogDto>
     */
    public List<PointLogDto> getPointLogList(Paging paging){
        org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userSecurity.getUsername())
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));

        Pageable limitOneHundred = PageRequest.of(paging.getLimitStart(), paging.getLimitEnd(), Sort.by("pointLogId").descending());
        List<PointLog> pointLogList = pointLogRepository.findAllByUser_UserId(user.getUserId(), limitOneHundred);

        List<PointLogDto> result = new ArrayList<>();
        for (PointLog e : pointLogList) {
            PointLogDto pointLogDto = new PointLogDto(e);

            if ("U".equals(e.getUseSep()) || "R".equals(e.getUseSep())){
                List<PointLogTicket> ticketList = e.getPointLogTickets();
                if (ticketList.size() != 0){
                    Ticket ticketEntity = ticketList.get(0).getTickets();
                    pointLogDto.setTicketDto(new TicketDto(ticketEntity));
                }
            } else if ("C".equals(e.getUseSep())){
                List<PointLogPoint> pointList = e.getPointLogPoints();
                if (pointList.size() != 0){
                    Point pointEntity = pointList.get(0).getPoint();
                    PointDto pointDto = PointDto.builder()
                            .paySep(pointEntity.getPaySep())
                            .build();
                    pointLogDto.setPointDto(pointDto);
                }
            }

            result.add(pointLogDto);
        }

        return result;
    }
}
