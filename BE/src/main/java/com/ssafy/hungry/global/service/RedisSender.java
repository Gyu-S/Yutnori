package com.ssafy.hungry.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSender {

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendToRedis(ChannelTopic topic, Object data){

        // 메세지를 redis topic에 발행
        redisTemplate.convertAndSend(topic.getTopic(), data);

    }
}
