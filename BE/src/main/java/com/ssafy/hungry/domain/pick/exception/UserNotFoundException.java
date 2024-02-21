package com.ssafy.hungry.domain.pick.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) { super(message); }
}
