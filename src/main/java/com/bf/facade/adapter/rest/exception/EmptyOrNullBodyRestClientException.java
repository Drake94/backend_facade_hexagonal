package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class EmptyOrNullBodyRestClientException extends GenericException {
    public EmptyOrNullBodyRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
