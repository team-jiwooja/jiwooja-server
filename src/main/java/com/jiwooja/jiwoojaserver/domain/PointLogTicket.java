package com.jiwooja.jiwoojaserver.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name="J_POINT_LOG_TICKET")
@NoArgsConstructor
public class PointLogTicket {
    @Id
    @Column(name="POINT_LOG_TICKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PointLogTicketId;

    @ManyToOne
    @JoinColumn(name = "POINT_LOG_ID")
    private PointLog pointLogs;

    @ManyToOne
    @JoinColumn(name = "TICKET_ID")
    private Ticket tickets;

    @Builder
    public PointLogTicket(PointLog pointLogs, Ticket tickets){
        this.pointLogs = pointLogs;
        this.tickets = tickets;
    }
}
