package com.ssafy.hungry.domain.game.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hungry.domain.game.dto.UserInfo;
import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GameRedisRepository {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveUserEnterInfo(String key, UserEntity user){

        // 현재 유저 정보 저장하기
        UserInfo userInfo = UserInfo.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForHash().put(key, String.valueOf(user.getId()), jsonData);
    }

    public Map<Object, Object> getCurrentUserInfo(String key){
        return redisTemplate.opsForHash().entries(key);
    }

}
