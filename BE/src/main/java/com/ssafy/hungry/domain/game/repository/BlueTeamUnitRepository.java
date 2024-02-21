package com.ssafy.hungry.domain.game.repository;

import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlueTeamUnitRepository extends JpaRepository<BlueTeamUnit, Integer> {
    BlueTeamUnit findByGameCodeAndUnitIndex(Game gameCode, int unitIndex);
    BlueTeamUnit findByGameCodeAndUnitId(Game gameCode, UnitEntity unitId);
}
