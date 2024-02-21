package com.ssafy.hungry.domain.game.dto;

import lombok.Data;

@Data
public class MissionSuccessDto {
    private int ActionCategory;
    private int team;
    private int unitId;
    private String hint;
    private int category;
}
