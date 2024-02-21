package com.ssafy.hungry.domain.pick.exception;

public class TeamNotFoundException extends RuntimeException{

    public TeamNotFoundException(String message) {
        super(message);
    }
}
