<template>
  <div class="modal">
    <div id="work-npc-main">
      <img
        class="work-npc-avatar"
        src="../../assets/img/home/test06.png"
        alt="이미지"
      />
      <div class="work-npc-box" @click="showNextMessage">
        <div class="work-npc-name">도움말</div>
        <img src="@/assets/img/help/cancel.png" alt="cancel-button" class="cancel-button" @click="closeModal('help')">
        <div class="work-npc-content" v-html="npcMessage"></div>
        
        <button @click="showNextIntroMessage" v-if="step === 0" class="modal-yes-btn">응, 알려줘</button>
        <div class="set-content">
          <!-- <img src="@/assets/img/help/mininextvector.png" alt="next-vector" class="next-vector"> -->
        </div>
        <div class="multi-button">
          <section class="work-npc-buttons">
            <button v-if="step >= 1" @click="showCheckScore()" class="modal-exp-btn" :class="{ changedColor: isClikedScore }"># 전적 조회</button>
            <button v-if="step >= 1" @click="showCreateGameroom()" class="modal-exp-btn" :class="{ changedColor: isClikedGameRoom }"># 게임방 생성</button>
            <button v-if="step >= 1" @click="showSelectCharacter()" class="modal-exp-btn" :class="{ changedColor: isClikedCharacter }"># 말선택</button>
            <button v-if="step >= 1" @click="showSelectSpy()" class="modal-exp-btn" :class="{ changedColor: isClikedSpy }"># 밀정 선택</button>
            <button v-if="step >= 1" @click="showDoMission()" class="modal-exp-btn" :class="{ changedColor: isClikedMisson }"># 미션 수행</button>
            <button v-if="step >= 1" @click="showManageCam()" class="modal-exp-btn" :class="{ changedColor: isClikedCam }"># 캠 관리</button>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      npcMessage: "",
      messageFinised: true,
      messageDelay: [],
      selectedMessages: [],
      selectedIndex: 0,
      isClikedScore: false,
      isClikedGameRoom: false,
      isClikedCharacter: false,
      isClikedSpy: false,
      isClikedMisson: false,
      isClikedCam: false,
      img : '@/assets/img/help/vector.gif',
    

      // 시작
      mainMessages: [
        "게임에 대해서 설명해줄까?",
        "너가 궁금한 점을 클릭하면 설명해줄게.",
      ],
      step: 0,

      //전적 조회
      checkScoreMessages: [
        "",
        '마이페이지의 전적 조회를 누르면<br> 너의 최근 10개의 전적을 확인할 수 있어. 👉',
        '전적을 클릭하면<br> 승패 여부, 미션 성공률, 말을 잡은 수 등<br> 상세 정보도 확인할 수 있어. 👉' ,
        '다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와.'
      ],
      currentCheckScoreIndex: 0,

      // 게임방 생성
      createGameroomMessages: [
        "",
        '게임방 생성은 방장만이 할 수 있어.<br> 방장이 게임을 시작하면<br> 다른 플레이어들이 참여할 수 있어. 👉',
        '그리고 게임방은<br> 공개와 비공개 방으로 만들 수 있어. 👉',
        '만약 게임을 하던 도중 나가더라도<br> 재참여가 가능해. 👉',
        '다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와. '
      ],
      currentCreateGameroomIndex: 0,

      // 말선택
      selectCharacterMessages: [
        "",
        "게임을 시작할 때,<br> 너는 자신의 팀을 대표하는 말을 선택해야 해.<br> 각 말에는 특별한 능력이 있어. 👉",
        "예를 들어 노비말은<br> 윷을 던져 나온 칸 수에서 한 칸 뒤로 이동해야 해.<br> 그러니 신중하게 말을 잘 골라봐. 👉",
        "다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와. 👉"
      ],
      currentSelectCharacterIndex: 0,

      // 밀정 선택
      selectSpyMessages: [
        "",
        "밀정은 게임에서 중요한 역할을 하는데,<br> 너의 팀과 상대 팀이<br> 각자 밀정을 선택해 게임을 시작해야 해. 👉",
        "밀정 말은 다섯번 째부터 추리할 수 있어.<br> 그동안 스무고개로<br> 최대한 밀정을 알아내는게 좋겠지? 👉",
        "그리고 만약 너가 밀정 추리를 성공한다면<br> 너희 팀의 밀정말이 상대 팀의 말이 돼. 👉",
        "최종적으로 밀정 말을 제외한<br> 나머지 말을 모두 통과시키면 승리하는거야. 👉",
        "다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와. 👉"
      ],
      currentSelectSpyIndex: 0,

      // 미션 수행
      doMissionMessages: [
        "",
        "이 게임에서 스무고개는<br> 매번 질문할 수 있는게 아니야. 👉",
        "윷놀이 판의 미션 칸에 도착했을 때<br> 미션을 성공적으로 수행해야 질문을 할 수 있어. 👉",
        "미션은 총 8개로 구성되어 있고,<br> 랜덤으로 수행 시 성공하면<br> 너의 팀에게 유리한 상황을 만들 수 있어. 👉",
        "미션에 대한 설명을 더 듣고 싶다면<br> 다음 버튼을 눌러줘. 👉",
        "'야바위'는<br> 상대방의 꺼진 화면을 무작위로 돌려서<br> 특정 상대를 찾는 미션이야. 👉",
        "'광물 캐기'는<br> 너가 직접 광석 캐는 모션을 취해서<br> 기준치 이상으로 광물을 캐내는 미션이야. 👉",
        "'눈싸움'은<br> 너의 팀 한 명과 상대 팀 한 명이<br> 캠으로 눈싸움을 해서 이기면 돼. 👉",
        "'계란 받기'는<br> 화면 위에서 떨어지는 계란을<br> 일정 개수 이상 바구니로 받는 미션이야. 👉",
        "'파리 잡기'는<br> 화면에 날아다니는 파리를<br> 손 동작으로 정해진 마리 수 만큼 잡는 미션이야. 👉",
        "'가위 바위 보'는<br> 너의 팀 한 명과 상대 팀 한명이<br> 대표로 가위바위보 게임을 해서 이기면 돼. 👉",
        "'참참참'은<br> 랜덤으로 화면에 방향이 부여될거야.<br> 그때, 상대 팀 모션이 그 방향과 불일치하면<br> 너의 팀이 이기는거야. 👉",
        "'고요 속의 외침'은 신서유기에서 봤지?<br> 제시어를 보고 입모양으로 나타내면<br> 정답을 맞추는 미션이야. 👉",
        "다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와. 👉"
      ],
      currentDoMissionIndex: 0,

      // 캠 관리
      manageCamMessages: [
        "",
        "게임 중에는 팀끼리 소통이 중요해.<br> 캠을 켜서 다른 플레이어들과 소통하면<br> 게임이 더 재미있어 질거야. 👉",
        "너는 캠을 끄거나 킬 수 있고,<br> 너를 나타낼 이미지로 대체할 수도 있어. 👉",
        "다만 게임이 시작되었을 때는 캠을 꼭 켜야해. 👉",
        "다른 정보가 궁금하다면 다른 태그를 눌러보고,<br> 궁금한게 없다면 다음에 궁금할 때 또 와. 👉"
      ],
      currentManageCamIndex: 0,
    };
  },

  methods: {
    // 모달창 닫기
    closeModal(modalId) {
      this.$emit("close-modal", modalId);
    },

    // setTimeout
    Setmessage(messages) {
      this.clearMessageDelay();
      this.npcMessage = "";
      this.messageFinised = false

      let i = 0;
      const delay = messages.length >= 20 ? 30 : 45;
      
      const processNextMessage = () => {
        
        if (i < messages.length && !this.messageFinised) {
          if (i+4 < messages.length) {
            const word = messages.slice(i, i + 4)
            if (word == "<img") {
              this.showNextMessage()
              return
            }
          }
          // const isImageTag = messages[i].includes("<img");

          // if (isImageTag) {
          //   this.showNextMessage()
          //   return
          // }
          this.npcMessage += messages[i];
          i++;
          setTimeout(processNextMessage, delay);
        } else {
          this.messageFinised = true
        }
      };

      processNextMessage();
    },

    clearMessageDelay() {
      if (this.messageDelay.length > 0) {
        this.messageDelay.forEach((element) => {
          clearTimeout(element);
        });
        this.messageDelay = [];
        this.npcMessage = "";
      }
    },

    showNextIntroMessage() {
      if (this.step < this.mainMessages.length - 1) {
        this.step++;
        this.Setmessage(this.mainMessages[this.step]);
      } else {
        this.closeModal('help');
      }
    },
    
    showNextMessage() {
      console.log(this.step)
      if (this.selectedMessages == this.mainMessages) {
        return
      }

      if (!this.messageFinised){
        this.npcMessage = this.selectedMessages[this.selectedIndex]
        this.messageFinised = true
        return
      }

      if (this.selectedIndex < this.selectedMessages.length-1) {
        this.selectedIndex++;
        this.Setmessage(this.selectedMessages[this.selectedIndex]);
      } else {
        this.closeModal('help');
      }
    },


    
    // 각각 상황별 처리

    // 전적 조회
    showCheckScore() {
      this.selectedMessages = this.checkScoreMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = true
      this.isClikedGameRoom = false
      this.isClikedCharacter = false
      this.isClikedSpy = false
      this.isClikedMisson = false
      this.isClikedCam = false
    },

    // 게임방 생성
    showCreateGameroom(){
      this.selectedMessages = this.createGameroomMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = false
      this.isClikedGameRoom = true
      this.isClikedCharacter = false
      this.isClikedSpy = false
      this.isClikedMisson = false
      this.isClikedCam = false
    },

    // 말 선택
    showSelectCharacter(){
      this.selectedMessages = this.selectCharacterMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = false
      this.isClikedGameRoom = false
      this.isClikedCharacter = true
      this.isClikedSpy = false
      this.isClikedMisson = false
      this.isClikedCam = false
    },

    // 밀정 선택
    showSelectSpy(){
      this.selectedMessages = this.selectSpyMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = false
      this.isClikedGameRoom = false
      this.isClikedCharacter = false
      this.isClikedSpy = true
      this.isClikedMisson = false
      this.isClikedCam = false
    },

    // 미션 수행
    showDoMission(){
      this.selectedMessages = this.doMissionMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = false
      this.isClikedGameRoom = false
      this.isClikedCharacter = false
      this.isClikedSpy = false
      this.isClikedMisson = true
      this.isClikedCam = false
    },

    // 캠 관리
    showManageCam(){
      this.selectedMessages = this.manageCamMessages
      this.selectedIndex = 0
      this.step = 2
      this.clearMessageDelay();
      this.npcMessage = "";
      this.isClikedScore = false
      this.isClikedGameRoom = false
      this.isClikedCharacter = false
      this.isClikedSpy = false
      this.isClikedMisson = false
      this.isClikedCam = true
    },

    showExpMessage() {
      if (this.check < this.checkScoreMessages.length - 1) {
        this.check++;
        this.Setmessage(this.checkScoreMessages[this.check]);
      } else {
        this.closeModal('help');
      }
    },
  },

  mounted() {
    this.Setmessage(this.mainMessages[this.step]);
    this.selectedIndex = this.step;
    this.selectedMessages = this.mainMessages;
  },
};
</script>

