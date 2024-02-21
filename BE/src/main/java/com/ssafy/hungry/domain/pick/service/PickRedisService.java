package com.ssafy.hungry.domain.pick.service;

import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.game.repository.GameRepository;
import com.ssafy.hungry.domain.game.repository.UnitRepository;
import com.ssafy.hungry.domain.pick.dto.CurrentUnitPickDto;
import com.ssafy.hungry.domain.pick.dto.CurrentUserPickDto;
import com.ssafy.hungry.domain.pick.dto.PickInfoDto;
import com.ssafy.hungry.domain.pick.exception.TeamNotFoundException;
import com.ssafy.hungry.domain.pick.repository.PickRedisRepository;
import com.ssafy.hungry.domain.room.dto.CurrentSeatDto;
import com.ssafy.hungry.domain.room.entity.RoomEntity;
import com.ssafy.hungry.domain.room.repository.RoomRedisRepository;
import com.ssafy.hungry.domain.room.repository.RoomRepository;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PickRedisService {

    private final PickRedisRepository pickRedisRepository;
    private final RoomRedisRepository roomRedisRepository;
    private final RoomRepository roomRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final static String PICK_KEY_PREFIX = "UserPickInfo:";
    private final static String ROOM_KEY_PREFIX = "RoomInfo:";
    private final static String RED_KEY_PREFIX = "RedUnitInfo:";
    private final static String BLUE_KEY_PREFIX = "BlueUnitInfo:";
    private final static String SPY_KEY_PREFIX = "SpyInfo:";

    // redis키 생성하기
    public String generateKey(String prefix, String roomCode) {
        return prefix + " " + roomCode;
    }

    // 방에서 게임 준비가 완료되면 유저들의 유닛 선택 정보를 currentSeatInfo를 활용하여 redis에 추가
    public void createCurrentUserPick(String roomCode) {
        String PickKey = generateKey(PICK_KEY_PREFIX, roomCode);
        String RoomKey = generateKey(ROOM_KEY_PREFIX, roomCode);

        List<CurrentSeatDto> currentSeatDtoList = roomRedisRepository.getCurrentRoomInfo(RoomKey);
        // 팀을 나눠서 저장 0 ~ 2번은 1팀(홍팀), 3 ~ 5번은 2팀(청팀)
        for (int i = 0; i < 3; i++) {
            pickRedisRepository.saveUserPickToRedis(PickKey, CurrentUserPickDto.builder()
                    .pick(false)
                    .team(1)
                    .seatNumber(i)
                    .userId(currentSeatDtoList.get(i).getUserId())
                    .nickname(currentSeatDtoList.get(i).getNickname())
                    .build());
        }

        for (int i = 3; i < 6; i++) {
            pickRedisRepository.saveUserPickToRedis(PickKey, CurrentUserPickDto.builder()
                    .pick(false)
                    .team(2)
                    .seatNumber(i)
                    .userId(currentSeatDtoList.get(i).getUserId())
                    .nickname(currentSeatDtoList.get(i).getNickname())
                    .build());
        }

    }

    // 홍팀, 청팀 유닛 정보를 redis에 저장하기
    public void createCurrentUnitPick(String roomCode) {
        String redUnitKey = generateKey(RED_KEY_PREFIX, roomCode);
        String blueUnitKey = generateKey(BLUE_KEY_PREFIX, roomCode);

        List<UnitEntity> unitList = unitRepository.findAll();
        log.info("유닛 정보 리스트 : " + unitList.toString());
        // 홍팀 유닛 정보 redis에 저장
        for (UnitEntity unit : unitList) {

            CurrentUnitPickDto unitPickDto = CurrentUnitPickDto.builder()
                    .unitId(unit.getId())
                    .name(unit.getName())
                    .skill(unit.getSkill())
                    .isPick(false)
                    .build();

            if (unit.getId() == 4 || unit.getId() == 5) {
                unitPickDto.setPick(true);
            }

            pickRedisRepository.saveUnitPickToRedis(redUnitKey, unitPickDto);
        }

        // 청팀 유닛 정보 redis에 저장
        for (UnitEntity unit : unitList) {

            CurrentUnitPickDto unitPickDto = CurrentUnitPickDto.builder()
                    .unitId(unit.getId())
                    .name(unit.getName())
                    .skill(unit.getSkill())
                    .isPick(false)
                    .build();

            if (unit.getId() == 4 || unit.getId() == 5) {
                unitPickDto.setPick(true);
            }

            pickRedisRepository.saveUnitPickToRedis(blueUnitKey, unitPickDto);
        }

    }

    // 현재 유저들의 유닛 선택 정보 반환하기
    public Map<String, List<CurrentUserPickDto>> getCurrentUserPickInfo(String roomCode) {
        String key = generateKey(PICK_KEY_PREFIX, roomCode);

        // 홍팀 유저들의 유닛 선택창 정보
        List<CurrentUserPickDto> redTeamCurrentPickInfo = pickRedisRepository.getCurrentUserPickInfo(key, 0, 2);

        // 청팀 유저들의 유닛 선택창 정보
        List<CurrentUserPickDto> blueTeamCurrentPickInfo = pickRedisRepository.getCurrentUserPickInfo(key, 3, 5);

        // 나눈 유저들의 유닛 선택창 정보를 Map에 담기
        Map<String, List<CurrentUserPickDto>> currentPickInfo = new HashMap<>();

        currentPickInfo.put("홍팀", redTeamCurrentPickInfo);
        currentPickInfo.put("청팀", blueTeamCurrentPickInfo);

        return currentPickInfo;
    }

    // 현재 유닛들의 정보와 선택 상황 정보 반환하기
    public Map<String, List<CurrentUnitPickDto>> getCurrentUnitPickInfo(String roomCode) {
        String redUnitKey = generateKey(RED_KEY_PREFIX, roomCode);
        String blueUnitKey = generateKey(BLUE_KEY_PREFIX, roomCode);

        // 홍팀 유저들의 유닛 선택창 정보
        List<CurrentUnitPickDto> redTeamCurrentUnitPickInfo = pickRedisRepository.getCurrentUnitPickInfo(redUnitKey);

        // 청팀 유저들의 유닛 선택창 정보
        List<CurrentUnitPickDto> blueTeamCurrentUnitPickInfo = pickRedisRepository.getCurrentUnitPickInfo(blueUnitKey);

        // 나눈 유저들의 유닛 선택창 정보를 Map에 담기
        Map<String, List<CurrentUnitPickDto>> currentUnitPickInfo = new HashMap<>();

        currentUnitPickInfo.put("홍팀", redTeamCurrentUnitPickInfo);
        currentUnitPickInfo.put("청팀", blueTeamCurrentUnitPickInfo);

        return currentUnitPickInfo;
    }

    // 각 팀의 픽 순서 찾기
    public String findPickOrder(String roomCode, String team) {

        String pickEmail = "";

        if (team.equals("홍팀")) {
            String key = generateKey(PICK_KEY_PREFIX, roomCode);
            List<CurrentUserPickDto> currentUserPickDtoList = pickRedisRepository.getCurrentUserPickInfo(key, 0, 2);

            int pickId = 0;
            for (CurrentUserPickDto userPick : currentUserPickDtoList) {
                if (!userPick.isPick()) {
                    pickId = userPick.getUserId();
                    break;
                }
            }

            if (pickId != 0) {
                UserEntity user = userRepository.findById(pickId);
                pickEmail = user.getEmail();
            }

        } else if (team.equals("청팀")) {
            String key = generateKey(PICK_KEY_PREFIX, roomCode);
            List<CurrentUserPickDto> currentUserPickDtoList = pickRedisRepository.getCurrentUserPickInfo(key, 3, 5);

            int pickId = 0;
            for (CurrentUserPickDto userPick : currentUserPickDtoList) {
                log.info("청팀 유저 픽 정보 : " + userPick.toString());
                if (!userPick.isPick()) {
                    pickId = userPick.getUserId();
                    break;
                }
            }

            if (pickId != 0) {
                UserEntity user = userRepository.findById(pickId);
                pickEmail = user.getEmail();
            }

        } else {
            throw new TeamNotFoundException("존재하지 않은 팀입니다.");
        }


        return pickEmail;
    }
    // 각 팀의 픽 상태 최신화
    public List<CurrentUserPickDto> updateCurrentPickInfo(String roomCode, PickInfoDto pickInfoDto){
        String userInfoKey = generateKey(PICK_KEY_PREFIX, roomCode);
        String teamKey = "";
        int count = 0;
        log.info("updateCurrentPickInfo 들어온 정보 : " + pickInfoDto.toString());
        if (pickInfoDto.getTeam().equals("홍팀")) {
            teamKey = generateKey(RED_KEY_PREFIX, roomCode);
        } else if (pickInfoDto.getTeam().equals("청팀")) {
            count = 3;
            teamKey = generateKey(BLUE_KEY_PREFIX, roomCode);
        } else {
            throw new TeamNotFoundException("존재하지 않은 팀입니다.");
        }

        // UserPickInfo와 UnitInfo 수정하기
        List<CurrentUserPickDto> currentUserPickDtoList = getCurrentUserPickInfo(roomCode).get(pickInfoDto.getTeam());
        UserEntity user = userRepository.findByEmail(pickInfoDto.getEmail());

        // UserPickInfo를 가지고 와서 픽 정보 수정하기
        for (CurrentUserPickDto userPick : currentUserPickDtoList) {
            if (userPick.getUserId() == user.getId()) {
                userPick.setSelectUnitId(pickInfoDto.getUnitId());
                pickRedisRepository.reSaveUserPickToRedis(userInfoKey, userPick, count);
                break;
            }
            count++;
        }
        return currentUserPickDtoList;
    }

    // 각 팀의 픽 정보 확정 최신화
    public Map<String, Object> updateFinalPickInfo(String roomCode, PickInfoDto pickInfoDto) {
        String userInfoKey = generateKey(PICK_KEY_PREFIX, roomCode);
        String teamKey = "";
        int count = 0;
        log.info("updateFinalPickInfo 들어온 팀 정보 : " + pickInfoDto.getTeam());
        if (pickInfoDto.getTeam().equals("홍팀")) {
            teamKey = generateKey(RED_KEY_PREFIX, roomCode);
        } else if (pickInfoDto.getTeam().equals("청팀")) {
            count = 3;
            teamKey = generateKey(BLUE_KEY_PREFIX, roomCode);
        } else {
            throw new TeamNotFoundException("존재하지 않은 팀입니다.");
        }

        // UserPickInfo와 UnitInfo 수정하기
        List<CurrentUserPickDto> currentUserPickDtoList = getCurrentUserPickInfo(roomCode).get(pickInfoDto.getTeam());
        UserEntity user = userRepository.findByEmail(pickInfoDto.getEmail());

        // UserPickInfo를 가지고 와서 픽 정보 수정하기
        for (CurrentUserPickDto userPick : currentUserPickDtoList) {
            if (userPick.getUserId() == user.getId()) {
                userPick.setSelectUnitId(pickInfoDto.getUnitId());
                userPick.setPick(true);
                pickRedisRepository.reSaveUserPickToRedis(userInfoKey, userPick, count);
                break;
            }
            count++;
        }

        // UnitInfo를 가지고 와서 픽 정보 수정하기
        List<CurrentUnitPickDto> currentUnitPickDtoList = getCurrentUnitPickInfo(roomCode).get(pickInfoDto.getTeam());

        count = 0;
        for (CurrentUnitPickDto unitPick : currentUnitPickDtoList) {
            if (unitPick.getUnitId() == pickInfoDto.getUnitId()) {
                unitPick.setPick(true);
                pickRedisRepository.reSaveUnitPickToRedis(teamKey, unitPick, count);
                break;
            }
            count++;
        }
        Map<String, Object> allPickInfo = new HashMap<>();
        allPickInfo.put("userInfo",currentUserPickDtoList);
        allPickInfo.put("unitInfo", currentUnitPickDtoList);
        return allPickInfo;
    }

    // 상대팀 픽이 다 끝났는지 확인하기
    public boolean isDonePick(String roomCode) {
        String key = generateKey(PICK_KEY_PREFIX, roomCode);

        Boolean isDone = true;
        List<CurrentUserPickDto> userPickDtoList = pickRedisRepository.getCurrentUserPickInfo(key, 0, -1);

        for (CurrentUserPickDto userPick : userPickDtoList) {
            if (!userPick.isPick()) {
                isDone = false;
            }
        }

        return isDone;
    }

    // 선택한 유닛의 정보 찾아오기
    public List<CurrentUnitPickDto> getPickUnitList(String roomCode, String team) {
        String key = "";
        List<CurrentUnitPickDto> allUnitPickDtoList = null;
        List<CurrentUnitPickDto> unitPickDtoList = new ArrayList<>();
        if (team.equals("홍팀")) {
            key = generateKey(RED_KEY_PREFIX, roomCode);
            allUnitPickDtoList = pickRedisRepository.getCurrentUnitPickInfo(key);

            for (CurrentUnitPickDto unitPick : allUnitPickDtoList) {
                if (unitPick.isPick()) {
                    unitPickDtoList.add(unitPick);
                }
            }

            return unitPickDtoList;

        } else if (team.equals("청팀")) {
            key = generateKey(BLUE_KEY_PREFIX, roomCode);
            allUnitPickDtoList = pickRedisRepository.getCurrentUnitPickInfo(key);

            for (CurrentUnitPickDto unitPick : allUnitPickDtoList) {
                if (unitPick.isPick()) {
                    unitPickDtoList.add(unitPick);
                }
            }

            return unitPickDtoList;

        } else {
            throw new TeamNotFoundException("존재하지 않은 팀입니다.");
        }

    }

    // 스파이 정보 저장하기
    public void updateSpyInfo(String roomCode, String team, int unitId) {
        String key = generateKey(SPY_KEY_PREFIX, roomCode);
        pickRedisRepository.saveSpyPickToRedis(key, team, unitId);

    }

    // 스파이 정보가 다 들어왔는지 확인하기
    public int countSpyInfo(String roomCode) {
        String key = generateKey(SPY_KEY_PREFIX, roomCode);
        Map<Object, Object> spyPickInfo = pickRedisRepository.getCurrentSpyPickInfo(key);
        return spyPickInfo.size();
    }

    public Map<Object, Object> getSpyInfo(String roomCode){
        String key = generateKey(SPY_KEY_PREFIX, roomCode);
        Map<Object, Object> spyPickInfo = pickRedisRepository.getCurrentSpyPickInfo(key);
        return spyPickInfo;
    }

    // 픽 창이 시작되면 방 비활성화
    public void disableRoom(String roomCode){
        RoomEntity roomEntity = roomRepository.findByRoomCode(roomCode);
        roomEntity.setEndAt(LocalDateTime.now());
        roomRepository.save(roomEntity);
    }
}
