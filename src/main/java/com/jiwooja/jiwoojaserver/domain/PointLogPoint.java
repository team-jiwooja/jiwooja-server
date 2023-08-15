package com.jiwooja.jiwoojaserver.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name="J_POINT_LOG_POINT")
@NoArgsConstructor
public class PointLogPoint {
    @Id
    @Column(name="POINT_LOG_POINT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PointLogPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_LOG_ID")
    private PointLog pointLogs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_SEQ")
    private Point point;

    @Builder
    public PointLogPoint(PointLog pointLogs, Point point){
        this.pointLogs = pointLogs;
        this.point = point;
    }
}
