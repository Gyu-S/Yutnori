package com.ssafy.hungry.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hungry.global.dto.StompDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
// Redis 채널에서 메시지를 수신 받고, 알맞은 구독자들에게 전달.
public class RedisListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    // Redis 채널에서 메시지가 수신될 때마다 자동으로 호출 되는 메서드
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String clientMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

        try {
            StompDataDto stompDataDto = objectMapper.readValue(clientMessage, StompDataDto.class);

          if (stompDataDto.getType().startsWith("ROOM")){
              messagingTemplate.convertAndSend("/sub/room/" + stompDataDto.getCode(), stompDataDto);
          }
          else if(stompDataDto.getType().startsWith("PICK")){
              messagingTemplate.convertAndSend("/sub/room/" + stompDataDto.getCode(), stompDataDto);
          }
          else if(stompDataDto.getType().equals("GAME_START")){
              messagingTemplate.convertAndSend("/sub/room/" + stompDataDto.getCode(), stompDataDto);
          }
          else if(stompDataDto.getType().startsWith("MINIGAME")){
              messagingTemplate.convertAndSend("/sub/game/" + stompDataDto.getCode(), stompDataDto);
          }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
