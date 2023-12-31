package com.jiwooja.jiwoojaserver.domain;

import com.jiwooja.jiwoojaserver.dto.PointDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Point {

    @Id
    @Column(name="POINT_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointSeq;

    private Integer purchasePrice;

    private String paySep;

    private Boolean approved = false;

    private LocalDateTime approvalDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PointLogPoint> pointLogPoints = new ArrayList<>();

    @Builder
    public Point(Integer purchasePrice, String paySep, Boolean approved, LocalDateTime approvalDateTime, User user) {
        this.purchasePrice = purchasePrice;
        this.paySep = paySep;
        this.approved = approved;
        this.approvalDateTime = approvalDateTime;
        setUser(user);
    }

    private void setUser(User user) {
        if (this.user != null) {
            this.user.getPoints().remove(this);
        }
        this.user = user;
        user.getPoints().add(this);
    }

    public static Point requestPoint(User user, PointDto pointDto) {
        return Point.builder()
                .user(user)
                .purchasePrice(pointDto.getPurchasePrice())
                .paySep(pointDto.getPaySep())
                .build();
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setApprovalDateTime(LocalDateTime approvalDateTime) {
        this.approvalDateTime = approvalDateTime;
    }
    
}
