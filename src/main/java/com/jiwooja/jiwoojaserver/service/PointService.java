package com.jiwooja.jiwoojaserver.service;

import com.jiwooja.jiwoojaserver.domain.Point;
import com.jiwooja.jiwoojaserver.domain.User;
import com.jiwooja.jiwoojaserver.dto.PointDto;
import com.jiwooja.jiwoojaserver.exception.NotFoundUserException;
import com.jiwooja.jiwoojaserver.exception.PointNotFoundException;
import com.jiwooja.jiwoojaserver.pointLog.PointLogService;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLog;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLogRepository;
import com.jiwooja.jiwoojaserver.repository.PointRepository;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    private final UserRepository userRepository;

    private final PointLogRepository pointLogRepository;

    private final PointLogService pointLogService;

    public Long savePurchasePrice(String username, PointDto pointDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));
        Point point = Point.requestPoint(user, pointDto);
        Point savedPrice = pointRepository.save(point);
        return savedPrice.getPointSeq();
    }

    public void approvePoint(Long pointSeq) {
        Optional<Point> optionalPoint = pointRepository.findById(pointSeq);
        if (optionalPoint.isPresent()) {
            Point point = optionalPoint.get();
            point.setApproved(true);
            point.setApprovalDateTime(LocalDateTime.now());
            pointRepository.save(point);

            // 총합 업데이트
            User user = point.getUser();
            List<Point> approvedPurchases = pointRepository.findByApprovedTrue();
            user.setPoints(approvedPurchases);
            user.updateTotalPoints();
            userRepository.save(user);

            // 포인트 로그 작성
            pointLogService.pointLogging(user.getUserId(), "C", point.getPurchasePrice());


        } else {
            throw new PointNotFoundException("요청하신 포인트를 찾을 수 없습니다.");
        }
    }
}
