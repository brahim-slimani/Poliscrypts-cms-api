package com.poliscrypts.api.endpoints.unit.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poliscrypts.api.model.LoginRequest;
import com.poliscrypts.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Utils {

    @Autowired
    UserService userService;

    public static byte[] convert2Json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public String generateAuthJWTToken() {
        return (String) userService.login(new LoginRequest("admin", "admin")).getData();
    }

}
