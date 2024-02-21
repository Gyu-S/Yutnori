package com.ssafy.hungry.domain.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SendRequestFriendDto {
    private String toUserEmail;
    private String toUserNickname;
}
