package com.ssafy.hungry.domain.game.repository;

import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlueTeamMemberRepository extends JpaRepository<BlueTeamMember, Integer> {
}
