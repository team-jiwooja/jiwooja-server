package com.jiwooja.jiwoojaserver.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "J_USER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="USERNAME", length = 50, unique = true)
    private String username;
    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "NICKNAME", length = 50)
    private String nickname;

    @Column(name = "TOKEN", length = 200)
    private String token;

    private Integer pointsTotal;

    @ManyToMany
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")})
    private Set<Authority> authorities;


    @OneToMany(mappedBy = "user")
    private List<PointLog> pointLogs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();

    public void setPoints(List<Point> points) {
        this.points = points;
        updateTotalPoints();
    }

    public void updateTotalPoints() {
        int totalPoints = this.points.stream()
                .mapToInt(Point::getPurchasePrice)
                .sum();
        this.pointsTotal = totalPoints;
    }


    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();
}
