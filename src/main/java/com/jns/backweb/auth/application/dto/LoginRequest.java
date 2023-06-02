package com.jns.backweb.auth.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {

    @Email(message = "아이디 혹은 비밀번호를 확인해주세요.")
    private String email;
    @Length(min = 8, max = 20, message = "아이디 혹은 비밀번호를 확인해주세요.")
    private String password;
}
