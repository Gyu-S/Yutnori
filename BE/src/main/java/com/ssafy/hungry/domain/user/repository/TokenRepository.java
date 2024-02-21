package com.ssafy.hungry.domain.user.repository;

import com.ssafy.hungry.domain.user.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenEntity, String> {

}
