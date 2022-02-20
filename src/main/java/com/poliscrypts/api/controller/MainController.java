package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@Api(tags = "Entry Point")
public class MainController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/")
    public ResponseEntity<?> main() {
        return new ResponseEntity<>(new ExtendedGenericPojoResponse<>(messageSource.getMessage("WELCOME", null, new Locale("en"))), HttpStatus.OK);
    }
}
