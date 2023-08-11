package com.jiwooja.jiwoojaserver.pointLog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    Optional<PointLog> findTopByUser_UserIdOrderByPointLogIdDesc(Long userId);
}
