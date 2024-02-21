package com.ssafy.hungry.domain.game.entity.game.red;
import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.game.entity.game.Game;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "red_team_unit")
public class RedTeamUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int unitIndex;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "game_code", referencedColumnName = "game_code")
    private Game gameCode;


//    @MapsId("redTeamStatus")
//    @ManyToOne
//    @ToString.Exclude
//    @JoinColumns({
//            @JoinColumn(name = "game_code", referencedColumnName = "game_code"),
//            @JoinColumn(name = "game_turn", referencedColumnName = "game_turn"),
//            @JoinColumn(name = "turn_index", referencedColumnName = "turn_index")
//    })
//    private RedTeamStatus redTeamStatus;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    private UnitEntity unitId;
    private String place;
    private String time;
    private String contactor;
    private String stuff;
    private String scal;

    private boolean isGole;
}
