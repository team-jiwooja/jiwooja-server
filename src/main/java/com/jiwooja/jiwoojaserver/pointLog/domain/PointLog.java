package com.jiwooja.jiwoojaserver.pointLog.domain;

import com.jiwooja.jiwoojaserver.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="T_POINT_LOG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String useSep;

    @Column(name="POINT")
    private int point;

    @Column(name="TOTAL_POINT")
    private int totalPoint;

    @Column(name="INP_DATE")
    private LocalDateTime inpDate;

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
     * User setting
     * =========================================================================== */
    public void setUser(User user){
        this.user = user;
    }

}
