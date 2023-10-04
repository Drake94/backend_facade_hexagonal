package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NonTargetRestCardException extends GenericException{
    public NonTargetRestCardException (ErrorCode errorCode) {
        super(errorCode);
    }
}
