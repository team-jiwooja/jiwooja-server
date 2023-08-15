package com.jiwooja.jiwoojaserver.pointLog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointLogTicketRepository extends JpaRepository<PointLogTicket, Long> {
}
