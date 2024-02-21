package com.ssafy.hungry.domain.room.dto;

import com.ssafy.hungry.domain.room.entity.RoomEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RoomLobbyInfoDto {

    private int ownerId;
    private List<CurrentSeatDto> currentSeatDtoList;
    private RoomDetailDto roomDetailDto;
    private String message;
    private int roomState;
}
