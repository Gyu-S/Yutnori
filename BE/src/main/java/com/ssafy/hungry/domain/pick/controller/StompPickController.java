package com.ssafy.hungry.domain.pick.controller;

import com.ssafy.hungry.domain.pick.dto.*;
import com.ssafy.hungry.domain.pick.service.PickRedisService;
import com.ssafy.hungry.domain.room.dto.CurrentSeatDto;
import com.ssafy.hungry.domain.room.service.RoomRedisService;
import com.ssafy.hungry.global.dto.StompDataDto;
import com.ssafy.hungry.global.service.RedisSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompPickController {

    private final PickRedisService pickRedisService;
    private final RoomRedisService roomRedisService;
    private final RedisSender redisSender;
    private final ChannelTopic roomTopic;


    private final static int PICK_TIME = 15;

    // 유닛 선택이 시작되면 유닛 선택 정보 반환, 선택할 수 있는 유닛 리스트도 반환.
    @MessageMapping(value = "/pick/{roomCode}/get-pre-info")
    public void getPrePickInfo(@DestinationVariable String roomCode) {
        // 픽이 시작되었기 때문에 방 비활성화
        pickRedisService.disableRoom(roomCode);

        log.info("유닛 선택 호출 : " + roomCode);

        // 현재 유저 선택 정보 불러오기
        Map<String, List<CurrentUserPickDto>> currentUserPickInfo = pickRedisService.getCurrentUserPickInfo(roomCode);

        // 현재 유닛 리스트 불러오기
        Map<String, List<CurrentUnitPickDto>> currentUnitPickInfo = pickRedisService.getCurrentUnitPickInfo(roomCode);

        // 홍팀에 맞는 정보 넣기
        Map<String, Object> redTeamPickInfo = new HashMap<>();
        redTeamPickInfo.put("userInfo", currentUserPickInfo.get("홍팀"));
        redTeamPickInfo.put("unitInfo", currentUnitPickInfo.get("홍팀"));

        // 청팀에 맞는 정보 넣기
        Map<String, Object> blueTeamPickInfo = new HashMap<>();
        blueTeamPickInfo.put("userInfo", currentUserPickInfo.get("청팀"));
        blueTeamPickInfo.put("unitInfo", currentUnitPickInfo.get("청팀"));


        // 홍팀에게 홍팀 캐릭터 선택 정보 보내기
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_GET_PRE_INFO")
                .code(roomCode + "/red")
                .data(redTeamPickInfo) // Data 보내기
                .build());

        // 청팀에게 청팀 캐릭터 선택 정보 보내기
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_GET_PRE_INFO")
                .code(roomCode + "/blue")
                .data(blueTeamPickInfo) // Data 보내기
                .build());

    }


    // 유닛 픽 시작을 알림. timer 정보와, 픽을 시작해야하는 인원을 지정
    @MessageMapping(value = "/pick/{roomCode}/start")
    public void startPick(@DestinationVariable String roomCode) {
        log.info("유닛 픽 시작 호출 : " + roomCode);
        // 각 팀 첫번째 픽 순서 찾기
        String RedTeamPickEmail = pickRedisService.findPickOrder(roomCode, "홍팀");
        String BlueTeamPickEmail = pickRedisService.findPickOrder(roomCode, "청팀");

        // 홍팀에게 첫번째로 픽할 유저 보내기
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_ORDER")
                .code(roomCode + "/red")
                .data(OrderPickDto.builder()
                        .Email(RedTeamPickEmail)
                        .time(PICK_TIME)
                        .build()) // Data 보내기
                .build());

        // 청팀에게 첫번째로 픽할 유저 보내기
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_ORDER")
                .code(roomCode + "/blue")
                .data(OrderPickDto.builder()
                        .Email(BlueTeamPickEmail)
                        .time(PICK_TIME)
                        .build()) // Data 보내기
                .build());

    }
    // 한 유저가 유닛을 고르는 상황을 전달
    @MessageMapping(value ="/pick/{roomCode}/select")
    public void selectPick(@DestinationVariable String roomCode, PickInfoDto pickInfoDto){
        log.info("유닛 선택 상황 최신화 호출 " + roomCode);

        // 팀 정보를 확인하여 해당 팀의 픽 정보를 수정, 수정된 정보 얻기
        List<CurrentUserPickDto> currentUserPickDtoList = pickRedisService.updateCurrentPickInfo(roomCode, pickInfoDto);

        // 전달할 팀 고르기
        String targetRoomCode = "";
        if (pickInfoDto.getTeam().equals("홍팀")) {
            targetRoomCode = roomCode + "/red";
        } else if (pickInfoDto.getTeam().equals("청팀")) {
            targetRoomCode = roomCode + "/blue";
        }

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("userInfo", currentUserPickDtoList);

        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_SELECT")
                .code(targetRoomCode)
                .data(sendData) // Data 보내기
                .build());

    }

    // 한 유저가 pick이 완료 되었다면 redis에 수정사항 최신화
    @MessageMapping(value = "/pick/{roomCode}/done")
    public void pickDone(@DestinationVariable String roomCode, PickInfoDto pickInfoDto) {
        log.info("유닛 픽 완료 호출 : " + roomCode);

        // 팀 정보를 확인하여 해당 팀의 픽 정보를 수정
        Map<String, Object> allPickInfo = pickRedisService.updateFinalPickInfo(roomCode, pickInfoDto);

        // 전달할 팀
        String targetRoomCode = "";
        if (pickInfoDto.getTeam().equals("홍팀")) {
            targetRoomCode = roomCode + "/red";
        } else if (pickInfoDto.getTeam().equals("청팀")) {
            targetRoomCode = roomCode + "/blue";
        }

        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_SELECT_DONE")
                .code(targetRoomCode)
                .data(allPickInfo) // Data 보내기
                .build());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 양팀 픽이 다 끝났는지 확인하기
        // 양팀 픽이 다 끝났다면 밀정 픽 시작
        if (pickRedisService.isDonePick(roomCode)) {

            log.info("밀정 픽 시작 : " + roomCode);

            // 선택했던 유닛 정보 불러오기
            List<CurrentUnitPickDto> RedPickList = pickRedisService.getPickUnitList(roomCode, "홍팀");
            List<CurrentUnitPickDto> BluePickList = pickRedisService.getPickUnitList(roomCode, "청팀");

            // 각 팀에게 상대방의 픽 정보 넘기기
            redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                    .type("PICK_SELECT_SPY")
                    .code(roomCode + "/red")
                    .data(BluePickList) // Data 보내기
                    .build());

            redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                    .type("PICK_SELECT_SPY")
                    .code(roomCode + "/blue")
                    .data(RedPickList) // Data 보내기
                    .build());

        }

        // 양팀 픽이 끝나지 않았다면 다음 픽 순서를 확인 후 다음 픽할 사람을 지목
        else {
            log.info("다음 픽 정보 전달 : " + pickInfoDto.getTeam() + roomCode);

            // 들어온 팀의 다음번 픽 순서를 확인
            String pickEmail = pickRedisService.findPickOrder(roomCode, pickInfoDto.getTeam());

            // 한 팀만 끝났을 경우
            if (pickEmail.equals("")) {
                redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                        .type("PICK_WAIT")
                        .code(targetRoomCode)
                        .data("모든 픽이 완료될 때까지 기다려주세요.") // Data 보내기
                        .build());
            }

            // 다음 픽을 할 유저 정보 전달
            else {
                redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                        .type("PICK_NEXT")
                        .code(targetRoomCode)
                        .data(OrderPickDto.builder()
                                .Email(pickEmail)
                                .time(PICK_TIME)
                                .build()) // Data 보내기
                        .build());
            }


        }

    }

    // 밀정 선택 완료
    @MessageMapping(value = "/pick/{roomCode}/spy")
    public void spyPick(@DestinationVariable String roomCode, SpyPickDto spyPickDto) {
        log.info("SPY PICK 완료 : " + spyPickDto.getTeam() + roomCode);

        // 밀정 선택 정보 최신화
        pickRedisService.updateSpyInfo(roomCode, spyPickDto.getTeam(), spyPickDto.getUnitId());

        // 전달할 팀
        String targetRoomCode = "";
        if (spyPickDto.getTeam().equals("홍팀")) {
            targetRoomCode = roomCode + "/red";
        } else if (spyPickDto.getTeam().equals("청팀")) {
            targetRoomCode = roomCode + "/blue";
        }

        // 한 팀만 들어온 경우
        if (pickRedisService.countSpyInfo(roomCode) == 1){
            redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                    .type("PICK_SPY_WAIT")
                    .code(targetRoomCode)
                    .data("모든 픽이 완료될 때까지 기다려주세요.")
                    .build());
        }

        // 두 팀 다 들어온 경우
        else if (pickRedisService.countSpyInfo(roomCode) == 2){
            log.info("두팀 다 완료 : " + spyPickDto.getTeam() + roomCode);

            Map<String, Object> redTeamAllPickInfo = new HashMap<>();
            Map<String, Object> blueTeamAllPickInfo = new HashMap<>();

            // 밀정 정보 불러오기
            Map<Object,Object> spyPickInfo = pickRedisService.getSpyInfo(roomCode);

            // 각자 선택한 밀정 정보 가져오기 (적에 심어둔 우리팀의 밀정)
            redTeamAllPickInfo.put("MySpyInfo",spyPickInfo.get("청팀"));
            blueTeamAllPickInfo.put("MySpyInfo", spyPickInfo.get("홍팀"));

            // 현재 모든 유저 정보 가져오기
            List<CurrentSeatDto> currentSeatDtoList = roomRedisService.getCurrentRoomInfo(roomCode);

            redTeamAllPickInfo.put("allUserInfo",currentSeatDtoList);
            blueTeamAllPickInfo.put("allUserInfo",currentSeatDtoList);

            // 유닛 정보 가져오기
            List<CurrentUnitPickDto> redTeamUnitList = pickRedisService.getPickUnitList(roomCode,"홍팀");
            List<CurrentUnitPickDto> blueTeamUnitList= pickRedisService.getPickUnitList(roomCode,"청팀");
            redTeamAllPickInfo.put("redTeamUnitList",redTeamUnitList);
            redTeamAllPickInfo.put("redTeamUnitList",redTeamUnitList);
            redTeamAllPickInfo.put("blueTeamUnitList",blueTeamUnitList);
            blueTeamAllPickInfo.put("blueTeamUnitList",blueTeamUnitList);


            // 각 팀에게 정보 전달
            redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                    .type("GAME_START")
                    .code(roomCode + "/red")
                    .data(redTeamAllPickInfo)
                    .build());

            redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                    .type("GAME_START")
                    .code(roomCode + "/blue")
                    .data(blueTeamAllPickInfo)
                    .build());

        }
    }

    // 유닛 선택창의 팀 채팅
    @MessageMapping(value = "/pick/{roomCode}/chat")
    public void pickChat(@DestinationVariable String roomCode, PickChatMessageDto pickChatMessageDto) {
        log.info("유닛 선택 창 채팅 : " + pickChatMessageDto.getTeam() + roomCode);

        String targetRoomCode = "";
        if (pickChatMessageDto.getTeam().equals("홍팀")) {
            targetRoomCode = roomCode + "/red";
        } else if (pickChatMessageDto.getTeam().equals("청팀")) {
            targetRoomCode = roomCode + "/blue";
        }

        // redis Room Channel topic에 전달
        redisSender.sendToRedis(roomTopic, StompDataDto.builder()
                .type("PICK_CHAT")
                .code(targetRoomCode)
                .data(pickChatMessageDto)
                .build());
    }

}
