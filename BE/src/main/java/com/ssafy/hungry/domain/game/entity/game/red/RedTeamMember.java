package com.ssafy.hungry.domain.game.entity.game.red;

import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "red_team_member")
public class RedTeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "game_code", referencedColumnName = "game_code")
    private Game gameCode;

    private int userIndex;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;
}
