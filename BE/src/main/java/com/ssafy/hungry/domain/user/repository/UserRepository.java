package com.ssafy.hungry.domain.user.repository;

import com.ssafy.hungry.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    UserEntity findByEmail(String email);

    void deleteByEmail(String email);

    UserEntity findById(int id);

    List<UserEntity> findByNicknameContains(String nickname);
}
