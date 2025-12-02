package com.taskmanager.taskmanager_api.exception;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Centralized error handling
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>>handleNotFound(ResourceNotFoundException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("error",ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }


    // I am  NOT handling Spring Security’s AccessDeniedException.
    // it is likely importing using own custom AccessDeniedException (or none at all), while Spring Security throws:
    // org.springframework.security.access.AccessDeniedException

    //    Spring Security throws
    //            → org.springframework.security.access.AccessDeniedException
    //    Your handler expects
    //→ com.taskmanager.taskmanager_api.exception.AccessDeniedException
    //    Mismatch → your handler is SKIPPED
    //    The exception falls into:   @ExceptionHandler(Exception.class)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String,Object>>handleGeneral(Exception ex){
//        Map<String,Object>error=new HashMap<>();
//        error.put("timeline",LocalDateTime.now());
//        error.put("error","something went wrong");
//        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
//    }
@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String,Object>> handleGeneral(Exception ex){
    ex.printStackTrace(); // THIS LINE IS IMPORTANT

    Map<String,Object> error = new HashMap<>();
    error.put("timeline", LocalDateTime.now());
    error.put("error", ex.getClass().getSimpleName());
    error.put("message", ex.getMessage());

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
}



}

