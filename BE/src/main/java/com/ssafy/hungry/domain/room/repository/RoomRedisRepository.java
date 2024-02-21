package com.ssafy.hungry.domain.room.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hungry.domain.room.dto.CurrentSeatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoomRedisRepository {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;


    // redis에 저장하기
    public void saveToRedis(String key, CurrentSeatDto currentSeatDto){
        log.info("RoomRedisRepository saveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentSeatDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, jsonData, currentSeatDto.getSeatNumber());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    // redis에 지우고 다시 저장하기
    public void reSaveToRedis(String key, CurrentSeatDto currentSeatDto, int seatNumber){
        log.info("RoomRedisRepository reSaveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentSeatDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.removeRange(key, seatNumber, seatNumber);
            zSetOps.add(key, jsonData, currentSeatDto.getSeatNumber());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    // Redis에서 Room 정보 가져오기
    public List<CurrentSeatDto> getCurrentRoomInfo(String key){

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<String> list = zSetOps.range(key, 0, -1);
        List<CurrentSeatDto> currentSeatDtoList = null;

        try{
            currentSeatDtoList = objectMapper.readValue(list.toString(), new TypeReference<List<CurrentSeatDto>>(){});
        }catch (Exception e){
            e.printStackTrace();
        }

        return currentSeatDtoList;
    }

    // redis의 방 지우기
    public void deleteToRedis(String key){
        log.info("RoomRedisRepository deleteToRedis 호출 : " + key);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        redisTemplate.delete(key);
    }


}
