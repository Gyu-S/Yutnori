package com.ssafy.hungry.domain.room.exception;

public class AllUsersNotReadyException extends RuntimeException{

    public AllUsersNotReadyException(String message){
        super((message));
    }
}
