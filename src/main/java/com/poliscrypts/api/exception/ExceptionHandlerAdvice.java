package com.poliscrypts.api.exception;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<GenericPojoResponse> companyException(CompanyException exception) {
        log.warn(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> exceptionHandler(ConstraintViolationException e) {
        log.warn(e.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s", constraintViolation.getMessage()))
                .collect(Collectors.toList()));
        ExtendedGenericPojoResponse response = new ExtendedGenericPojoResponse(400, "Error Constraint validation", messages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
