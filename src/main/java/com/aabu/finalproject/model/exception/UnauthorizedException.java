package com.aabu.finalproject.model.exception;

import java.util.Map;

public class UnauthorizedException extends BaseApiException{


    public UnauthorizedException(String code, String message, Map<String, Object> details) {
        super(code, message, details);
    }
    public UnauthorizedException(String code, String message) {
        super(code, message, Map.of());
    }
}
