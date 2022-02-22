package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.GenericPojoResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
@Api(tags = "Entry point")
public class MainController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/")
    public GenericPojoResponse main() {
        return new GenericPojoResponse(0,messageSource.getMessage("WELCOME", null, new Locale("en")));
    }
}
