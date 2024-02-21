package com.ssafy.hungry.domain.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomDto {

    private int roomId;
    private int currentUserCount;
    private String title;
    private boolean isPublic;

}
