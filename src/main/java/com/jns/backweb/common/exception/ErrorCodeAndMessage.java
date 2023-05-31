package com.jns.backweb.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    // error-code의 경우 401 부터 시작

    SERVER_ERROR("G401", "서버 에러입니다."),
    INVALID_REQUEST("G402", "요청 정보를 확인해주세요.");


    private final String code;
    private final String message;
}
