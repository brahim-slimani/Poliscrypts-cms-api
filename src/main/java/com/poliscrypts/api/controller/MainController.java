package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public ResponseEntity<?> main() {
        return new ResponseEntity<>(new ExtendedGenericPojoResponse<>(0, "Success", "WECLOME TO CMS API"), HttpStatus.OK);
    }
}
