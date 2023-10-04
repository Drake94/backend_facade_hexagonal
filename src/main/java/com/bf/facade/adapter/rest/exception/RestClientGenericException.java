package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class RestClientGenericException extends GenericException {
    public RestClientGenericException(ErrorCode errorCode) {
        super(errorCode);
    }
}
