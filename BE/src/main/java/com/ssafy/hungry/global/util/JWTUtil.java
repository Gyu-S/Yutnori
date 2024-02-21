package com.ssafy.hungry.global.util;

import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.global.dto.TokenDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JWTUtil {

    private SecretKey secretKey;

    private final UserRepository userRepository;

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 15 * 60 * 1000L; // 15분
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 12 * 60 * 60 * 1000L; // 12시간

    public JWTUtil(@Value("${spring.jwt.secret}")String secret, UserRepository userRepository){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userRepository = userRepository;
    }
    public TokenDTO generateToken(String userId, String role) {

        // Access Token 생성
        String accessToken = Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
//        .claim("userId", userId)
//        .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS))
                .signWith(secretKey)
                .compact();

        return new TokenDTO(accessToken, refreshToken);
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("userId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public UserEntity getUserEntity(String token) {
        token = token.split(" ")[1];
        String email = getUserId(token);
        return userRepository.findByEmail(email);
    }

    /**
     * 토큰 유효성 체크
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        System.out.println("JWTUtil.validateToken");

        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
            e.printStackTrace();
        }

        return false;
    }
}
