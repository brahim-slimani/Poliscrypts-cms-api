package com.poliscrypts.api.exception;

import lombok.Data;

@Data
public class ContactException extends RuntimeException {
    private Integer code;
    public ContactException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
