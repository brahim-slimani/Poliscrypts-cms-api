package com.poliscrypts.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private String password;
}
