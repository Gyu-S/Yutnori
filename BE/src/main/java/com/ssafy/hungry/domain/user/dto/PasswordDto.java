package com.ssafy.hungry.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private String previousPassword;
    private String nextPassword;
}
