package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class BadRequestRestCardException extends GenericException {
    public BadRequestRestCardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
