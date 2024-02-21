package com.ssafy.hungry.domain.pick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PickChatMessageDto {
    private String team;
    private String nickname;
    private String message;
}
