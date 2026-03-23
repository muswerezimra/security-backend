package com.zimra.engine.common.exceptions;

public abstract class ApiException extends RuntimeException {

    protected ApiException(String message) {
        super(message);
    }
}
