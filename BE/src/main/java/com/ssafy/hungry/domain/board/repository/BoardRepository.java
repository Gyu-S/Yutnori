package com.ssafy.hungry.domain.board.repository;

import com.ssafy.hungry.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

}
