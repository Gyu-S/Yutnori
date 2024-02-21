package com.ssafy.hungry.domain.friend.service;

import com.ssafy.hungry.domain.friend.dto.MyFriendDto;
import com.ssafy.hungry.domain.friend.dto.ReceiveRequestFriendDto;
import com.ssafy.hungry.domain.friend.dto.SendRequestFriendDto;
import com.ssafy.hungry.domain.friend.entity.FriendEntity;
import com.ssafy.hungry.domain.friend.repository.FriendRepository;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.global.entity.StompPrincipal;
import com.ssafy.hungry.global.repository.SessionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final EntityManager em;
    private final SessionRepository sessionRepository;


    //친구 목록은 { fromUserId, toUserId, weAreFriend } 세개의 속성으로 구성되어 있음
    //요청을 보낼 때 { 나의Id, 상대방Id, true } 와 { 상대방Id, 나의Id, false } 값으로 저장
    //내가 보낸 요청 목록은 toUserId 컬럼에 나의Id, weAreFriend 컬럼에 false 가 되어 있는 로우를 검색
    //반대로 내가 받은 요청 목록은 fromUserId 컬럼에 나의Id, weAreFriend 컬럼에 false 가 되어 있는 로우를 검색
    //요청 수락은 내가 받은 목록 중 toUserId의 값이 상대의Id인 값을 검색하고 해당 로우의 weAreFriend 값을 true로 변경
    //내 친구 목록은 내가 보낸 요청과 상대가 받은 요청이 모두 True로 세팅 되어 있는 컬럼을 찾고 중복제거 하여 검색

    //내 친구 목록 보기 서비스
    public List<MyFriendDto> myFriend(String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //현재 이용자 기준 내가 보낸 요청과 상대가 받은 요청이 모두 True로 세팅 되어 있는 컬럼을 찾고 중복제거 하여 검색
        String jpql = "select distinct f.toUserId from FriendEntity as f join FriendEntity as sf on f.toUserId = sf.fromUserId" +
                " where f.fromUserId = : myId and f.weAreFriend = true and sf.toUserId = : myId and sf.weAreFriend = true";

        //쿼리 실행. 반환값은 List<Integer>형이다.
        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        //쿼리에 파라미터 바인딩
        query.setParameter("myId", myId);
        List<Integer> list = query.getResultList();

        //Controller의 반환형을 맞추기 위한 로직
        List<MyFriendDto> myFriendDtoList = new ArrayList<>();
        //반환 받은 친구의 id 값을 기준으로 유저에서 검색하여 값을 반환
        for(int i = 0; i < list.size(); i++){
            int id = list.get(i);
            UserEntity entity = userRepository.findById(id);
            MyFriendDto dto = new MyFriendDto();
            dto.setEmail(entity.getEmail());
            dto.setNickname(entity.getNickname());
            myFriendDtoList.add(dto);
        }
        myFriendDtoList = this.isOnline(myFriendDtoList);
        return myFriendDtoList;
    }

    //친구 목록에서 친구 온라인인지 추가 판별
    public List<MyFriendDto> isOnline(List<MyFriendDto> myFriendDtoList){
        for(MyFriendDto myFriendDto : myFriendDtoList){
            myFriendDto.getEmail();
            if(sessionRepository.existsById(myFriendDto.getEmail())){
                myFriendDto.setOnline(true);
            }
        }
        return myFriendDtoList;
    }

    //상대와 친구인지 확인하는 메소드
    public boolean areWeFriend(int myId, int targetId){
        //상대와 내가 모두 친구 여부에 true로 등록되어있는지 확인
        return friendRepository.existsByFromUserIdAndToUserIdAndWeAreFriendTrue(myId, targetId) && friendRepository.existsByFromUserIdAndToUserIdAndWeAreFriendTrue(targetId, myId);
    }

    //내가 보낸 요청 목록을 위한 서비스
    public List<SendRequestFriendDto> mySendRequest(String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //나의 아이디가 toUserId에 있으면서 weAreFriend 가 False인 값들을 가져온다.
        List<FriendEntity> list = friendRepository.findByToUserIdAndWeAreFriendFalse(myId);

        //리턴 타입으로 변경하기 위한 로직
        List<SendRequestFriendDto> sendRequestFriendDtoList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            int id = list.get(i).getFromUserId();
            UserEntity entity = userRepository.findById(id);
            SendRequestFriendDto dto = new SendRequestFriendDto();
            dto.setToUserEmail(entity.getEmail());
            dto.setToUserNickname(entity.getNickname());
            sendRequestFriendDtoList.add(dto);
        }
        return sendRequestFriendDtoList;
    }

    //내가 받은 요청 목록 저비스
    public List<ReceiveRequestFriendDto> myReceiveRequest(String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //나의 아이디가 fromUserId에 있으면서 weAreFriend 가 False인 값들을 가져온다.
        List<FriendEntity> list = friendRepository.findByFromUserIdAndWeAreFriendFalse(myId);

        //리턴 타입으로 변경을 위한 로직
        List<ReceiveRequestFriendDto> receiveRequestFriendDtoList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            int id = list.get(i).getToUserId();
            UserEntity entity = userRepository.findById(id);
            ReceiveRequestFriendDto dto = new ReceiveRequestFriendDto();
            dto.setFromUserEmail(entity.getEmail());
            dto.setFromUserNickname(entity.getNickname());
            receiveRequestFriendDtoList.add(dto);
        }
        return receiveRequestFriendDtoList;
    }

    //친구 요청을 위한 로직
    public String sendRequestToUser(SendRequestFriendDto dto, String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //친구 요청을 보낼 아이디 가져오기
        int toUserId = userRepository.findByEmail(dto.getToUserEmail()).getId();

        //이미 친구 요청 목록에 있다면
        if(friendRepository.existsByFromUserIdAndToUserId(myId, toUserId)){
            return "이미 처리된 요청";
        }

        //내가 친구에게 요청을 보냄 true
        FriendEntity entity1 = new FriendEntity(myId, toUserId, true);

        //친구가 내 요청을 수락하였는가 false
        FriendEntity entity2 = new FriendEntity(toUserId, myId, false);

        friendRepository.save(entity1);
        friendRepository.save(entity2);

        return "친구 요청 완료";
    }

    //요청 수락을 위한 서비스
    public String acceptRequestFromUser(ReceiveRequestFriendDto dto, String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //친구 수락 대상의 아이디 가져오기
        int fromUserId = userRepository.findByEmail(dto.getFromUserEmail()).getId();

        //해당 요첨 컬럼 가져오기
        FriendEntity entity = friendRepository.findByFromUserIdAndToUserId(myId, fromUserId);

        //이미 친구라면 처리된 요청 반환
        if(entity.isWeAreFriend()){
            return "이미 처리된 요청";
        }

        //해당 요청을 true로 수정함
        entity.setWeAreFriend(true);
        friendRepository.save(entity);

        return "요청 수락 완료";
    }

    //요정 거절을 위한 서비스
    public String reject(ReceiveRequestFriendDto dto, String email){
        //현재 요청자의 아이디 가져오기
        int myId = userRepository.findByEmail(email).getId();

        //친구 수락 대상의 아이디 가져오기
        int fromUserId = userRepository.findByEmail(dto.getFromUserEmail()).getId();

        //해당 요첨 컬럼 가져오기
        FriendEntity entity = friendRepository.findByFromUserIdAndToUserId(myId, fromUserId);
        //해당 요첨 컬럼 가져오기
        FriendEntity entity2 = friendRepository.findByFromUserIdAndToUserId(fromUserId, myId);

        //해당 컬럼 삭제
        friendRepository.delete(entity);
        friendRepository.delete(entity2);

        return "요청 거절 성공";
    }
}
