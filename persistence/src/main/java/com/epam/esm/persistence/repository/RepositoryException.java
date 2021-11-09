package com.epam.esm.persistence.repository;

public class RepositoryException extends RuntimeException {
    private final String errorCode;
    private final transient Object[] arguments;

    public RepositoryException(String errorCode, Object... arguments) {
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
