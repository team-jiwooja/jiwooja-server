package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
