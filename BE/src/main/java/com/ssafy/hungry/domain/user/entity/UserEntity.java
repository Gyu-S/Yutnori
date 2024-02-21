package com.ssafy.hungry.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false,length=30, unique = true)
    private String email;

    @Column(nullable=false,length=10, unique = true)
    private String nickname;

    @Column(nullable=false,length=60)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(nullable=false,length=1)
    private String gender;

    @Column(nullable=true,length=255)
    private String profileImgUrl;

//    @Column(nullable=true,length=255)
//    private String refreshToken;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date createdDate;

    @Column(nullable=false)
    private boolean isDelete;

    @Column(nullable=false,length=30)
    private String role;

//    public void updateRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
}
