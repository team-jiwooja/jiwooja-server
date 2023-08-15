package com.jiwooja.jiwoojaserver.domain;

import com.jiwooja.jiwoojaserver.pointLog.domain.PointLog;
import com.jiwooja.jiwoojaserver.pointLog.domain.PointLogTicket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name="TICKET")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
public class Ticket {
    @Id
    @Column(name="TICKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;      // 예매 구분자

    // START - FK
    @ManyToOne(optional = false)
    @JoinColumn(
            name="USER_ID"
            ,referencedColumnName="USER_ID")
    private User user;

    @Column(name = "TRAIN_NUM", nullable = false)
    private Long trainNum;

    @Column(name = "TRAIN_CODE", nullable = false)
    private Long trainCode;

    @Column(name = "SEAT_CODE", nullable = false)
    private Long seatCode;
    // END - FK


    @Column(name = "TICKET_NUM", nullable = false)
    private String ticketNum;       // 예매번호(티켓번호)

    @Column(name = "PRICE", nullable = false)
    private int price;              // 결제 금액

    @Column(name = "PAY_SEP", nullable = false)
    private String paySep;          // 결제 구분 (A: 포인트 결제, B: 현장결제)

    @Column(name = "REFUND_YN", nullable = false)
    @ColumnDefault("'N'")
    private String refundYn = "N";        // 환불여부 (default 값 = N)

    @Column(name = "INP_DATE", nullable = false)
    private LocalDateTime inpDate;  // 입력일(예매일)

    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;  // 수정일(환불일)


    @OneToMany(mappedBy = "tickets")
    private List<PointLogTicket> pointLogTickets = new ArrayList<>();

    /* ===========================================================================
     * 입력일 셋팅 / 디폴트 값 지정
     * @PrePersist : save(insert) 매서드 호출시 실행됨.
     * save 호출 시 입력일값을 현재 시간으로 셋팅함.
     * =========================================================================== */
    @PrePersist
    public void prePersist(){
        this.inpDate = LocalDateTime.now();
    }

    /* ===========================================================================
     * 수정일 셋팅
     * @PreUpdate : update 발생시 실행됨.
     * =========================================================================== */
    @PreUpdate
    public void preUpdate(){
        this.modDate = LocalDateTime.now();
    }

    /* ===========================================================================
     * Builder
     * =========================================================================== */
    @Builder
    public Ticket(
            User user, Long trainNum, Long trainCode,
            Long seatCode, String ticketNum, int price,
            String paySep, String refundYn){
        this.user = user;
        this.trainNum = trainNum;
        this.trainCode = trainCode;
        this.seatCode = seatCode;
        this.ticketNum = ticketNum;
        this.price = price;
        this.paySep = paySep;
        this.refundYn = refundYn;
    }

    /* ===========================================================================
     * FK setting
     * =========================================================================== */
    public void setUser(User user){
        this.user = user;
    }

    /* ===========================================================================
     * update and delete
     * =========================================================================== */
    public void refundTicket() {
        this.refundYn = "Y";
    }
}
