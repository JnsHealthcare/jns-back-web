package com.jns.backweb.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RegisterRequest {

    @Email(message = "이메일을 확인해주세요.")
    private String email;
    @Length(min = 2, max = 10, message = "이름을 확인해주세요.")
    private String name;
    private LocalDate birthDate;
    @Pattern(regexp = "^\\d{9,11}", message = "전화번호를 확인해주세요.")
    private String phoneNumber;
    @Length(min = 8, message = "비밀번호를 확인해주세요.")
    private String password;
}
