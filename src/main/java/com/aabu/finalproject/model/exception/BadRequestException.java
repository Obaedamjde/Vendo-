package com.aabu.finalproject.model.exception;

import java.util.Map;

public class BadRequestException  extends BaseApiException{
    public BadRequestException(String code, String message, Map<String, Object> details) {
        super(code, message, details);
    }
    public BadRequestException(String code, String message) {
        super(code, message, Map.of());
    }
}
