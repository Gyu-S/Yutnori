package com.ssafy.hungry.domain.game.repository;


import com.ssafy.hungry.domain.game.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Game findByGameCode(String gameCode);
    Boolean existsByGameCode(String gameCode);
}
