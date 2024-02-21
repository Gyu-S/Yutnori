package com.ssafy.hungry.domain.miniGame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MiniGameDto {

    private int actionCategory;
    private String email;
    private boolean result;

}
