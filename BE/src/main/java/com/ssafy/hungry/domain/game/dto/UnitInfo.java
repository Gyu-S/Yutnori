package com.ssafy.hungry.domain.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnitInfo {

    private int unitId;
    private String name;
    private String skill;
    private String place;
    private String time;
    private String contactor;
    private String stuff;
    private String scal;
}
