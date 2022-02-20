package com.poliscrypts.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenException extends RuntimeException {

    private Integer code;

    public TokenException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
