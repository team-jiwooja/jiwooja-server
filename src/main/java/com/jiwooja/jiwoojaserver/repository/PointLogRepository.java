package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.PointLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    Optional<PointLog> findTopByUser_UserIdOrderByPointLogIdDesc(Long userId);

    List<PointLog> findAllByUser_UserId(Long userId, Pageable pageable);
}
