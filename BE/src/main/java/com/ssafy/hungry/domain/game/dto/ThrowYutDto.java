package com.ssafy.hungry.domain.game.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ThrowYutDto {
//    private String email;
//    private String nickname;
    private int actionCategory;
    private int yutRes;
    private Boolean[] throwRes;
    private String team;
}
