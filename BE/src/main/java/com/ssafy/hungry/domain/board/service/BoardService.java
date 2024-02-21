package com.ssafy.hungry.domain.board.service;

import com.ssafy.hungry.domain.board.entity.BoardEntity;
import com.ssafy.hungry.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardEntity> boardList() {
        return boardRepository.findAll();
    }
}
