package com.ssafy.hungry.domain.room.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class CreateRoomDto {

    private String title;
    private boolean isPublic;
    private String password;
    private String theme;
    private int speed;

}
