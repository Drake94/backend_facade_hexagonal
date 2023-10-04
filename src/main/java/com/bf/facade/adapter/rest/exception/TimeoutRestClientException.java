package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class TimeoutRestClientException extends GenericException {
    public TimeoutRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
