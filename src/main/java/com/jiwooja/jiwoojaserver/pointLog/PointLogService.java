package com.jiwooja.jiwoojaserver.pointLog;

import com.jiwooja.jiwoojaserver.domain.User;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLog;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLogRepository;
import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointLogService {

    @Autowired
    private PointLogRepository pointLogRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * '현재 총합 포인트' 조회
     * @param userId 회원 구분자
     * @return int 로그데이터가 없을 경우, 0값을 리턴.
     */
    public int preTotalPoint(Long userId){
        int preTotalPoint = 0;
        // log test

        /* ========================================================================
         * 해당 계정의 가장 최근 포인트 로그 데이터 조회
         * ======================================================================== */
        Optional<PointLog> prePointLog = pointLogRepository.findTopByUser_UserIdOrderByPointLogIdDesc(userId);

        /* ========================================================================
         * 가장 최근 포인트 로그 데이터가 존재할 경우, 해당 데이터의 총합 포인트 반환
         * 없을 경우, 0 값으로 반환
         * ======================================================================== */
        if (prePointLog.isPresent()){
            preTotalPoint = prePointLog.get().getTotalPoint();
        }

        return preTotalPoint;
    }

    /**
     * T_POINT_LOG 테이블에 데이터 INSERT
     * @param userId 회원 구분자
     * @param point 충전/사용/환불하는 포인트 액수
     * @param useSep 사용구분 ( C:충전 / U:사용 / R:환불 )
     * @return boolean
     */
    public boolean pointLogging(Long userId, String useSep, int point){
        /* ========================================================================
         * 1. 계정 존재 확인 및 셋팅
         * ======================================================================== */
        Optional<User> thisUser = userRepository.findById(userId);
        if (!thisUser.isPresent()){
            return false;
        }

        /* ========================================================================
         * 2. PointLogDto 생성
         * ======================================================================== */
        PointLogDto pointLogDto = new PointLogDto();
        pointLogDto.setPoint(point);                            // 사용 point
        pointLogDto.setUseSep(useSep);                          // 사용구분 "U" (C:충전 / U:사용 / R:환불)
        pointLogDto.setTotalPointByPre(preTotalPoint(userId));  // 총합 포인트

        /* ========================================================================
         * 3. pointLogDto entity 변환 및 데이터 취합 & T_POINT_LOG 테이블에 INSERT
         * ======================================================================== */
        PointLog pointLog = pointLogDto.toEntity();
        pointLog.setUser(thisUser.get());               // user
        pointLogRepository.save(pointLog);

        return true;
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
    public boolean pointLogging(Long userId, String useSep, int point, int preTotalPoint){
        /* ========================================================================
         * 1. 계정 존재 확인 및 셋팅
         * ======================================================================== */
        Optional<User> thisUser = userRepository.findById(userId);
        if (!thisUser.isPresent()){
            return false;
        }

        /* ========================================================================
         * 2. PointLogDto 생성
         * ======================================================================== */
        PointLogDto pointLogDto = new PointLogDto();
        pointLogDto.setPoint(point);                            // 사용 point
        pointLogDto.setUseSep(useSep);                          // 사용구분 "U" (C:충전 / U:사용 / R:환불)
        pointLogDto.setTotalPointByPre(preTotalPoint);          // 총합 포인트

        /* ========================================================================
         * 3. pointLogDto entity 변환 및 데이터 취합 & T_POINT_LOG 테이블에 INSERT
         * ======================================================================== */
        PointLog pointLog = pointLogDto.toEntity();
        pointLog.setUser(thisUser.get());               // user
        pointLogRepository.save(pointLog);

        return true;
    }
}
