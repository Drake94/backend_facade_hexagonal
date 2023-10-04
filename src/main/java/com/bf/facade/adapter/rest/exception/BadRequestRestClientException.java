package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class BadRequestRestClientException extends GenericException {
    public BadRequestRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
