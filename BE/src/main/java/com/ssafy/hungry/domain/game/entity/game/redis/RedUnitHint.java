package com.ssafy.hungry.domain.game.entity.game.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("redUnitHint")
public class RedUnitHint {
    @Id
    private String roomCode;

    private String unitHint;

    public RedUnitHint(String roomCode, String unitHint){
        this.roomCode = roomCode;
        this.unitHint = unitHint;
    }
}