package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class BadRequestRestAccountException extends GenericException {
    public BadRequestRestAccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
