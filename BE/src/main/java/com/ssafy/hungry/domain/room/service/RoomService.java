package com.ssafy.hungry.domain.room.service;

import com.ssafy.hungry.domain.room.dto.CreateRoomDto;
import com.ssafy.hungry.domain.room.dto.RoomDetailDto;
import com.ssafy.hungry.domain.room.dto.RoomDto;
import com.ssafy.hungry.domain.room.entity.RoomEntity;
import com.ssafy.hungry.domain.room.repository.RoomRedisRepository;
import com.ssafy.hungry.domain.room.repository.RoomRepository;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {


    private final UserService userService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomRedisRepository roomRedisRepository;
    private final RoomRedisService roomRedisService;
    // 방 비밀번호를 암호화 시키기 위한 객체
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 방 전체 목록 검색
    public List<RoomDto> getRoomList() {
        log.info("RoomService getRoomList 호출");

        List<RoomEntity> roomEntityList = roomRepository.findAllByEndAtIsNullOrderByStartAt();
        List<RoomDto> roomDtoList = new ArrayList<>();

        for(RoomEntity roomEntity : roomEntityList){
            roomDtoList.add(RoomDto.builder()
                            .roomId(roomEntity.getRoomId())
                            .currentUserCount(roomRedisService.getCurrentUserCount(roomEntity.getRoomCode()))
                            .title(roomEntity.getTitle())
                            .isPublic(roomEntity.isPublic())
                            .build());
        }

        return roomDtoList;
    }

    // 방 세부 정보 조회
    public RoomDetailDto getRoomDetail(int roomId){
        RoomEntity roomEntity = roomRepository.findByEndAtIsNullAndRoomId(roomId);
        RoomDetailDto roomDetail = RoomDetailDto.builder()
                .title(roomEntity.getTitle())
                .isPublic(roomEntity.isPublic())
                .gameSpeed(roomEntity.getGameSpeed())
                .currentUserCount(roomRedisService.getCurrentUserCount(roomEntity.getRoomCode()))
                .theme(roomEntity.getTheme())
                .build();

        return roomDetail;
    }

    // 방 생성
    public String createRoom(CreateRoomDto createRoomDto, String email){
        log.info("RoomService createRoom 호출 : " + createRoomDto);

        String roomCode = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);

        // 이메일을 이용해 유저 정보 가져오기
        UserEntity user = userRepository.findByEmail(email);

        RoomEntity roomEntity = RoomEntity.builder()
                .roomCode(roomCode)
                .owner(user)
                .title(createRoomDto.getTitle())
                .theme(createRoomDto.getTheme())
                .gameSpeed(createRoomDto.getSpeed())
                .isPublic(createRoomDto.isPublic())
                .build();

        // room이 비공개라면 password를 암호화 시키기
        if(!createRoomDto.isPublic() && createRoomDto.getPassword() != null){
            roomEntity.setPassword(bCryptPasswordEncoder.encode(createRoomDto.getPassword()));
        }

        // rooms 테이블 저장
        roomRepository.save(roomEntity);

        // redis에 만들어진 방에 현재 좌석 정보를 만들기
        roomRedisService.createCurrentSeat(roomCode);

        // 만들어진 room code 반환
        return roomCode;
    }

    // 종료되지 않은 방인지 확인
    public RoomEntity getRoomByRoomId (int roomId){
        log.info("isActiveRoomExists 호출 / roomId : " + roomId);
        RoomEntity room = roomRepository.findByEndAtIsNullAndRoomId(roomId);
        return room;
    }

    // 비공개 방 비밀번호 검증
    public boolean validatePassword(String password, String encodedPassword){
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

    // room code로 방 찾기
    public RoomEntity getRoomByRoomCode(String roomCode){
        return roomRepository.findByRoomCode(roomCode);
    }
}
