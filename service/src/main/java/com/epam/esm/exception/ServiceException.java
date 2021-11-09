package com.epam.esm.exception;

public class ServiceException extends RuntimeException {
    private final String errorCode;
    private final transient Object[] arguments;

    public ServiceException(String errorCode, Object... arguments) {
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
