package com.ssafy.hungry.domain.pick.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hungry.domain.pick.dto.CurrentUnitPickDto;
import com.ssafy.hungry.domain.pick.dto.CurrentUserPickDto;
import com.ssafy.hungry.domain.pick.dto.SpyPickDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PickRedisRepository {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    // redis에 userPick 저장하기
    public void saveUserPickToRedis(String key, CurrentUserPickDto currentUserPickDto){
        log.info("PickRedisRepository saveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentUserPickDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, jsonData, currentUserPickDto.getSeatNumber());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    // redis에 unitPick 저장하기
    public void saveUnitPickToRedis(String key, CurrentUnitPickDto currentUnitPickDto){
        log.info("PickRedisRepository saveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentUnitPickDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, jsonData, currentUnitPickDto.getUnitId());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    // redis에 우리팀이 픽한 상대팀의 밀정 유닛 아이디를 저장
    public void saveSpyPickToRedis(String key, String team, int unitId){
        redisTemplate.opsForHash().put(key, team, String.valueOf(unitId));
    }

    // userPick을 redis에 지우고 다시 저장하기
    public void reSaveUserPickToRedis(String key, CurrentUserPickDto currentUserPickDto, int seatNumber){
        log.info("PickRedisRepository reSaveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentUserPickDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.removeRange(key, seatNumber, seatNumber);
            zSetOps.add(key, jsonData, currentUserPickDto.getSeatNumber());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    // unitPick을 redis에 지우고 다시 저장하기
    public void reSaveUnitPickToRedis(String key, CurrentUnitPickDto currentUnitPickDto, int index){
        log.info("PickRedisRepository reSaveToRedis 호출 : " + key);

        try {
            String jsonData = objectMapper.writeValueAsString(currentUnitPickDto);
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.removeRange(key, index, index);
            zSetOps.add(key, jsonData, currentUnitPickDto.getUnitId());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    // redis에서  현재 유저의 유닛 선택 정보 가져오기
    public List<CurrentUserPickDto> getCurrentUserPickInfo(String key, int start, int end){

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<String> list = zSetOps.range(key, start, end);
        List<CurrentUserPickDto> currentUserPickDtoList = null;

        try{
            currentUserPickDtoList = objectMapper.readValue(list.toString(), new TypeReference<List<CurrentUserPickDto>>(){});
        }catch (Exception e){
            e.printStackTrace();
        }

        return currentUserPickDtoList;
    }

    // redis에서  현재 유닛 정보 가져오기
    public List<CurrentUnitPickDto> getCurrentUnitPickInfo(String key){

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<String> list = zSetOps.range(key, 0, -1);
        List<CurrentUnitPickDto> CurrentUnitPickDtoList = null;

        try{
            CurrentUnitPickDtoList = objectMapper.readValue(list.toString(), new TypeReference<List<CurrentUnitPickDto>>(){});
        }catch (Exception e){
            e.printStackTrace();
        }

        return CurrentUnitPickDtoList;
    }

    public Map<Object, Object> getCurrentSpyPickInfo(String key){
        Map<Object, Object> spyPickInfo = redisTemplate.opsForHash().entries(key);
        return spyPickInfo;
    }

}
