package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class TimeoutRestAccountException extends GenericException {
    public TimeoutRestAccountException (ErrorCode errorCode) {
        super(errorCode);
    }
}
