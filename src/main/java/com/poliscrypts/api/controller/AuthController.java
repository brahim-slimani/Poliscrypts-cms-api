package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.model.LoginRequest;
import com.poliscrypts.api.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Api(tags = "Authentication", description = "Auth operations Login & Logout")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ExtendedGenericPojoResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/logout")
    public GenericPojoResponse logout(@RequestParam("Authorization") String authorization) {
        return userService.logout(authorization);
    }
}
