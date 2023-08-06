package com.jiwooja.jiwoojaserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessTokenResponse extends TokenResponse{
    private String accessToken;
}
