package com.poliscrypts.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserException extends RuntimeException {

    private Integer code;

    public UserException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
