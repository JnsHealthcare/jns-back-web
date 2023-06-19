package com.jns.backweb.auth.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class UnauthorizedException extends JnsWebApplicationException {
    public UnauthorizedException() {
    super(ErrorCodeAndMessage.UNAUTHORIZED);
    }

    public UnauthorizedException(Throwable cause) {
        super(ErrorCodeAndMessage.UNAUTHORIZED, cause);
    }
}
