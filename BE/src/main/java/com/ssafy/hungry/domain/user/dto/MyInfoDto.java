package com.ssafy.hungry.domain.user.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class MyInfoDto {
    private String email;
    private String nickname;
    private Date birthDate;
    private String gender;
    private String profileImgUrl;
}