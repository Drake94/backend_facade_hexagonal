package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class NotFoundRestClientException extends GenericException {
    public NotFoundRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
