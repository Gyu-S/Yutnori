package com.ssafy.hungry.global.filter;

import com.ssafy.hungry.domain.user.detail.CustomUserDetails;
import com.ssafy.hungry.global.dto.TokenDTO;
import com.ssafy.hungry.global.util.JWTUtil;
import com.ssafy.hungry.domain.user.entity.TokenEntity;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    private final TokenRepository repository;

    public JWTFilter(JWTUtil jwtUtil, TokenRepository repository) {

        this.jwtUtil = jwtUtil;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 스웨거 경로를 확인하고 필터 적용을 건너뛰기
        if (request.getRequestURI().startsWith("/swagger-ui") ||
                request.getRequestURI().startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 존재하거나 Bearer로 시작했는지 확인.
        // 재발급 요청 X
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("올바르지 못한 토큰");
            filterChain.doFilter(request, response);
            return;
        }

        //requset에서 RefreshToken 헤더를 찾음
        String refreshToken = request.getHeader("RefreshToken");

        // Bearer 부분 제거 후 순수 Access 토큰만 획득
        String accessToken = authorization.split(" ")[1];

        if (refreshToken != null) {
            log.info("RefreshToken 검증");

            if (!jwtUtil.validateToken(refreshToken)) {
                log.info("Refresh 토큰 검증 시도 중 오류 발생, 재발급 필요");
                response.sendError(406, "require login");
            }

            //refreshToken 값을 키로 accessToken을 가져온다. 두 값이 일치하면 새로운 refreshToken 과 accessToken을 발급한다.
            TokenEntity tokenEntity = repository.findById(refreshToken).get();
            if (accessToken.equals(tokenEntity.getAccessToken())) {
                String email = tokenEntity.getEmail();
                String role = tokenEntity.getRole();

                //token 재발급
                TokenDTO tokenDTO = jwtUtil.generateToken(email, role);

                String acToken = tokenDTO.getAccessToken();
                String reToken = tokenDTO.getRefreshToken();

                TokenEntity token1 = new TokenEntity(reToken, acToken, email, role, 12 * 60 * 60);
                repository.save(token1);

                //이전 refreshToken 은 삭제
                repository.deleteById(refreshToken);

                response.addHeader("Authorization", "Bearer " + acToken);
                response.addHeader("RefreshToken", reToken);

                //userEntity를 생성하여 값 set
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(email);
                userEntity.setPassword("temppassword");
                userEntity.setRole(role);

                //UserDetails에 회원 정보 객체 담기
                CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

                //스프링 시큐리티 인증 토큰 생성
                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                //세션에 사용자 등록
                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);
            } else {
                log.info("RefreshToken 불일치, 로그인 다시 필요");
                response.sendError(401, "require login");
                return;
            }
        } else {
            log.info("AccessToken 검증");

            if (jwtUtil.validateToken(accessToken)) {
                //토큰에서 username과 role 획득
                String email = jwtUtil.getUserId(accessToken);
                String role = jwtUtil.getRole(accessToken);

                //userEntity를 생성하여 값 set
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(email);
                userEntity.setPassword("temppassword");
                userEntity.setRole(role);

                //UserDetails에 회원 정보 객체 담기
                CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

                //스프링 시큐리티 인증 토큰 생성
                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                //세션에 사용자 등록
                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);
            } else {
                log.info("token expired");

                response.sendError(406, "token expired");
            }
        }
    }
}