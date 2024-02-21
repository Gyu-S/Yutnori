package com.ssafy.hungry.domain.pick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PickInfoDto {

    private String team;
    private String email;
    private int unitId;

}
