package com.jns.backweb.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    // error-code의 경우 401 부터 시작
    SERVER_ERROR("E401", "서버 에러입니다."),
    INVALID_REQUEST("E402", "요청 정보를 확인해주세요."),
    UNAUTHORIZED("E403", "권한이 없습니다."),
    DUPLICATED_EMAIL("E404", "이미 회원가입된 이메일입니다."),
    NOT_FOUND_MEMBER("E405", "회원 정보를 찾을 수 없습니다."),
    NOT_MATCHED_EMAIL_PW("E406", "이메일과 비밀번호를 확인해주세요.");


    private final String code;
    private final String message;
}
