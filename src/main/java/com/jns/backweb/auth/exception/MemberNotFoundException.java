package com.jns.backweb.auth.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class MemberNotFoundException extends JnsWebApplicationException {
    public MemberNotFoundException() {
        super(ErrorCodeAndMessage.NOT_FOUND_MEMBER);
    }

    public MemberNotFoundException(Throwable cause) {
        super(ErrorCodeAndMessage.NOT_FOUND_MEMBER, cause);
    }
}
