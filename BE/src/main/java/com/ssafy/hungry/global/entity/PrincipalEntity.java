package com.ssafy.hungry.global.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("principal")
public class PrincipalEntity {
    @Id
    private String name;

    private String email;

    public PrincipalEntity(String name ,String email){
        this.name = name;
        this.email = email;
    }

}
