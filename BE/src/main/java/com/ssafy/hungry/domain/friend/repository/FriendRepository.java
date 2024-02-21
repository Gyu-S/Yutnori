package com.ssafy.hungry.domain.friend.repository;

import com.ssafy.hungry.domain.friend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    List<FriendEntity> findByFromUserId(int fromUserId);

    Boolean existsByFromUserIdAndToUserId(int fromUserId, int toUserId);

    Boolean existsByFromUserIdAndToUserIdAndWeAreFriendTrue(int fromUserId, int toUserId);

    FriendEntity findByFromUserIdAndToUserId(int fromUserId, int toUserId);

    List<FriendEntity> findByFromUserIdAndWeAreFriendFalse(int FromUserId);

    List<FriendEntity> findByToUserIdAndWeAreFriendFalse(int toUserId);
}