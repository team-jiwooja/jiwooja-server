package com.jiwooja.jiwoojaserver.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="USERNAME", length = 50, unique = true)
    private String username;
    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "NICKNAME", length = 50)
    private String nickname;

    @Column(name = "token", length = 200)
    private String token;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
