package com.ssafy.hungry.global.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.security.Principal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("session")
public class SessionEntity {
    @Id
    private String email;

    private StompPrincipal stompPrincipal;

    public SessionEntity(String email, StompPrincipal stompPrincipal){
        this.email = email;
        this.stompPrincipal = stompPrincipal;
    }
}

