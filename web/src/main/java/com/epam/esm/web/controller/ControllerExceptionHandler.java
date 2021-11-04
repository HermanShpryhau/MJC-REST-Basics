package com.epam.esm.web.controller;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.web.exception.HttpErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Locale;

@EnableWebMvc
@ControllerAdvice
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(@Qualifier("errorMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<HttpErrorResponse> handleServiceException(ServiceException e) {
        String message = messageSource.getMessage(e.getErrorCode(), e.getArguments(), Locale.ENGLISH);
        HttpErrorResponse errorResponse = new HttpErrorResponse(e.getErrorCode(), message);
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseBody
    public ResponseEntity<HttpErrorResponse> handleRepositoryException(RepositoryException e) {
        String message = messageSource.getMessage(e.getErrorCode(), e.getArguments(), Locale.ENGLISH);
        HttpErrorResponse errorResponse = new HttpErrorResponse(e.getErrorCode(), message);
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().get(0);
        String errorCode = error.getDefaultMessage();
        String message = messageSource.getMessage(errorCode, null, Locale.ENGLISH);
        return new HttpErrorResponse(errorCode, message);
    }
}
