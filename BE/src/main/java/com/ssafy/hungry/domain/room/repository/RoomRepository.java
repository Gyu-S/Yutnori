package com.ssafy.hungry.domain.room.repository;

import com.ssafy.hungry.domain.room.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    // owner_id를 통해 방 전체 찾기 (조건 : 종료되지 않은 방)
    List<RoomEntity> findAllByEndAtIsNullOrderByStartAt();

    // 게임을 시작하지 않거나, 종료되지 않은 방
    RoomEntity findByEndAtIsNullAndRoomId(int roomId);

    // room code 찾기
    @Query("SELECT r.roomCode FROM RoomEntity r WHERE r.roomId = ?1")
    String findByRoomId(int roomId);

    // room code로 방 찾기
    RoomEntity findByRoomCode(String roomCode);

}
