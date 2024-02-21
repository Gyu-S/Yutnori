package com.ssafy.hungry.domain.room.exception;

public class CannotChangeTeamException extends RuntimeException{
    public CannotChangeTeamException(String message){
        super((message));
    }
}
