<template>
  <div class="background">
    <transition name="fade">
      <Loading v-if="showStartModal" @close-modal="closeModal" />
    </transition>

    <transition name="fade">
      <Wait v-if="showWaitModal" @close-modal="closeModal" />
    </transition>

    <transition name="fade"
      ><spyModal v-if="showSpyModal" @close-modal="closeModal"
    /></transition>

    <transition name="fade"
      ><WaitSpyOpp v-if="showWaitSpyOppModal" @close-modal="closeModal"
    /></transition>

    <transition name="fade"
      ><WaitSpyPick v-if="showWaitSpyPickModal" @close-modal="closeModal"
    /></transition>

    <div class="timer">{{ nowRemainTime }}</div>
    <div class="content">
      <!-- (시작) OpenVidu -->
      <div id="main-container" class="container">
        <div id="session" v-if="session">
          <!-- (시작) 카메라 영역 -->
          <div class="rtc-container">
            <div id="video-container">
              <user-video
                v-for="user in myTeamUsers"
                :key="user.stream.connection.connectionId"
                :stream-manager="user"
                :nickname="currentUserNickname"
              />
            </div>
          </div>
          <!-- (끝) 카메라 영역 -->
        </div>
      </div>
      <!-- (끝) OpenVidu -->

      <div class="characters-wrapper">
        <div class="character-container">
          <div v-for="user in getUserInfo" :key="user.userId">
            <div class="character-item">
              <img
                class="sample-img"
                :src="
                  getCharacterImage(user.selectUnitId)
                    ? getCharacterImage(user.selectUnitId)
                    : imgs
                "
              />
              <p>{{ user.nickname }}</p>
            </div>
          </div>
        </div>

        <!-- (시작) 캐릭터 활성화/비활성화 -->
        <div class="character-box" v-if="getUnitInfo">
          <div v-for="character in characters" :key="character" class="box">
            <div
              class="character"
              :class="{
                disabled: getUnitInfo.find(
                  (info) => info.name === getCharacterName(character)
                )?.pick,
              }"
              :style="{
                opacity: getUnitInfo.find(
                  (info) => info.name === getCharacterName(character)
                )?.pick
                  ? '0.2'
                  : '1',
              }"
              @click="selectCharacter(character)"
            >
              <img :src="character" class="select-img" />
            </div>
            <div class="character-name">
              <span v-if="character.includes('king')">대왕</span>
              <span v-else-if="character.includes('spearman')">창병</span>
              <span v-else-if="character.includes('cavalry')">기병</span>
              <span v-else-if="character.includes('peasant')">농민</span>
              <span v-else-if="character.includes('slave')">노비</span>
            </div>
          </div>
        </div>
        <!-- (끝) 캐릭터 활성화/비활성화 -->
        <button @click="prepareComplete" class="ready" v-if="getIsMyTurn">
          준비완료
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { watch } from "vue";

import spyModal from "@/components/game/pick/Spy.vue";
import Loading from "@/components/game/pick/Loading.vue";
import Wait from "@/components/game/pick/Wait.vue";
import WaitSpyPick from "@/components/game/pick/WaitSpyPick.vue";
import WaitSpyOpp from "@/components/game/pick/WaitSpyOpp.vue";

import { useUserStore } from "@/store/userStore";
import { usePickStore } from "@/store/pickStore";
import { pubPick, pubPickInfo } from "@/util/socket";

import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/game/openvidu/UserVideo.vue";

