package com.jiwooja.jiwoojaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paging {
    private Integer limitStart;
    private Integer limitEnd;
}
