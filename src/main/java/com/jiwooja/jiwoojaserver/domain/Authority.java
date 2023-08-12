package com.jiwooja.jiwoojaserver.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority {

    @Id
    @Column(name = "AUTHORITY_NAME", length = 50)
    private String authorityName;

}