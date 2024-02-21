package com.ssafy.hungry.domain.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameChatDto {

    private int actionCategory;
    private String team;
    private String nickname;
    private String message;

}
