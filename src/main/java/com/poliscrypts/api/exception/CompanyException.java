package com.poliscrypts.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyException extends RuntimeException {

    private Integer code;

    public CompanyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
