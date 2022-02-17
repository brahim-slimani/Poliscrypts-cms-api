package com.poliscrypts.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactException extends RuntimeException {

    private Integer code;

    public ContactException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
