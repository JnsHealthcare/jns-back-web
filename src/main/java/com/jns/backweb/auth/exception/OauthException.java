package com.jns.backweb.auth.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class OauthException extends JnsWebApplicationException {
    public OauthException() {
        super(ErrorCodeAndMessage.OAUTH_FAIL);
    }

    public OauthException(Throwable cause) {
        super(ErrorCodeAndMessage.OAUTH_FAIL, cause);
    }
}
