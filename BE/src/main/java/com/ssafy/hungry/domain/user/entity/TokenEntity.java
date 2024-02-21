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
@RedisHash("token")
public class TokenEntity {

    @Id
    private String refreshToken;

    private String accessToken;
    private String email;
    private String role;

    @TimeToLive
    private Long expiration;

    public TokenEntity(String refreshToken, String accessToken, String email, String role,long l) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.email = email;
        this.role = role;
        this.expiration = l;
    }
}
