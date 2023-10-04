package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class RestAccountGenericException extends GenericException {
    public RestAccountGenericException (ErrorCode errorCode) {
        super(errorCode);
    }
}
