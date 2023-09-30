package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByApprovedTrue(); // 승인된 포인트 구매 조회

    Page<Point> findAllByUser_Nickname(String nickname, Pageable pageable); // 승인된 포인트 구매 조회

}
