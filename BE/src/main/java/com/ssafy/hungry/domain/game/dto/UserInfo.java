package com.ssafy.hungry.domain.game.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    private String email;
    private String nickname;
    private String profileImgUrl;
}
