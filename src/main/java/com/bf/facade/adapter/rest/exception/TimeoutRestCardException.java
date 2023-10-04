package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class TimeoutRestCardException extends GenericException{
    public TimeoutRestCardException (ErrorCode errorCode) {
        super(errorCode);
    }
}
