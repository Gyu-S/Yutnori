package com.ssafy.hungry.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StompDataDto {

    private String type;
    private String code;
    private Object data;

}
