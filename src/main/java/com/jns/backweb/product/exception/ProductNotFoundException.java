package com.jns.backweb.product.exception;

import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;

public class ProductNotFoundException extends JnsWebApplicationException {
    public ProductNotFoundException() {
        super(ErrorCodeAndMessage.NOT_FOUND_PRODUCT);
    }

    public ProductNotFoundException(Throwable cause) {
        super(ErrorCodeAndMessage.NOT_FOUND_PRODUCT, cause);
    }
}
