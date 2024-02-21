package com.ssafy.hungry.domain.event.controller;

import com.ssafy.hungry.domain.event.dto.EventDto;
import com.ssafy.hungry.domain.friend.dto.MyFriendDto;
import com.ssafy.hungry.domain.friend.service.FriendService;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.global.entity.StompPrincipal;
import com.ssafy.hungry.global.repository.PrincipalRepository;
import com.ssafy.hungry.global.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionRepository sessionRepository;
    private final PrincipalRepository principalRepository;
    private final FriendService friendService;

    @MessageMapping("/event")
    public void event(EventDto dto){
        String toUserEmail = dto.getToUserEmail();
        System.out.println(dto);
        StompPrincipal principal = new StompPrincipal(sessionRepository.findById(toUserEmail).get().getStompPrincipal().getName());
        messagingTemplate.convertAndSendToUser(principal.getName(), "sub/event", dto);
    }

    @MessageMapping("/login")
    public void login(Principal uuid, EventDto dto){
        //principal 정보로 redis에서 이메일 정보 가져오기
        String email = principalRepository.findById(uuid.getName()).get().getEmail();

        //이메일 정보로 친구목록 불러오기
        List<MyFriendDto> myFriendDtoList = friendService.myFriend(email);

        //친구 목록에 있는 유저에게 로그인 이벤트 전송
        for(MyFriendDto myFriendDto : myFriendDtoList){
            StompPrincipal principal = new StompPrincipal(sessionRepository.findById(myFriendDto.getEmail()).get().getStompPrincipal().getName());
            messagingTemplate.convertAndSendToUser(principal.getName(), "sub/event", dto);
        }
    }

    @MessageMapping("/logout")
    public void logout(Principal uuid, EventDto dto){
        //principal 정보로 redis에서 이메일 정보 가져오기
        String email = principalRepository.findById(uuid.getName()).get().getEmail();

        //이메일 정보로 친구목록 불러오기
        List<MyFriendDto> myFriendDtoList = friendService.myFriend(email);

        //친구 목록에 있는 유저에게 로그인 이벤트 전송
        for(MyFriendDto myFriendDto : myFriendDtoList){
            StompPrincipal principal = new StompPrincipal(sessionRepository.findById(myFriendDto.getEmail()).get().getStompPrincipal().getName());
            messagingTemplate.convertAndSendToUser(principal.getName(), "sub/event", dto);
        }
    }
}
