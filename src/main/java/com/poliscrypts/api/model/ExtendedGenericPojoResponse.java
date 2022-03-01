package com.poliscrypts.api.model;

import lombok.Data;

@Data
public class ExtendedGenericPojoResponse<T> extends GenericPojoResponse {
    private T data;
    public static final String SUCCESS_MESSAGE = "Sucess";
    public ExtendedGenericPojoResponse(T data) {
        super (0, SUCCESS_MESSAGE);
        this.data = data;
    }
}
