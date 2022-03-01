package com.poliscrypts.api.exception;

import lombok.Data;

@Data
public class CompanyException extends RuntimeException {
    private Integer code;
    public CompanyException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
