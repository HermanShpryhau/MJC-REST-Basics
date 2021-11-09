package com.epam.esm.web.exception;

import org.springframework.http.HttpStatus;

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

    public HttpStatus httpStatus() {
        HttpStatus status= HttpStatus.BAD_REQUEST;
        if (errorCode.startsWith("404")){
            status = HttpStatus.NOT_FOUND;
        } else if (errorCode.startsWith("403")) {
            status = HttpStatus.FORBIDDEN;
        }
        return status;
    }
}
