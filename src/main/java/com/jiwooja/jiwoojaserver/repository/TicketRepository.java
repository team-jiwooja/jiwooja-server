package com.jiwooja.jiwoojaserver.repository;

import com.jiwooja.jiwoojaserver.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketIdAndUser_UserId(Long ticketId, Long UserId);
}
