package com.ssafy.hungry.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("email_auth")
public class EmailEntity {
    @Id
    private String email;
    private String authCode;
    @TimeToLive
    private Long expiration;

    public EmailEntity(String email, String authCode, Long expiration){
        this.email = email;
        this.authCode = authCode;
        this.expiration = expiration;
    }
}
