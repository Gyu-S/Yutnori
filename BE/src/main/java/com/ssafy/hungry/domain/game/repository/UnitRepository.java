package com.ssafy.hungry.domain.game.repository;

import com.ssafy.hungry.domain.game.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitEntity, Integer> {
    UnitEntity findById(int id);
}
