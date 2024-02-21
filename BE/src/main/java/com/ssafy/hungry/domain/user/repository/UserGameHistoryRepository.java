package com.ssafy.hungry.domain.user.repository;

import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.entity.UserGameHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGameHistoryRepository extends JpaRepository<UserGameHistoryEntity, Integer> {
    List<UserGameHistoryEntity> findByUserId(int user);
}
