package com.ssafy.hungry.domain.user.repository;

import com.ssafy.hungry.domain.user.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailEntity, String> {
    boolean checkExistsValue(String redisAuthCode);
}