<style scoped>
/* @import url("../../assets/css/home/help.css"); *//* 모달 애니메이션 */
.modal {
  /* 기본 스타일 유지 */
  border-color: black;
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
  position: fixed;
  left: 0;
  top: 0px;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.879);
  z-index: 5000;
}

.cancel-button {
  display: flex;
  width: 25px;
  height: 25px;
  margin: 10px;
  position: absolute;
  top: 10px;
  right: 10px;
}

.next-vector {
  width: 25px;
  height: 25px;
  position: absolute;
  top: 40%;
  right: 0px;
  transform: translateY(-50%);
  margin-right: 25%;
}

#work-npc-main {
  position: fixed;

  width: 100%;
  height: 100%;

  top: 0;
  left: 0;

  /* backdrop-filter: blur(5px); */
  background: rgba(50, 50, 50, 0.4);

  z-index: 3;
}

#work-npc-main > .work-npc-box {
  position: absolute;

  width: 100%;
  height: 300px;

  left: 0;
  bottom: 0;

  background: linear-gradient(
    to right,
    rgba(0, 0, 0, 0.85) 50%,
    rgba(0, 0, 0, 0.65) 80%,
    rgba(0, 0, 0, 0.25) 90%,
    rgba(0, 0, 0, 0.05) 95%,
    rgba(0, 0, 0, 0) 100%
  );
}

