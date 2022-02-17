package com.poliscrypts.api.exception;

import com.poliscrypts.api.model.GenericPojoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.Locale;

@Slf4j
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

}
