package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.GenericPojoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ApiIgnore
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Autowired
    MessageSource messageSource;

    @RequestMapping("${server.error.path}")
    public GenericPojoResponse handleGeneralError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            return new GenericPojoResponse(statusCode, messageSource.getMessage("NOT_FOUND", null, new Locale("en")));
        }
        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new GenericPojoResponse(statusCode, messageSource.getMessage("FATAL_ERROR", null, new Locale("en")));
        }
        return new GenericPojoResponse(statusCode, exception.getMessage());
    }
}
