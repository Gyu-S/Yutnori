package com.ssafy.hungry.domain.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomDetailDto {

    private String title;
    private boolean isPublic;
    private int gameSpeed;
    private int currentUserCount;
    private String theme;

}
