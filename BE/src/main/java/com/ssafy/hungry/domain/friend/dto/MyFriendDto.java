package com.ssafy.hungry.domain.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyFriendDto {
    private String email;
    private String nickname;
    private boolean isOnline;
}
