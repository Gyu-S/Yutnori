package com.ssafy.hungry.domain.room.entity;

import com.ssafy.hungry.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @Column
    private String roomCode;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private int gameSpeed;

    @Column(nullable = false)
    private boolean isPublic;

    @Column
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;


}
