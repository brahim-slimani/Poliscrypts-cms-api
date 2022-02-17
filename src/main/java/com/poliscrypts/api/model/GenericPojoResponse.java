package com.poliscrypts.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericPojoResponse {
    private int code;
    private String message;
}
