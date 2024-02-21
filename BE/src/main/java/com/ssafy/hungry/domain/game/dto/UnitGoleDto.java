package com.ssafy.hungry.domain.game.dto;

import lombok.Data;

@Data
public class UnitGoleDto {
    private int actionCategory;
    private int team;
    private int[] unitId;
    private boolean isSpy;
    private int spyId;
}
