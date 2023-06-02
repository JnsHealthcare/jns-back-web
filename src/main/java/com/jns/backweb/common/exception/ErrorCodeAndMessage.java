package com.jns.backweb.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    // error-code의 경우 401 부터 시작
    SERVER_ERROR("E401", "서버 에러입니다."),
    INVALID_REQUEST("E402", "요청 정보를 확인해주세요."),
    UNAUTHORIZED("E403", "권한이 없습니다.");


    private final String code;
    private final String message;
}
