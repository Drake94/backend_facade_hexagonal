package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class EmptyOrNullBodyRestAccountException extends GenericException {
    public EmptyOrNullBodyRestAccountException (ErrorCode errorCode) {
        super(errorCode);
    }
}
