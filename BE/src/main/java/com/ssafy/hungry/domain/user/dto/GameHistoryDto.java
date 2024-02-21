package com.ssafy.hungry.domain.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class GameHistoryDto {

    private int gameSpeed;
    private String gameTheme;
    private int[] missionRegion;
    private String winner;
    private String blueSpyName;
    private String redSpyName;
    private String redSpyHint;
    private String blueSpyHint;
    private boolean redTeamReasoningResult;
    private boolean blueTeamReasoningResult;
    private Date createdDate;
    private Date endDate;
    private List<BlueTeamMemberDto> blueTeamMemberDtoList = new ArrayList<>();
    private List<RedTeamMemberDto> redTeamMemberDtoList = new ArrayList<>();
    private List<BlueTeamUnitDto> blueTeamUnitDtoList = new ArrayList<>();
    private List<RedTeamUnitDto> redTeamUnitDtoList = new ArrayList<>();

}
