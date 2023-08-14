package com.jiwooja.jiwoojaserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @NotNull
    @Column(name = "TRAIN_NUM")
    private Long trainNum;

    @NotNull
    @Column(name = "TRAIN_CODE")
    private Long trainCode;

    @NotNull
    @Column(name = "SEAT_CODE")
    private Long seatCode;
    // END - FK


    @NotNull
    @Column(name = "TICKET_NUM")
    private String ticketNum;       // 예매번호(티켓번호)

    @NotNull
    @Column(name = "PRICE")
    private int price;              // 결제 금액

    @NotNull
    @Column(name = "PAY_SEP")
    private String paySep;          // 결제 구분 (A: 포인트 결제, B: 현장결제)

    @NotNull
    @Column(name = "REFUND_YN")
    @ColumnDefault("'N'")
    private String refundYn = "N";        // 환불여부 (default 값 = N)

    @NotNull
    @Column(name = "INP_DATE")
    private LocalDateTime inpDate;  // 입력일(예매일)

    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;  // 수정일(환불일)

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
}
