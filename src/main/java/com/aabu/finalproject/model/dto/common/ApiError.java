package com.aabu.finalproject.model.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String code;
    private String message;
    private HttpStatus status ;
    private OffsetDateTime timestamp;
    private String path;
    private Map<String,Object> details;
}
