package com.bf.facade.adapter.jdbc.exception;

public class SqlReaderException extends RuntimeException {
    public SqlReaderException(String message) {
        super(message);
    }
    public SqlReaderException(Throwable cause) {
        super(cause);
    }
}
