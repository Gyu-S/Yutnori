package com.ssafy.hungry.domain.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "friends")
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fromUserId;

    private int toUserId;

    private boolean weAreFriend;

    public FriendEntity(int fromUserId, int toUserId, boolean weAreFriend){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.weAreFriend = weAreFriend;
    }
}
