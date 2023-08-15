package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.PointLogPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointLogPointRepository extends JpaRepository<PointLogPoint, Long> {
}
