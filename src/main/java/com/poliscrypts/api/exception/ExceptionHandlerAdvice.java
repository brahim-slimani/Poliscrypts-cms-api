package com.poliscrypts.api.exception;

import com.poliscrypts.api.model.GenericPojoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericPojoResponse> globalException(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(500, messageSource.getMessage("FATAL_ERROR", null, new Locale("en")));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GenericPojoResponse> resourceNotFoundException(NoHandlerFoundException exception) {
        exception.printStackTrace();
        log.warn(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(404, messageSource.getMessage("NOT_FOUND", null, new Locale("en")));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContactException.class)
    public ResponseEntity<GenericPojoResponse> contactException(ContactException exception) {
        log.warn(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

/*@ControllerAdvice
class CustomErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " -> parameter is missing in request";
        return new ResponseEntity<>(new GenericPojoResponse(status.value(), error), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "body is missing in request";
        return new ResponseEntity<>(new GenericPojoResponse(status.value(), error), status);
    }

}*/
