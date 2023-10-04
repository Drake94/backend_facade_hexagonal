package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NotFoundRestCardException extends GenericException {
    public NotFoundRestCardException (ErrorCode errorCode) {
        super(errorCode);
    }
}
