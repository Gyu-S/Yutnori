package com.ssafy.hungry.domain.game.repository;

import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedTeamMemberRepository extends JpaRepository<RedTeamMember, Integer> {
}
