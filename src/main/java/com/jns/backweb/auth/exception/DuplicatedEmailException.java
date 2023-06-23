package com.jns.backweb.auth.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class DuplicatedEmailException extends JnsWebApplicationException {
    public DuplicatedEmailException() {
        super(ErrorCodeAndMessage.DUPLICATED_EMAIL);
    }

    public DuplicatedEmailException(Throwable cause) {
        super(ErrorCodeAndMessage.DUPLICATED_EMAIL, cause);
    }
}
