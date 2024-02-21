package com.ssafy.hungry.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hungry.domain.user.dto.LoginDto;
import com.ssafy.hungry.domain.user.detail.CustomUserDetails;
import com.ssafy.hungry.domain.user.entity.TokenEntity;
import com.ssafy.hungry.domain.user.repository.TokenRepository;
import com.ssafy.hungry.global.dto.TokenDTO;
import com.ssafy.hungry.global.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final TokenRepository repository;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, TokenRepository repository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.repository = repository;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("LoginFilter.attemptAuthentication");

        UsernamePasswordAuthenticationToken authToken;

        //json형태로 request 받는다면
        if (request.getContentType().equals("application/json")) {
            try {
                //클라이언트의 요청이 DispatcherServlet에 도달하기 전에 가로챔.
                //가로챈 request에서 json을 추출 후 objectMapper를 이용해 loginDto 객체로 변환
                LoginDto dto = mapper.readValue(
                        request.getReader().lines().collect(Collectors.joining()), LoginDto.class);

                //Spring Security에서 AuthenticationManager에게 인증을 받으려면 Dto처럼 userId와 password를 Token에 담아서 보내줘야함.
                authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null); // 3번째 인자로 role 값을 넣는다.
            } catch (IOException e) {
                e.printStackTrace();
                throw new AuthenticationServiceException("Request Content-Type(application/json)");
            }
        }
        //json형태가 아닌 form 요청이라면
        else {
            //클라이언트의 요청이 DispatcherServlet에 도달하기 전에 가로채서 userId와 password를 추출.
            String email = obtainUsername(request);
            String password = obtainPassword(request);
            authToken = new UsernamePasswordAuthenticationToken(email, password, null); // 3번째 인자로 role 값을 넣는다.
        }

        //Token을 검증하기 위해 AuthenticationManager에게 전달.
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        TokenDTO tokenDTO = jwtUtil.generateToken(email, role);
        TokenEntity token = new TokenEntity(tokenDTO.getRefreshToken(), tokenDTO.getAccessToken(), email, role, 12*60*60);

        repository.save(token);

        // JSON으로 token 전달
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("access-token", tokenDTO.getAccessToken());
        resultMap.put("refresh-token", tokenDTO.getRefreshToken());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(resultMap));
        response.getWriter().flush();
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("LoginFilter.unsuccessfulAuthentication");

        //로그인 실패시 401 응답 코드 반환
        Map<String, String> resultMap = new HashMap<>();
        response.setStatus(403);
    }
}