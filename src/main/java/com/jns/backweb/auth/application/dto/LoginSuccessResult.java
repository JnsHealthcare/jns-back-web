package com.jns.backweb.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class LoginSuccessResult {


    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int refreshTokenDuration;

}
