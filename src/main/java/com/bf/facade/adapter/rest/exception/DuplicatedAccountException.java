package com.bf.facade.adapter.rest.exception;

import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.exception.GenericException;

public class DuplicatedAccountException extends GenericException {
    public DuplicatedAccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
