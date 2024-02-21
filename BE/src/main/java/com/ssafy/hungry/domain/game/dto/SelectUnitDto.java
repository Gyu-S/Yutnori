package com.ssafy.hungry.domain.game.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SelectUnitDto {
    private int actionCategory;
    private int unitIndex;
    private int team;
    private boolean isGoDiagonal;
    private boolean isCenterDir;
}
