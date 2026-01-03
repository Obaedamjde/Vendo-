package com.aabu.finalproject.model.exception;

import java.util.Map;

public class NotFoundException extends BaseApiException{

    public NotFoundException(String code, String message, Map<String, Object> details) {
        super(code, message, details);
    }
    public NotFoundException(String code, String message) {
        super(code, message, Map.of());
    }
}
