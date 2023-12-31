package com.jiwooja.jiwoojaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {

    private Integer purchasePrice;
    private String paySep;  // A: 무통장 / B: 대리

}
