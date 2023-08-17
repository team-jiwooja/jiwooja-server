package com.jiwooja.jiwoojaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto {
    private String username;
    private String password;
    private String nickname;
    private Integer pointsTotal;
}
