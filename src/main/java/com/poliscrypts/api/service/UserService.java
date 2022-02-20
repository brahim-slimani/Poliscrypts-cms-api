package com.poliscrypts.api.service;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.model.LoginRequest;
import com.poliscrypts.api.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MessageSource messageSource;

    /**
     * login process is done based on spring security, the credentials will be taken in userDetails service and the process attends within Authentication manager
     * in case of successful authentication JWT utils will be invoked to generate the bearer token
     * @param loginRequest credentials payload
     * @return code, message, authorization
     */
    public ExtendedGenericPojoResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorization = jwtUtils.generateToken(authentication);
        return new ExtendedGenericPojoResponse(authorization);
    }

    /**
     * this method invalidate the token in the blacklist so that it will not be used again after logging out
     * @param authorization token to be invalidated
     * @return code, message
     */
    public GenericPojoResponse logout(String authorization){
        jwtUtils.invalidateToken(authorization);
        return new GenericPojoResponse(0, messageSource.getMessage("SUCCESS", null, new Locale("en")));
    }
}
