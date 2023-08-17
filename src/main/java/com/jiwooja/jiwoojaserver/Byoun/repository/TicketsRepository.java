package com.jiwooja.jiwoojaserver.Byoun.repository;

import com.jiwooja.jiwoojaserver.Byoun.dto.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketsRepository extends JpaRepository<TicketDTO, String> {
    List<TicketDTO> findByNickname(String nickname);
}