package com.jns.backweb.auth.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class InvalidLoginException extends JnsWebApplicationException {
    public InvalidLoginException() {
        super(ErrorCodeAndMessage.NOT_MATCHED_EMAIL_PW);
    }

    public InvalidLoginException(Throwable cause) {
        super(ErrorCodeAndMessage.NOT_MATCHED_EMAIL_PW, cause);
    }
}
