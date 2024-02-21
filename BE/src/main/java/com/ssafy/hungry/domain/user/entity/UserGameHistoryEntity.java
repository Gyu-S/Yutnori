package com.ssafy.hungry.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.hungry.domain.game.entity.game.Game;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_game_history")
public class UserGameHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity user;


    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "game_code", referencedColumnName = "game_code")
    public Game gameCode;
}
