package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class EmptyOrNullBodyRestCardException extends GenericException {
    public EmptyOrNullBodyRestCardException (ErrorCode errorCode) {
        super(errorCode);
    }
}
