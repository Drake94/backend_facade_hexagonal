package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class DuplicatedClientException extends GenericException{
    public DuplicatedClientException(ErrorCode errorCode) {
        super(errorCode);
    }    
}
