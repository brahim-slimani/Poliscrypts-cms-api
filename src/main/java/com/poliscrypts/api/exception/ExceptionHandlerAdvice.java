package com.poliscrypts.api.exception;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericPojoResponse globalException(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageSource.getMessage("FATAL_ERROR", null, new Locale("en")));
        return response;
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

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<GenericPojoResponse> userException(TokenException exception) {
        log.warn(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GenericPojoResponse badCredentialsException(BadCredentialsException exception) {
        log.warn(exception.getMessage());
        GenericPojoResponse response = new GenericPojoResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExtendedGenericPojoResponse exceptionHandler(ConstraintViolationException e) {
        log.warn(e.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s", constraintViolation.getMessage()))
                .collect(Collectors.toList()));
        ExtendedGenericPojoResponse response = new ExtendedGenericPojoResponse(HttpStatus.BAD_REQUEST.value(), "Error Constraint validation", messages);
        return response;
    }

}
