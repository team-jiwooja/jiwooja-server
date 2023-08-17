package com.jiwooja.jiwoojaserver.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="T_POINT_LOG")
@NoArgsConstructor
public class PointLog {
    @Id
    @Column(name="POINT_LOG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointLogId;

    @ManyToOne(optional = false)
    @JoinColumn(
            name="USER_ID"
            ,referencedColumnName="USER_ID")
    private User user;

    @Column(name="USE_SEP")
    private String useSep;  // (C:충전 / U:사용 / R:환불)

    @Column(name="POINT")
    private int point;

    @Column(name="TOTAL_POINT")
    private int totalPoint;

    @Column(name="INP_DATE")
    private LocalDateTime inpDate;


    @OneToMany(mappedBy = "pointLogs")
    private List<PointLogTicket> pointLogTickets = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<PointLogPoint> pointLogPoints = new ArrayList<>();

    /* ===========================================================================
     * 입력일 셋팅
     * @PrePersist : save(insert) 매서드 호출시 실행됨.
     * save 호출 시 입력일값을 현재 시간으로 셋팅함.
     * =========================================================================== */
    @PrePersist
    public void prePersist(){
        this.inpDate = LocalDateTime.now();
    }


    /* ===========================================================================
     * Builder
     * =========================================================================== */
    @Builder
    public PointLog( User user, String useSep, int point, int totalPoint){
        this.user = user;
        this.useSep = useSep;
        this.point = point;
        this.totalPoint = totalPoint;
    }

    /* ===========================================================================
     * FK setting
     * =========================================================================== */
    public void setUser(User user){
        this.user = user;
    }

    public void setPointLog(User user, String useSep, int point, LocalDateTime inpDate){
        this.user = user;
        this.useSep = useSep;
        this.point = point;
        this.inpDate = inpDate;
    }


}
