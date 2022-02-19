package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.GenericPojoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Autowired
    MessageSource messageSource;

    @RequestMapping("/error")
    public GenericPojoResponse handleGeneralError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return new GenericPojoResponse(statusCode, messageSource.getMessage("NOT_FOUND", null, new Locale("en")));
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new GenericPojoResponse(statusCode, messageSource.getMessage("FATAL_ERROR", null, new Locale("en")));
            }
        }
        return null;
    }
}
