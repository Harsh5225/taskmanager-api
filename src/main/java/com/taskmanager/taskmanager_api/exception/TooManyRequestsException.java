package com.taskmanager.taskmanager_api.exception;

public class TooManyRequestsException extends RuntimeException{
    public TooManyRequestsException(String message){
        super(message);
    }
}

// We don’t want to return 500 error
//We want proper HTTP 429 Too Many Requests