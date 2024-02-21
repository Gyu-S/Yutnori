package com.ssafy.hungry.domain.pick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrentUserPickDto {

    private int team;
    private int seatNumber;
    private int userId;
    private String nickname;
    private int selectUnitId;
    private boolean pick;

}
