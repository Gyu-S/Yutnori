package com.ssafy.hungry.domain.room.controller;

import com.ssafy.hungry.domain.room.exception.AllUsersNotReadyException;
import com.ssafy.hungry.domain.room.exception.CannotChangeTeamException;
import com.ssafy.hungry.domain.room.exception.OwnerValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomControllerAdvice {

    @ExceptionHandler({OwnerValidationException.class})
    public ResponseEntity<String> handleOwnerValidationException(OwnerValidationException e){
        return ResponseEntity.status(403).body("Only the room manager can execute the command.");
    }

    @ExceptionHandler({AllUsersNotReadyException.class})
    public ResponseEntity<String> handleAllUsersNotReadyException(AllUsersNotReadyException e){
        return ResponseEntity.status(403).body("Not all users are ready.");
    }

    @ExceptionHandler({CannotChangeTeamException.class})
    public ResponseEntity<String> handleCannotChangeTeamException(CannotChangeTeamException e){
        return ResponseEntity.status(403).body("Can not change team.");
    }

}
