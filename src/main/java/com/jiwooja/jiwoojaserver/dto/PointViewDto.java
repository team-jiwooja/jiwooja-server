package com.jiwooja.jiwoojaserver.dto;

import com.jiwooja.jiwoojaserver.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointViewDto {

    private Long pointSeq;
    private Integer purchasePrice;      // 금액
    private String paySep;              // A: 무통장 / B: 대리
    private Boolean approved;           // 승인여부
    private LocalDateTime approvalDateTime; // 승인일자
    private String username;            // 사용자

    public PointViewDto(Point entity){
        this.pointSeq = entity.getPointSeq();
        this.purchasePrice = entity.getPurchasePrice();
        this.paySep = entity.getPaySep();
        this.approved = entity.getApproved();
        this.approvalDateTime = entity.getApprovalDateTime();
        this.username = entity.getUser().getUsername();
    }

}