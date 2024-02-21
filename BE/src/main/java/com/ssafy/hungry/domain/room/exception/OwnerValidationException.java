package com.ssafy.hungry.domain.room.exception;

public class OwnerValidationException extends RuntimeException{

    public OwnerValidationException(String message){
        super(message);
    }
}
