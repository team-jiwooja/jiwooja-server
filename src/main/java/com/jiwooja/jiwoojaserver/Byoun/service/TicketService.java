package com.jiwooja.jiwoojaserver.Byoun.service;

import com.jiwooja.jiwoojaserver.Byoun.dto.TicketDTO;
import com.jiwooja.jiwoojaserver.Byoun.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketsRepository ticketRepository;

    public List<TicketDTO> searchMemTicket(String nickName) {
        return ticketRepository.findByNickname(nickName);
    }

    @Transactional
    public boolean refundMemTicket(String ticketNum) {
        Optional<TicketDTO> ticket = ticketRepository.findById(ticketNum);
        if (ticket.isPresent()) {
            ticketRepository.deleteById(ticketNum);
            return true;
        }
        return false;
    }
}
