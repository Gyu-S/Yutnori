package com.ssafy.hungry.global.handler;

import com.ssafy.hungry.domain.event.dto.EventDto;
import com.ssafy.hungry.domain.friend.dto.MyFriendDto;
import com.ssafy.hungry.domain.friend.service.FriendService;
import com.ssafy.hungry.domain.user.service.UserService;
import com.ssafy.hungry.global.entity.PrincipalEntity;
import com.ssafy.hungry.global.entity.SessionEntity;
import com.ssafy.hungry.global.entity.StompPrincipal;
import com.ssafy.hungry.global.repository.PrincipalRepository;
import com.ssafy.hungry.global.repository.SessionRepository;
import com.ssafy.hungry.global.util.JWTUtil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {
    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final PrincipalRepository principalRepository;
    private final FriendService friendService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //구독할 때 토큰 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 헤더 토큰 얻기
            String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
            if (authorizationHeader == null || !authorizationHeader.startsWith("[Bearer ")) {

                log.info("소켓 통신 토큰 없음");
                throw new MessageDeliveryException("메세지 예외");
            }

            String token = authorizationHeader.split(" ")[1];
            // 토큰 인증

            try {
                if (jwtUtil.validateToken(token)) {
                    log.info("소켓통신에서 토큰 검증");
                    String email = jwtUtil.getUserId(token);
                    StompPrincipal user = new StompPrincipal(accessor.getUser().getName());
                    sessionRepository.save(new SessionEntity(email, user));
                    principalRepository.save(new PrincipalEntity(user.getName(), email));
                } else {
                    throw new AccessDeniedException("토큰이 유효하지 않습니다.");
                }

            }catch (MessageDeliveryException e){
                throw new MessageDeliveryException("메세지 에러");
            } catch (MalformedJwtException e) {
                throw new MessageDeliveryException("예외3");
            }

        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            log.info("SUBSCRIBE 감지");
        } else if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
            log.info("UNSUBSCRIBE 감지");
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info("DISCONNECT 감지");
            //여기서 끊어지면 로그아웃 하는거처럼 하고 싶은데..
            String email = principalRepository.findById(accessor.getUser().getName()).get().getEmail();
            sessionRepository.deleteById(email);
            principalRepository.deleteById(accessor.getUser().getName());
        } else if (StompCommand.SEND.equals(accessor.getCommand())) {
            log.info("SEND 감지");
        }

        return message;
    }
}

