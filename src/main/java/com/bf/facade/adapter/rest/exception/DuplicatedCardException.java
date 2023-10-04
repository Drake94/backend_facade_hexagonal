package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class DuplicatedCardException extends GenericException {
    public DuplicatedCardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
