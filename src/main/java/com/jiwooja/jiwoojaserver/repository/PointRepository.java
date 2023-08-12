package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
}
