package com.ssafy.hungry.domain.pick.controller;

import com.ssafy.hungry.domain.pick.exception.TeamNotFoundException;
import com.ssafy.hungry.domain.pick.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PickControllerAdvice {

    @ExceptionHandler({TeamNotFoundException.class})
    public ResponseEntity<String> handleTeamNotFoundException(TeamNotFoundException e){
        return ResponseEntity.status(403).body("This team does not exist.");
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(403).body("This user does not exist.");
    }
}
