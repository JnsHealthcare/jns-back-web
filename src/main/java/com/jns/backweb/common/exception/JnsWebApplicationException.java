package com.jns.backweb.common.exception;

import lombok.Getter;

@Getter
public class JnsWebApplicationException extends RuntimeException {

    private final String code;

    public JnsWebApplicationException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getMessage());
        this.code = errorCodeAndMessage.getCode();
    }

    public JnsWebApplicationException(ErrorCodeAndMessage errorCodeAndMessage, Throwable cause) {
        super(errorCodeAndMessage.getMessage(), cause);
        this.code = errorCodeAndMessage.getCode();
    }
}
