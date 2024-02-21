package com.ssafy.hungry.domain.user.service;

import com.ssafy.hungry.domain.friend.service.FriendService;
import com.ssafy.hungry.domain.game.entity.UnitEntity;
import com.ssafy.hungry.domain.game.entity.game.Game;
import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamMember;
import com.ssafy.hungry.domain.game.entity.game.blue.BlueTeamUnit;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamMember;
import com.ssafy.hungry.domain.game.entity.game.red.RedTeamUnit;
import com.ssafy.hungry.domain.game.repository.GameRepository;
import com.ssafy.hungry.domain.game.repository.UnitRepository;
import com.ssafy.hungry.domain.user.detail.CustomUserDetails;
import com.ssafy.hungry.domain.user.dto.*;
import com.ssafy.hungry.domain.user.entity.EmailEntity;
import com.ssafy.hungry.domain.user.entity.UserEntity;
import com.ssafy.hungry.domain.user.entity.UserGameHistoryEntity;
import com.ssafy.hungry.domain.user.repository.EmailRepository;
import com.ssafy.hungry.domain.user.repository.TokenRepository;
import com.ssafy.hungry.domain.user.repository.UserGameHistoryRepository;
import com.ssafy.hungry.domain.user.repository.UserRepository;
import com.ssafy.hungry.global.repository.PrincipalRepository;
import com.ssafy.hungry.global.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService { //회원 관련 서비스를 모아둔 클래스
    //유저 레포지토리 명령어 사용을 위한 선언
    private final UserRepository userRepository;
    //비밀번호를 암호화 하여 저장하기 위한 선언
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailRepository emailRepository;
    private final MailService mailService;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;
    private final TokenRepository tokenRepository;
    private final SessionRepository sessionRepository;
    private final PrincipalRepository principalRepository;
    private final GameRepository gameRepository;
    private final UserGameHistoryRepository userGameHistoryRepository;
    private final UnitRepository unitRepository;
    private final FriendService friendService;

    //회원 가입 메소드
    public Boolean join(JoinDto joinDto) {
        String email = joinDto.getEmail();
        String nickname = joinDto.getNickname();

        //이메일 닉네임 중복 확인
        if (userRepository.existsByEmail(email) && !userRepository.existsByNickname(nickname)) return false;

        //이메일 인증을 받았는지 확인
        if(emailRepository.existsById(AUTH_CODE_PREFIX + email)) return false;

        UserEntity user = new UserEntity();
        user.setEmail(joinDto.getEmail());
        user.setGender(joinDto.getGender());
        user.setBirthDate(joinDto.getBirthDate());
        user.setNickname(joinDto.getNickname());
        user.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return true;
    }

    // 아이디 중복 검사
    public boolean checkId(String email){
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복 검사
    public boolean checkNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    //닉네임 변경
    public void changeNickname(String email, String nickname){
        UserEntity user = userRepository.findByEmail(email);
        user.setNickname(nickname);
        userRepository.save(user);
    }

    // 사용자 정보 변경
    public void modifyProfile(String userId, MyInfoDto dto) {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("userDetailService");
        UserEntity userData = userRepository.findByEmail(email);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }

    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    //회원 탈퇴
    public void deleteUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        user.setDelete(true);
        userRepository.save(user);
    }

    //이메일을 받아서 해당 이메일에 인증 메일 발송
    public void sendCodeToEmail(String toEmail) {
        //제목 세팅
        String title = "밀정 윷놀이 가입 이메일 인증 코드입니다.";
        //인증 코드 세팅
        String authCode = this.createCode(6);
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        EmailEntity entity = new EmailEntity(AUTH_CODE_PREFIX + toEmail, authCode, authCodeExpirationMillis );
        emailRepository.save(entity);
    }

    //인증코드를 만들어주는 메소드
    private String createCode(int lenth) {
        //정의된 길이 만큼 인증코드를 생성
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //임시비밀번호 생성
    public String getRamdomPassword(int size) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    //사용자가 입력한 인증코드가 맞는지 이메일을 키로 레디스에서 검색
    public Boolean verifiedCode(String email, String authCode) {
        try{
            String redisAuthCode = emailRepository.findById(AUTH_CODE_PREFIX + email).get().getAuthCode();
            boolean authResult = redisAuthCode.equals(authCode);
            if (authResult){
                emailRepository.deleteById(AUTH_CODE_PREFIX + email);
            }
            return authResult;
        }catch (NoSuchElementException e) {
            System.out.println("레디스에서 찾을 수 없습민당");
        }
        return false;
    }

    //임시 비밀 번호 발급 이메일 인증 요청
    public void getTemporaryPasswordEmailVerificationRequest(String toEmail) {
        //제목 세팅
        String title = "밀정 윷놀이 임시 비밀 번호 이메일 인증 코드입니다.";
        //인증 코드 세팅
        String authCode = this.createCode(6);
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        EmailEntity entity = new EmailEntity("PASSWORD " + toEmail, authCode, authCodeExpirationMillis );
        emailRepository.save(entity);
    }

    //사용자가 입력한 인증코드가 맞는지 이메일을 키로 레디스에서 검색
    public Boolean getTemporaryPasswordEmailVerificationCode(String email, String authCode) {
        try{
            String redisAuthCode = emailRepository.findById("PASSWORD " + email).get().getAuthCode();
            boolean authResult = redisAuthCode.equals(authCode);
            if (authResult){
                emailRepository.deleteById("PASSWORD " + email);
                this.getTemporaryPassword(email);
            }
            return authResult;
        }catch (NoSuchElementException e) {
            System.out.println("레디스에서 찾을 수 없습민당");
        }
        return false;
    }

    //임시비밀번호로 비밀번호를 바꾸고 해당 비밀번호 이메일 전송
    public void getTemporaryPassword(String toEmail) {
        //제목 세팅
        String title = "밀정 윷놀이 임시 비밀 번호입니다.";
        //임시 비밀번호 세팅
        String tmporaryPassword = this.getRamdomPassword(8);
        //임시 비밀번호 전송
        mailService.sendEmail(toEmail, title, tmporaryPassword);
        //유저 비밀번호를 임시 비밀번호로 변경
        UserEntity user = userRepository.findByEmail(toEmail);
        user.setPassword(bCryptPasswordEncoder.encode(tmporaryPassword));
        userRepository.save(user);
    }

    //비밀번호 변경
    public boolean changePassword(String previousPassword, String nextPassword){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email);

        if(bCryptPasswordEncoder.matches(previousPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(nextPassword));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    //로그아웃
    public void logout(String email, String refreshToken){
        tokenRepository.deleteById(refreshToken);
        String uuid = sessionRepository.findById(email).get().getStompPrincipal().getName();
        sessionRepository.deleteById(email);
        principalRepository.deleteById(uuid);
    }

    //전적검색
    public List<GameHistoryDto> myGameHistory(String email){
        UserEntity user = userRepository.findByEmail(email);
        List<GameHistoryDto> dtoList = new ArrayList<>();
        List<UserGameHistoryEntity> gameList = userGameHistoryRepository.findByUserId(user.getId());
        for(UserGameHistoryEntity gameHistoryEntity : gameList){
            Game game = gameHistoryEntity.getGameCode();
            GameHistoryDto gameHistoryDto = new GameHistoryDto();
            gameHistoryDto.setGameTheme(game.getGameTheme());
            gameHistoryDto.setGameSpeed(game.getGameSpeed());
            gameHistoryDto.setBlueTeamReasoningResult(game.isBlueTeamReasoningResult());
            gameHistoryDto.setRedTeamReasoningResult(game.isRedTeamReasoningResult());
            gameHistoryDto.setMissionRegion(game.getMissionRegion());
            gameHistoryDto.setBlueSpyHint(game.getBlueSpyHint());
            gameHistoryDto.setRedSpyHint(game.getRedSpyHint());
//            gameHistoryDto.setCreatedDate(game.getCreatedDate());
//            gameHistoryDto.setEndDate(game.getEndDate());
            UnitEntity unit = unitRepository.findById(game.getBlueSpyId());
            gameHistoryDto.setBlueSpyName(unit.getName());
            unit = unitRepository.findById(game.getRedSpyId());
            gameHistoryDto.setRedSpyName(unit.getName());
            if(game.getWinner() == 1){
                gameHistoryDto.setWinner("청팀");
            }else{
                gameHistoryDto.setWinner("홍팀");
            }

            for(BlueTeamMember blueTeamMember : game.getBlueTeamMemberList()){
                BlueTeamMemberDto memberDto = new BlueTeamMemberDto();
                memberDto.setNickname(blueTeamMember.getUserId().getNickname());
                gameHistoryDto.getBlueTeamMemberDtoList().add(memberDto);
            }

            for(RedTeamMember redTeamMember : game.getRedteamMemberList()){
                RedTeamMemberDto memberDto = new RedTeamMemberDto();
                memberDto.setNickname(redTeamMember.getUserId().getNickname());
                gameHistoryDto.getRedTeamMemberDtoList().add(memberDto);
            }

            for(BlueTeamUnit blueTeamUnit : game.getBlueTeamUnitList()){
                BlueTeamUnitDto unitDto = new BlueTeamUnitDto();
                unitDto.setUnitName(blueTeamUnit.getUnitId().getName());
                unitDto.setSkill(blueTeamUnit.getUnitId().getSkill());
                unitDto.setPlace(blueTeamUnit.getPlace());
                unitDto.setTime(blueTeamUnit.getTime());
                unitDto.setContactor(blueTeamUnit.getContactor());
                unitDto.setStuff(blueTeamUnit.getStuff());
                unitDto.setScal(blueTeamUnit.getScal());
                gameHistoryDto.getBlueTeamUnitDtoList().add(unitDto);
            }

            for(RedTeamUnit redTeamUnit : game.getRedTeamUnitList()){
                RedTeamUnitDto unitDto = new RedTeamUnitDto();
                unitDto.setUnitName(redTeamUnit.getUnitId().getName());
                unitDto.setSkill(redTeamUnit.getUnitId().getSkill());
                unitDto.setPlace(redTeamUnit.getPlace());
                unitDto.setTime(redTeamUnit.getTime());
                unitDto.setContactor(redTeamUnit.getContactor());
                unitDto.setStuff(redTeamUnit.getStuff());
                unitDto.setScal(redTeamUnit.getScal());
                gameHistoryDto.getRedTeamUnitDtoList().add(unitDto);
            }

            dtoList.add(gameHistoryDto);
        }
        return dtoList;
    }

    //유저검색
    public List<UserDto> userSearch(String nickname, String email) {
        //내 아이디 값 가져오기
        int myId = userRepository.findByEmail(email).getId();
        //리턴 보낼 리스트 생성
        List<UserDto> userDtoList = new ArrayList<>();
        //닉네임으로 유저 검색
        List<UserEntity> userEntityList = userRepository.findByNicknameContains(nickname);
        for(UserEntity userEntity : userEntityList){
            //이미 친구라면 리스트에 포함하지 않음
            if(friendService.areWeFriend(myId, userEntity.getId())){
                continue;
            //친구 목록이므로 나 자신도 포함하지 않음
            }else if(myId == userEntity.getId()){
                continue;
            } else {
                UserDto dto = new UserDto();
                dto.setEmail(userEntity.getEmail());
                dto.setNickname(userEntity.getNickname());
                userDtoList.add(dto);
            }
        }
        return userDtoList;
    }
}