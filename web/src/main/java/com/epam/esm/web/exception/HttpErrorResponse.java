package com.epam.esm.web.exception;

public class HttpErrorResponse {
    private final String errorCode;
    private final String errorMessage;

    public HttpErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
