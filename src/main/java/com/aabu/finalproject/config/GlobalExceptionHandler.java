package com.aabu.finalproject.config;


import com.aabu.finalproject.model.dto.common.ApiError;
import com.aabu.finalproject.model.exception.BadRequestException;
import com.aabu.finalproject.model.exception.ConflictException;
import com.aabu.finalproject.model.exception.NotFoundException;
import com.aabu.finalproject.model.exception.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice // Global , operate on all system
public class GlobalExceptionHandler {


    private ApiError Build (String code, String message , HttpStatus status , WebRequest request, Map<String,Object> details){
        return ApiError.builder()
                        .code(code)
                                .message(message)
                                        .status(status)
                                                .timestamp(OffsetDateTime.now())
                .path(request.getDescription(false).replace("uri=",""))

                // return only the link not all this meta data   "path": "session=ABC123;client=192.168.1.20;uri=/api/users/5"
                //.path(request.getContextPath()) just return the first link
                //https://example.com/myapp/api/users -->> {myapp}
                                                                .details(details).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex ,WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Build(ex.getCode(),ex.getMessage(),HttpStatus.NOT_FOUND ,request,ex.getDetails()));
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex ,WebRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Build(ex.getCode(),ex.getMessage(),HttpStatus.BAD_REQUEST ,request,ex.getDetails()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex ,WebRequest request){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Build(ex.getCode(),ex.getMessage(),HttpStatus.CONFLICT ,request,ex.getDetails()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Build(ex.getCode(), ex.getMessage(), HttpStatus.UNAUTHORIZED, request, ex.getDetails()));
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation (MethodArgumentNotValidException ex ,WebRequest request){

        var details= ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fe->fe.getField(),
                        fe-> (Object) (fe.getDefaultMessage()!=null?fe.getDefaultMessage() :fe.getCode() ),

                        (first,second )->first // if duplicate error like {not blank } chose just one
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Build("VALIDATION_ERROR","Invalid request data" ,HttpStatus.BAD_REQUEST,request,details));

    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex ,WebRequest request){


        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Build("DATA_INTEGRITY_VIOLATION","Data conflict",HttpStatus.CONFLICT,request,
                        Map.of("root",ex.getMostSpecificCause()!=null ?ex.getMostSpecificCause().getMessage() : ex.getMessage())));

    }


    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ApiError> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException ex ,WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Build("AuthenticationCredentialsNotFoundException " ,"Must be login " ,HttpStatus.UNAUTHORIZED , request,
                        Map.of("root", ex.getMessage())));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>handleOther(Exception ex,WebRequest request){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Build("INTERNAL_ERROR","somthing get error ",HttpStatus.INTERNAL_SERVER_ERROR ,request ,Map.of("root", ex.getMessage())));
    }

}
