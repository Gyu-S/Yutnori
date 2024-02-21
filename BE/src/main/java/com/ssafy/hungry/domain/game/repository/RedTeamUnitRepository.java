package com.ssafy.hungry.domain.game.repository;

import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamUnit;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedTeamUnitRepository extends JpaRepository<RedTeamUnit, Integer> {
    RedTeamUnit findByGameCodeAndUnitIndex(Game gameCode, int unitIndex);
    RedTeamUnit findByGameCodeAndUnitId(Game gameCode, UnitEntity unitId);
}
