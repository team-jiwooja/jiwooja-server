package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.PointLogTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointLogTicketRepository extends JpaRepository<PointLogTicket, Long> {
}
