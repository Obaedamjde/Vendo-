package com.aabu.finalproject.model.exception;


import lombok.Getter;

import java.util.Map;

@Getter
public abstract  class BaseApiException extends RuntimeException{

    protected final String code;
    protected final Map<String,Object>details;

    protected BaseApiException(String code,String message ,Map<String,Object> details){
        super(message);
        this.code=code;
        this.details=details;
    }
}
