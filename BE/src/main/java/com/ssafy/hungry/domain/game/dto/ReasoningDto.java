package com.ssafy.hungry.domain.game.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReasoningDto {
    private int team;
    private int actionCategory;
    private int selectedUnit;
    private boolean isSpy;
}
