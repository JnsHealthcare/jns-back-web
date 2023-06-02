package com.jns.backweb.auth.ui.dto;

import com.jns.backweb.auth.application.dto.LoginSuccessResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponse {

    private String email;
    private String name;
    private String token;


    public static LoginResponse from(LoginSuccessResult loginSuccessResult) {
        return new LoginResponse(
                loginSuccessResult.getEmail(),
                loginSuccessResult.getName(),
                loginSuccessResult.getAccessToken()
        );
    }
}
