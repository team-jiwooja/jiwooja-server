package com.jiwooja.jiwoojaserver.pointLog.dto;

import com.jiwooja.jiwoojaserver.pointLog.domain.PointLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PointLogDto {
    private Long pointLogId;
    private Long userId;
    private String useSep;
    private int point;
    private int totalPoint;
    private LocalDateTime inpDate;

    /* ===========================================================================
     * entity -> dto 변환을 위한 생성자
     * =========================================================================== */
    public PointLogDto(PointLog entity){
        this.pointLogId = entity.getPointLogId();
        this.userId = entity.getUser().getUserId();
        this.useSep = entity.getUseSep();
        this.point = entity.getPoint();
        this.totalPoint = entity.getTotalPoint();
        this.inpDate = entity.getInpDate();
    }

    /* =======================================================
     * dto -> entity 변환
     * ======================================================= */
    public PointLog toEntity(){
        return PointLog.builder()
                .useSep(useSep)
                .point(point)
                .totalPoint(totalPoint)
                .build();
    }

    /* =======================================================
     * totalPoint 계산 매서드
     * ======================================================= */
    public void setTotalPointByPre(int preTotalPoint){
        switch (this.useSep){
            case "C": // 충전(Charge)
            case "R": // 사용취소(Refund)
                this.totalPoint = preTotalPoint + this.point;
                break;
            case "U": // 사용(Use)
                this.totalPoint = preTotalPoint - this.point;
                break;
        }
    }
}
