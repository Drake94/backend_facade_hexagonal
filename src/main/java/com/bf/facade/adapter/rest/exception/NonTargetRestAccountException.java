package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NonTargetRestAccountException extends GenericException {
    public NonTargetRestAccountException (ErrorCode errorCode) {
        super(errorCode);
    }
}
