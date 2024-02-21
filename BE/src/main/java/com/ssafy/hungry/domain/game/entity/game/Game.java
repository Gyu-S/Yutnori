package com.ssafy.hungry.domain.game.entity.game;

import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamMember;
import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamUnit;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamMember;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamUnit;
import com.ssafy.hungry.domain.user.entity.UserGameHistoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "game")
public class Game {

    @Id
    @Column(name = "game_Code")
    private String gameCode;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable=false)
//    private Date createdDate;
//
//    @UpdateTimestamp
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date endDate;

    private int gameSpeed;
    private String gameTheme;
    private int[] missionRegion;
    private int winner;
    private int blueSpyId;
    private int redSpyId;
    private String blueSpyHint;
    private String redSpyHint;
    private boolean redTeamReasoningResult;
    private boolean blueTeamReasoningResult;

    @OneToMany(mappedBy = "gameCode")
    private List<UserGameHistoryEntity> userList = new ArrayList<>();

    @OneToMany(mappedBy = "gameCode")
    private List<RedTeamMember> redteamMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "gameCode")
    private List<BlueTeamMember> blueTeamMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "gameCode")
    private List<BlueTeamUnit> blueTeamUnitList = new ArrayList<>();

    @OneToMany(mappedBy = "gameCode")
    private List<RedTeamUnit> redTeamUnitList = new ArrayList<>();
}