import king from "@/assets/img/game/pick/king.png";
import cavalry from "@/assets/img/game/pick/cavalry.png";
import peasant from "@/assets/img/game/pick/peasant.png";
import slave from "@/assets/img/game/pick/slave.png";
import spearman from "@/assets/img/game/pick/spearman.png";
import alternativeImage from "@/assets/img/1.png";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  components: {
    spyModal,
    Loading,
    Wait,
    WaitSpyPick,
    WaitSpyOpp,
    UserVideo,
  },

  data() {
    return {
      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      remainingTime: 30,
      timerInterval: null,

      mySessionId: "",
      myUserName: "",

      userInfo: null,
      unitInfo: null,

      myTeamName: "",
      isMyturn: false,

      currentUserNickname: "",
      currentIdx: "",

      nowDoPickPlayerEmail: "",
      nowRemainTime: null,

      selectedCharacter: null,
      selectedIdx: 0,

      cancelTimeout: null,

      isSelected: false,
      finished: false,

      showReadyBtn: false,
      showStartModal: true,
      showSpyModal: false,
      showWaitModal: false,
      showWaitSpyPickModal: false,
      showWaitSpyOppModal: false,

      isLeader: false,

      myTeamUsers: [],

      imgs: alternativeImage,
    };
  },

  created() {},

  setup() {
    const store = useUserStore();

    const characters = [king, spearman, cavalry, peasant, slave];
    const selectedCharacters = [];

    return {
      openModal: store.openModal,
      characters,
      selectedCharacters,
      borderColor: "initialBorderColor",
    };
  },

  methods: {
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        // this.subscribers.push(subscriber);
        // console.log(this.subscribers);

        this.myTeamUsers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {});

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publish er
            this.mainStreamManager = publisher;
            // this.publisher = publisher;

            this.myTeamUsers.push(publisher);

            // --- 6) Publish your stream ---
            this.session.publish(publisher);
          })
          .catch((error) => {});
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.myTeamUsers = [];

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(
        "https://i10d205.p.ssafy.io/api/v1" + "/sessions",
        { customSessionId: sessionId },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data;
    },

    async createToken(sessionId) {
      const response = await axios.post(
        "https://i10d205.p.ssafy.io/api/v1" +
          "/sessions/" +
          sessionId +
          "/connections",
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },

    // 새로고침 방지 이벤트
    leave(event) {
      event.preventDefault();
      event.returnValue = "홈으로...";

      // 홈으로 이동
      this.leaveSession();
      useUserStore().initData();
      alert("홈으로!!");
      window.location.href = "/";
      return event.returnValue;
    },

    // 픽 차례가 자신에게 왔을때 빨간색 보더 추가
    applyBorderToActiveUser(idx) {
      const userElements = document.querySelectorAll(".character-item");
      let i = 0;

      userElements.forEach((element) => {
        const userEmail = element.getElementsByClassName("sample-img")[0];
        if (idx === i) userEmail.style.border = "5px solid red";
        else userEmail.style.border = "";
        i++;
      });
    },

    // 캐릭터를 선택하면 "4px solid red" 적용
    applyBorder(character) {
      const selectedCharacterInfo = this.getUnitInfo.find(
        (info) => info.name === this.getCharacterName(character)
      );
      const select = document.querySelector(`[src='${character}']`);

      // 모든 캐릭터에 설정된 보더를 초기화
      const allCharacters = document.querySelectorAll(".character img");
      allCharacters.forEach((img) => {
        img.style.border = "none";
        img.classList.add("empty-image");
      });

      // 선택된 캐릭터에 보더를 적용 (비활성화된 경우 보더를 적용하지 않음)
      if (!selectedCharacterInfo.pick) {
        select.style.border = "4px solid red";
        select.classList.remove("empty-image");
      }
    },

    // 캐릭터 이미지를 클릭하면 보더 색상 추가
    selectCharacter(character) {
      // 자기 차례가 아니면 선택 불가능
      if (!this.getIsMyTurn) return;

      // 선택한 인덱스 번호
      this.selectedIdx = this.getCharacterIdx(character);

      // 픽한 캐릭터라면 선택 불가능
      if (this.getUnitInfo[this.selectedIdx - 1].pick) return;

      // 보더 색상 추가
      this.applyBorder(character);
      this.selectedCharacter = character;

      if (this.selectedIdx === 0) return;

      // 실시간으로 값을 전달
      const sendData = {
        team: this.myTeamName,
        email: useUserStore().userInfo.email,
        unitId: this.selectedIdx,
      };

      // 서버로부터 픽을 보내고 현재 무엇을 선택하고 있는지 정보를 pub한다.
      pubPickInfo(
        "/pub/pick/" + useUserStore().currentRoomInfo.roomCode + "/select",
        sendData
      );
    },

    // 선택한 캐릭터 이름 가져오기.
    getCharacterName(str) {
      if (str.includes("king")) {
        return "대왕";
      } else if (str.includes("spearman")) {
        return "창병";
      } else if (str.includes("cavalry")) {
        return "기병";
      } else if (str.includes("peasant")) {
        return "농민";
      } else {
        return "노비";
      }
    },

    // 선택한 캐릭터 이름 가져오기.
    getCharacterIdx(str) {
      if (str.includes("king")) {
        return 1;
      } else if (str.includes("spearman")) {
        return 2;
      } else if (str.includes("cavalry")) {
        return 3;
      } else {
        return 0;
      }
    },

    // 랜덤 인덱스 접근하여 가져오기.
    getRandomPick(arr) {
      return Math.floor(Math.random() * arr.length);
    },

    // 선택한 유닛 아이디 확인
    getCharacterImage(selectedUnitId) {
      if (selectedUnitId === 1) {
        return king;
      } else if (selectedUnitId === 2) {
        return spearman;
      } else if (selectedUnitId === 3) {
        return cavalry;
      } else {
        return "";
      }
    },

    async startTimer() {
      // 현재 남은 시간을 나타내는 변수
      let remainingTime = 15;

      const timer = () => {
        if (remainingTime > 0) {
          remainingTime--;
          this.nowRemainTime = remainingTime;
          this.timerInterval = setTimeout(timer, 1000);
        }
      };

      timer();
    },

    async delay(ms) {
      return new Promise((resolve) => {
        const timeout = setTimeout(() => {
          resolve();
        }, ms);

        // 버튼 클릭 시 타이머 취소
        this.cancelTimeout = () => {
          clearTimeout(timeout);
          resolve(); // 버튼 클릭 시 즉시 다음 문장 실행
        };
      });
    },

    async delay2(ms) {
      return new Promise((resolve) => {
        setTimeout(resolve, ms);
      });
    },

    // '준비완료' 버튼 클릭 시 실행되는 함수
    async prepareComplete() {
      clearTimeout(this.timerInterval);
      this.cancelTimeout();
    },

    // (시작, 대기) 로딩 중 모달
    openModal(value) {
      if (value === "wait") {
        this.showWaitModal = true;
      }
    },

    closeModal(value) {
      if (value === "start") {
        this.showStartModal = false;
      } else if (value === "wait") {
        this.showWaitModal = false;
      }
    },
  },

  /* 캐릭터 픽창 시작 */
  async mounted() {
    // 먼저, 서버에게 픽 시작을 알리고 픽 순서와 타임을 입력 받는다.
    pubPick("/pub/pick/" + useUserStore().currentRoomInfo.roomCode + "/start");

    const pickStore = usePickStore();

    watch(
      () => pickStore.finished,
      (newValue) => {
        if (!this.isSelected) this.prepareComplete();
        else this.isSelected = false;
      }
    );

    watch(
      () => pickStore.pickFinished,
      (newValue) => {
        this.showWaitModal = true;
      }
    );

    watch(
      () => pickStore.pickRealFinished,
      (newValue) => {
        this.showWaitModal = false;

        if (this.isLeader) this.showSpyModal = true;
        else this.showWaitSpyPickModal = true;
      }
    );

    watch(
      () => pickStore.pickSpyWait,
      (newValue) => {
        if (this.isLeader) this.showSpyModal = false;
        else this.showWaitSpyPickModal = false;

        this.showWaitSpyOppModal = true;
      }
    );
    
    // 새로고침 방지 이벤트를 추가한다.
    window.addEventListener("beforeunload", this.leave);

    // 자신의 이름과 이메일
    this.currentUserNickname = useUserStore().userInfo.nickname;

    // 게임 진행에 방해되는 요소는 잠시 false
    useUserStore().showModalSide = false;

    let myTurnNumber = -1;

    // (1) 픽창으로부터 홍팀 또는 청팀의 정보를 받아온다.
    // * userInfo : 사용자 픽 순서, 픽 유무 등
    // * unitInfo : 현재 구현한 캐릭터 정보
    setTimeout(() => {
      const str = usePickStore().code;
      this.myTeamName = str.substring(str.lastIndexOf("/") + 1);

      if (this.myTeamName === "red") this.myTeamName = "홍팀";
      else this.myTeamName = "청팀";

      for (let i = 0; i < this.getUserInfo.length; i++) {
        if (this.getUserInfo[i].nickname === useUserStore().userInfo.nickname) {
          myTurnNumber = i + 1;
          break;
        }
      }

      // (2) 자신의 픽 순서를 보고 자신의 팀 OpenVidu에 접속한다.
      setTimeout(() => {
        this.myUserName = useUserStore().userInfo.nickname;
        this.mySessionId = usePickStore().code.replace(/\//g, "");
        this.joinSession();
      }, 250 * myTurnNumber);

      // (3) 서버에게 받은 현재 픽 해야하는 이메일과 타임을 가져온다.
      setTimeout(async () => {
        for (let i = 0; i < 3; i++) {
          // 초기 랜더링 작업 때문에,
          // 3~5초 정도 기다리고 그 후부터는 픽 시간에 맞춤.
          // (로딩중이라는 모달창 띄울 필요)

          if (i === 0) await this.delay(6800); // 로딩 시간
          else await this.delay2(1000);

          this.currentIdx = i;

          // 시간은 누구에게나 다 보여줘야 함.
          this.nowRemainTime = usePickStore().nowPickPlayerInfo.time;
          this.startTimer();

          // 여기서는 자신이 픽이라는 것을 red 색상으로 보더 색칠
          this.applyBorderToActiveUser(i);

          if (this.getIsMyTurn) {
            this.isSelected = true;
          }

          // 픽 하거나 대기하는 시간
          // await this.delay(usePickStore().nowPickPlayerInfo.time * 1000);
          await this.delay(15000);

          // 만약 현재 자신의 턴이라면
          if (this.getIsMyTurn) {
            if (i === 0) this.isLeader = true;

            let idx = -1;
            let selectedCharacterName = null;

            // 캐릭터를 픽 하였다면, idx에 선택한 유닛의 ID 저장
            if (this.selectedCharacter !== null) {
              selectedCharacterName = this.getCharacterName(
                this.selectedCharacter
              );

              for (let j = 0; j < this.getUnitInfo.length; j++) {
                if (this.getUnitInfo[j].pick) continue;

                if (this.getUnitInfo[j].name === selectedCharacterName) {
                  idx = j;
                  break;
                }
              }
            }

            // 캐릭터를 픽하지 않을 경우, 살아있는 유닛의 ID 들 중 랜덤으로 하나 저장
            else {
              const notSelectedCharacters = [];

              for (let j = 0; j < this.getUnitInfo.length; j++) {
                if (this.getUnitInfo[j].pick) continue;
                notSelectedCharacters.push(j);
              }
              idx = this.getRandomPick(notSelectedCharacters);
            }

            // 여기서는 자신이 픽한 것을 pub로 서버에게 알리는 로직
            const sendData = {
              team: this.myTeamName,
              email: this.getCurrentEmail,
              unitId: idx + 1,
            };

            // 서버로부터 픽을 보내고 다음 픽을 받도록 준비한다.
            pubPickInfo(
              "/pub/pick/" + useUserStore().currentRoomInfo.roomCode + "/done",
              sendData
            );
          }
        }

        // 1초 뒤 현재 상황 대기
      }, 250 * (4 - myTurnNumber));
    }, 50);
  },

  // mounted에 설정한 새로고침 방지 이벤트 리스너를 삭제한다.
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.confirmExit);
  },

  computed: {
    // userInfo를 반환
    getUserInfo() {
      return usePickStore().userInfo;
    },

    // unitInfo 반환하는 computed 속성
    getUnitInfo() {
      return usePickStore().unitInfo;
    },

    // 자신의 차례인지 여부를 반환하는 computed 속성
    getIsMyTurn() {
      return usePickStore().nowPickPlayerInfo.email === this.getCurrentEmail;
    },

    // 현재 내 이메일
    getCurrentEmail() {
      return useUserStore().userInfo.email;
    },
  },
};
</script>

<style scoped>
@import url("../../../assets/css/game/character.css");
</style>
