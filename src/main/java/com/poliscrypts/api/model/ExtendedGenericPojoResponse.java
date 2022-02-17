package com.poliscrypts.api.model;

import lombok.Data;

@Data
public class ExtendedGenericPojoResponse<T> extends GenericPojoResponse {
    private T data;

    public ExtendedGenericPojoResponse(T data) {
        super (0, "Sucess");
        this.data = data;
    }

    public ExtendedGenericPojoResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }
}
