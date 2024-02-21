package com.ssafy.hungry.domain.room.controller;

import com.ssafy.hungry.domain.room.dto.RoomChatMessageDto;
import com.ssafy.hungry.domain.room.dto.CurrentSeatDto;
import com.ssafy.hungry.domain.room.dto.RoomLobbyInfoDto;
import com.ssafy.hungry.domain.room.entity.RoomEntity;
import com.ssafy.hungry.domain.room.service.RoomRedisService;
import com.ssafy.hungry.domain.room.service.RoomService;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.service.UserService;
import com.ssafy.hungry.global.dto.StompDataDto;
import com.ssafy.hungry.global.service.RedisSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompRoomController {

    private final RoomService roomService;
    private final RoomRedisService roomRedisService;
    private final UserService userService;
    private final RedisSender redisSender;
    private final ChannelTopic roomTopic;

    // 방 입장
    @MessageMapping(value = "/room/{roomCode}/enter")
    public void enterRoom (@DestinationVariable String roomCode , String email){
        log.info("방 입장 호출 : " + roomCode);

        // token 을 가져와 userEntity 생성
        UserEntity user = userService.findByEmail(email);
        // room code를 가져와 roomEntity 생성
        RoomEntity room = roomService.getRoomByRoomCode(roomCode);

        // redis room 정보 최신화
        RoomLobbyInfoDto roomLobbyInfoDto = roomRedisService.userEnterRoom(room, user);

        // redis Room Channel topic에 전달
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("ROOM_ENTER_INFO")
                .code(roomCode)
                .data(roomLobbyInfoDto)
                .build());

    }

    // 방 나가기
    // 방장이 나갈 경우 방이 삭제되는 기능 구현
    @MessageMapping(value = "/room/{roomCode}/exit")
    public void exitRoom(@DestinationVariable String roomCode , String email){
        log.info("방 나가기 호출 : " + roomCode);
        // token 을 가져와 userEntity 생성
        UserEntity user = userService.findByEmail(email);

        // room code를 가져와 roomEntity 생성
        RoomEntity room = roomService.getRoomByRoomCode(roomCode);

        // 방 나가기 로직 실행
        RoomLobbyInfoDto roomLobbyInfoDto = roomRedisService.userExitRoom(room, user);

        // redis Room Channel topic에 전달
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("ROOM_EXIT_INFO")
                .code(roomCode)
                .data(roomLobbyInfoDto)
                .build());
    }

    // 방 채팅
    @MessageMapping(value = "/room/{roomCode}/chat")
    public void chatRoom(@DestinationVariable String roomCode, RoomChatMessageDto roomChatMessageDto){
        log.info("방 채팅 호출 : " + roomCode);

        // redis Room Channel topic에 전달
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("ROOM_CHAT")
                .code(roomCode)
                .data(roomChatMessageDto)
                .build());

    }
    // 방 팀 변경
    @MessageMapping(value = "/room/{roomCode}/change")
    public void changeTeam(@DestinationVariable String roomCode, String email){
        log.info("팀 변경 호출 : " + roomCode);
        // 이메일로 유저 찾기
        UserEntity user = userService.findByEmail(email);

        List<CurrentSeatDto> updateSeatDtoList= roomRedisService.changeTeam(roomCode, user);

        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("ROOM_CHANGE_TEAM")
                .code(roomCode)
                .data(updateSeatDtoList)
                .build());

    }

    // 방 게임 준비
    @MessageMapping(value = "/room/{roomCode}/ready")
    public void readyRoom(@DestinationVariable String roomCode, String email){
        log.info("방 게임 준비 호출 : " + roomCode);

        // 이메일로 유저 정보 가져오기
        UserEntity user = userService.findByEmail(email);

        // ready 상태로 현재 좌석 정보 최신화 시키기
        List<CurrentSeatDto> currentSeatDtoList = roomRedisService.userReadyRoom(roomCode, user.getId());

        // redis Room Channel topic에 전달
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("ROOM_READY")
                .code(roomCode)
                .data(currentSeatDtoList)
                .build());

    }


    // 게임 시작 (캐릭터 선택 창으로 이동 전 처리)
    @MessageMapping(value = "/room/{roomCode}/start")
    public void startRoom(@DestinationVariable String roomCode, String email){
        log.info("방 게임 시작 호출 : " + roomCode);
        // 이메일로 유저 정보 가져오기
        UserEntity user = userService.findByEmail(email);

        // room code를 가져와 room의 정보 가져오기
        RoomEntity room = roomService.getRoomByRoomCode(roomCode);

        roomRedisService.enterPickRoom(room, user);

        // 구독자들에게 캐릭터 선택이 시작되었음을 보내기
        redisSender.sendToRedis(roomTopic,StompDataDto.builder()
                .type("ROOM_START_PICK")
                .code(roomCode)
                .data("캐릭터 선택 시작") // Data 보내기
                .build());

    }

}
