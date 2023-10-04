package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NotFoundRestAccountException extends GenericException{
    public NotFoundRestAccountException (ErrorCode errorCode) {
        super(errorCode);
    }
}