#work-npc-main > .work-npc-avatar {
  position: absolute;

  top: 150px;
  left: 0;
  bottom: 0;
  filter: drop-shadow(10px 0 10px rgba(0, 0, 0, 0.6));
}

#work-npc-main > .work-npc-box > .work-npc-name {
  position: absolute;

  top: -40px;

  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 25px;

  padding: 0 10px;
  padding-left: 100px;

  color: white;
  background: linear-gradient(
    to left,
    rgba(0, 0, 0, 0.85) 0%,
    rgba(0, 0, 0, 0) 100%
  );
}

.work-npc-content {
  padding-top: 50px;
}

#work-npc-main > .work-npc-box > .work-npc-content {
  color: white;
  font-size: 20px;

  width: 500px;
  height: 130px;
  /* border: 1px solid red; */

  text-align: center;
  margin: 0 auto;

  display: flex;
  align-items: center;
  justify-content: center;
}

#work-npc-main > .work-npc-box > .work-npc-buttons {
  width: max-content;
  margin: 0 auto;
  margin-top: 5px;
}

.work-npc-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-top: 30px;
}

.modal-btn {
  margin-left: 10px;
  border-radius: 50px;
}

.modal-yes-btn {
  display: block;
  margin: auto;
  border-radius: 20px;
  cursor: pointer;
  margin-top: 30px;
}

/* 응, 알려줘 버튼 호버 효과 */
.modal-yes-btn:hover {
  background-color: #2D81FF;
  color: white;
  border-radius: 20px;

  /* 트랜지션 효과 */
  transition: background-color 0.3s ease, color 0.3s ease;
}


/* 아니 버튼 호버 효과 */
.modal-no-btn:hover {
  background-color: #DC3545;
  color: white;
  
  /* 트랜지션 효과 */
  transition: background-color 0.3s ease, color 0.3s ease;
}

.modal-exp-btn:hover {
  background-color: #db55c5;
  color: white;
  transition: background-color 0.3s ease, color 0.3s ease;
  border-radius: 20px;
}

/* 선택됐을 때 버튼 */
.changedColor {
  background-color: #db55c5;
  color: white;
  border-radius: 20px;
}

.hide-previous-message {
  display: none;
}
</style>
