package com.ssafy.hungry.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class JoinDto {
    private String email;
    private String nickname;
    private String password;
    private Date birthDate;
    private String gender;
}