package com.ssafy.hungry.domain.game.controller;

import com.ssafy.hungry.domain.game.dto.*;
import com.ssafy.hungry.domain.game.repository.GameRepository;
import com.ssafy.hungry.domain.game.service.GameService;
import com.ssafy.hungry.domain.room.entity.RoomEntity;
import com.ssafy.hungry.domain.room.repository.RoomRedisRepository;
import com.ssafy.hungry.domain.room.repository.RoomRepository;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.global.repository.PrincipalRepository;
import com.ssafy.hungry.global.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameStompController {
    private final SessionRepository sessionRepository;
    private final PrincipalRepository principalRepository;
    private final RoomRepository roomRepository;
    private final RoomRedisRepository roomRedisRepository;
    private final UserRepository userRepository;
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameRepository gameRepository;

    //게임 시작
    @MessageMapping("/game/{roomCode}/start")
    public void gameStart(@DestinationVariable String roomCode, Principal principal) {

        // pub가 들어올 때마다 유저 추가하기
        String email = principalRepository.findById(principal.getName()).get().getEmail();
        log.info("gameStart 호출 : " + roomCode + " " + email);
        UserEntity user = userRepository.findByEmail(email);
        log.info("gameStart user검색 : " + roomCode + " " + user.toString());
        gameService.updateEnterUserInfo(roomCode, user);

        // 들어온 유저가 6명일 때만 game start
        if (gameService.getUserEnterCountInfo(roomCode) == 6) {
            RoomEntity room = roomRepository.findByRoomCode(roomCode);
            Map<String, Object> gamePreInfo = gameService.startGame(room);
//            Map<String, Object> gamePreInfo = gameService.startGameDummy();
            simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode + "/red", gamePreInfo.get("홍팀"));
            simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode + "/blue", gamePreInfo.get("청팀"));
        }
    }

    //윳을 던진 결과를 전달
    @MessageMapping("/game/{roomCode}/throw-yut")
    public void throwYut(@DestinationVariable String roomCode, ThrowYutDto dto) {
        dto.setActionCategory(1);
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }

    //이동에 선택된 말을 전달
    @MessageMapping("/game/{roomCode}/select-unit")
    public void selectUnit(@DestinationVariable String roomCode, SelectUnitDto dto) {
        dto.setActionCategory(2);
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }

    //밀정 추리 전달
    @MessageMapping("/game/{roomCode}/reasoning")
    public void reasoning(@DestinationVariable String roomCode, ReasoningDto dto) {
        dto.setActionCategory(3);
        dto.setSpy(gameService.isSpy(dto.getSelectedUnit(), dto.getTeam(), roomCode));
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }

    //말도착
    @MessageMapping("/game/{roomCode}/unit-gole")
    public void unitGole(@DestinationVariable String roomCode, UnitGoleDto dto){
        dto.setActionCategory(5);
        UnitGoleDto goleInfo = gameService.unitGole(roomCode, dto);
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, goleInfo);
    }

    //게임 종료
    @MessageMapping("/game/{roomCode}/finish")
    public void finish(@DestinationVariable String roomCode, GameFinishDto dto){
        dto.setActionCategory(9);
        gameService.saveGameResult(roomCode, dto.getTeam());
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }


    //힌드 받기 레디스에 RedUnitHint / blueUnitHint 로 나누고 방 코드로 저장
    @MessageMapping("/game/{roomCode}/hint")
    public void hint(@DestinationVariable String roomCode, MissionSuccessDto dto){
        dto.setActionCategory(10);
        dto = gameService.unitHint(roomCode ,dto);
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }

    //추리권 사용여부
    @MessageMapping("/game/{roomCode}/reason-ticket-use")
    public void reasonTicketUse(@DestinationVariable String roomCode, ReasonTicketDto dto){
        dto.setActionCategory(4);
        simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode, dto);
    }
    
    //게임 결과 (승리팀)
    @MessageMapping("/game/{roomCode}/chat")
    public void gameChat(@DestinationVariable String roomCode, GameChatDto gameChatDto){
        log.info("게임 채팅 호출 : " + roomCode + " " + gameChatDto.getMessage());
        gameChatDto.setActionCategory(6);
        if(gameChatDto.getTeam().equals("홍팀")){
            simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode + "/red", gameChatDto);

        }else if(gameChatDto.getTeam().equals("청팀")){
            simpMessagingTemplate.convertAndSend("/sub/game/" + roomCode + "/blue", gameChatDto);
        }

    }

    @MessageMapping("/game/selecttest")
    public void test2() {
        gameService.gameSelectTest();
    }


}

