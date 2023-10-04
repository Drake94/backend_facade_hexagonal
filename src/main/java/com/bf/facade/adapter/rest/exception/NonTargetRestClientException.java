package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NonTargetRestClientException extends GenericException {
    public NonTargetRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
