package com.ssafy.hungry.domain.room.controller;

import com.ssafy.hungry.domain.room.dto.CreateRoomDto;
import com.ssafy.hungry.domain.room.dto.RoomDetailDto;
import com.ssafy.hungry.domain.room.dto.RoomDto;
import com.ssafy.hungry.domain.room.entity.RoomEntity;
import com.ssafy.hungry.domain.room.service.RoomRedisService;
import com.ssafy.hungry.domain.room.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final RoomRedisService roomRedisService;

    // 방 전체 조회하기
    @GetMapping
    public ResponseEntity<List<RoomDto>> getRoomList(){
        log.info("방 목록 조회 호출");
        List<RoomDto> roomList = roomService.getRoomList();
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    // 방 세부 정보 조회
    @GetMapping("/detail/{roomId}")
    public ResponseEntity<RoomDetailDto> getRoomDetail(@PathVariable int roomId){
        log.info("방 세부 정보 조회");
        RoomDetailDto roomDetail = roomService.getRoomDetail(roomId);

        return new ResponseEntity<>(roomDetail, HttpStatus.OK);
    }

    // 방 생성. 방 생성 후 room code 반환.
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody CreateRoomDto createRoomDto){
        log.info("방 생성 : " + createRoomDto.getTitle());

        // 방을 개설한 유저 email 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String roomCode = roomService.createRoom(createRoomDto, email);

        return new ResponseEntity<>(roomCode, HttpStatus.CREATED);
    }

    // 방 입장 가능 여부 조회
    @PostMapping("/{roomId}")
    public ResponseEntity<String> canEnterRoom(@PathVariable int roomId, @RequestBody(required = false) Map<String, String> roomPassword){
        log.info("canEnterRoom 진입 / roomId : " + roomId);


        String password = roomPassword.get("password");
        // room Id로 방 entity 얻기
        RoomEntity room = roomService.getRoomByRoomId(roomId);

        // 해당 방이 시작했거나 종료하지않았는지 확인
        if(room == null){
            // 404 에러
            return new ResponseEntity<>("방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 가득 차 있다면
        if(roomRedisService.getCurrentUserCount(roomId) >= 6){
            // 403 에러
            return new ResponseEntity<>("방이 가득찼습니다.",HttpStatus.FORBIDDEN);
        }

        // 비공개 방 이라면
        if(!room.isPublic()){
            if(password == null){
                return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.FORBIDDEN);
            }
            // 비밀번호가 틀렸다면
            if(!roomService.validatePassword(password, room.getPassword())){
                return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.FORBIDDEN);
            }
        }

        // room code 반환
        return new ResponseEntity<>(room.getRoomCode(), HttpStatus.OK);
    }

}
