package com.ssafy.hungry.global.repository;

import com.ssafy.hungry.global.entity.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<SessionEntity, String> {

}
