package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class RestCardGenericException extends GenericException{
    public RestCardGenericException (ErrorCode errorCode){
        super(errorCode);
    }
}
