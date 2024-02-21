package com.ssafy.hungry.domain.pick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrentUnitPickDto {

    private int unitId;
    private String name;
    private int age;
    private String skill;
    private boolean isPick;

}
