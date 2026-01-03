package com.aabu.finalproject.model.exception;

import java.util.Map;

public class ConflictException extends BaseApiException{
    public ConflictException(String code, String message, Map<String, Object> details) {
        super(code, message, details);
    }
    public ConflictException(String code, String message) {
        super(code, message, Map.of());
    }
}
