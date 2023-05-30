package com.jns.backweb.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    // error-code의 경우 401 부터 시작
    PROCESS_NOT_IN_SAME_JOB("P401", "해당 채용공고에 포함된 채용 프로세스가 아닙니다."),
    PROCESS_SEQUENCE_INVALID("P402", "잘못된 순서 정보입니다."),
    PROCESS_COUNT_OVER_LIMIT("P403", "채용 프로세스는 채용공고 당 10개까지 생성할 수 있습니다."),
    PROCESS_NOT_FOUND("P404", "존재하지않는 채용 프로세스 입니다."),
    PROCESS_SEQUENCE_NULL("P405", "잘못된 채용 프로세스 위치 정보입니다."),
    PROCESS_MUST_HAVE_ONE("P406", "채용 공고당 프로세스는 하나 이상 존재해야합니다."),
    PROCESS_REGISTER_LOCK("P407", "채용 프로세스 등록에 실패했습니다. 잠시 후에 다시 시도해주세요."),
    JOB_NOT_FOUND("J401", "존재하지 않는 채용 공고입니다."),

    APPLICANT_NOT_FOUND("A401", "존재하지 않는 지원자입니다."),

    SERVER_ERROR("G401", "서버 에러입니다."),
    INVALID_REQUEST("G402", "요청 정보를 확인해주세요.");


    private final String code;
    private final String message;
}
