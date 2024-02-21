package com.ssafy.hungry.domain.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class GameStartDto {

    private int actionCategory;
    private int[] missionRegion;
    private int gameSpeed;
    private String gameTheme;
    private List<UserInfo> redTeamUserList;
    private List<UserInfo> blueTeamUserList;
    private List<UnitInfo> redTeamUnitList;
    private List<UnitInfo> blueTeamUnitList;
    private int mySpyUnitId;
    private String mySpyHint;

}
