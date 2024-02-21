<template>
  <div class="game-main">
    <transition name="fade">
      <Loading v-if="showStartModal" @close-start-modal="closeStartModal" />
    </transition>

    <MessageModal />

    <!-- 홍팀 -->
    <div class="game-video-team1">
      <div id="session" v-if="session">
        <div class="rtc-container">
          <div id="video-container">
            <user-video
              v-for="user in redUsers"
              :key="user.stream.connection.connectionId"
              :stream-manager="user"
              :nickname="currentUserNickname"
            />
          </div>
        </div>
      </div>
    </div>

    <GameBoard class="game-board-main" />

    <!-- 청팀 -->
    <div class="game-video-team2">
      <div id="session" v-if="session">
        <div class="rtc-container">
          <div id="video-container">
            <user-video
              v-for="user in blueUsers"
              :key="user.stream.connection.connectionId"
              :stream-manager="user"
              :nickname="currentUserNickname"
            />
          </div>
        </div>
      </div>
    </div>

    <MiniGame v-if="isMission"/>
    <GameChat class="game-chat-main" />
    <GameEnd
      v-if="isGameEnd"
      class="game-end"
      :winMessage="winMessage"
      @closeModal="closeModal"
    />
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/game/UserVideo.vue";
import { socketSend } from "@/util/socket.js";

import { useUserStore } from "@/store/userStore";
import { useGameStore } from "@/store/gameStore";
import { useMiniGameStore } from "@/store/miniGameStore"

import GameBoard from "@/components/game/GameBoard.vue";
import GameChat from "@/components/game/GameChat.vue";
import MiniGame from "@/components/game/MiniGame.vue";
import MessageModal from "@/components/layout/MessageModal.vue";
import GameEnd from "@/components/game/GameEnd.vue";
import Loading from "@/components/game/Loading.vue";

export default {
  components: {
    GameBoard,
    GameChat,
    MiniGame,
    MessageModal,
    GameEnd,
    Loading,
    UserVideo,
  },

  data() {
    return {
      winMessage: null,
      showStartModal: true,

      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      // subscribers: [],

      mySessionId: "",
      myUserName: "",
      myTeamIdx: null,

      currentUserNickname: "",

      redUsers: [],
      blueUsers: [],
      msg: { team: 1 },
    };
  },

  created() {
    // 새로고침 방지 이벤트를 추가한다.
    this.myTeamIdx = useUserStore().myTeamIdx;
  },

  computed: {
    // 미션 장소 체크.
    isMission() {
      const gameStore = useGameStore();
      if(gameStore.isMission){
        useMiniGameStore().isShowCardSelect = true;
      }
      return gameStore.isMission;
    },
    // 게임 상태 체크
    isGameEnd() {
      if (useGameStore().redEnd === 5) {
        this.redWin();
        return true;
      }
      if (useGameStore().blueEnd === 5) {
        this.blueWin();
        return true;
      }
      if (useGameStore().spyGoal) {
        this.spyEnd();
        return true;
      }
      return false;
    },

    currentUserNickname() {
      return useUserStore().userInfo.nickname;
    },
  },

  methods: {
    leave(event) {
      event.preventDefault();
      event.returnValue = "홈으로...";

      // 홈으로 이동
      useUserStore().initData();
      alert("홈으로!!");
      window.location.href = "/";
      return event.returnValue;
    },
    redWin() {
      this.msg.team = 1;
      if (useGameStore().isThrowYut) {
        socketSend(
          `/pub/game/${useUserStore().currentRoomInfo.roomCode}/finish`,
          this.msg
        );
      }
      this.winMessage = 1;
      this.isShowEnd = true;
    },
    blueWin() {
      this.msg.team = 2;
      if (useGameStore().isThrowYut) {
        socketSend(
          `/pub/game/${useUserStore().currentRoomInfo.roomCode}/finish`,
          this.msg
        );
      }
      this.winMessage = 2;
      this.isShowEnd = true;
    },
    spyEnd() {
      this.msg.team = useGameStore().receivedMsg.team === 1 ? 2 : 1;
      if (useGameStore().isThrowYut) {
        socketSend(
          `/pub/game/${useUserStore().currentRoomInfo.roomCode}/finish`,
          this.msg
        );
      }
      this.winMessage = useGameStore().receivedMsg.team === 1 ? 2 : 1;
      this.isShowEnd = true;
    },
    closeModal() {
      this.$router.push({ name: "home" });
    },

    closeStartModal(value) {
      if (value === "start") {
        this.showStartModal = false;
      }

      // 게임 시작
    },

    joinSession() {
      this.OV = new OpenVidu();
      this.session = this.OV.initSession();

      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        // this.subscribers.push(subscriber);

        const data = JSON.parse(stream.connection.data);
        const jsonData = JSON.parse(data.data);
        const teamValue = jsonData.team;

        if (teamValue === 1) {
          this.redUsers.push(subscriber);
        } else if (teamValue === 2) {
          this.blueUsers.push(subscriber);
        }
      });

      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      const userData = {
        team: useGameStore().myTeam,
      };

      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, {
            clientData: this.myUserName,
            data: JSON.stringify(userData),
          })
          .then(() => {
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined,
              videoSource: undefined,
              publishAudio: true,
              publishVideo: true,
              resolution: "640x480",
              frameRate: 30,
              insertMode: "APPEND",
              mirror: false,
            });

            this.mainStreamManager = publisher;

            if (useGameStore().myTeam === 1) this.redUsers.push(publisher);
            else this.blueUsers.push(publisher);

            this.session.publish(publisher);
          })
          .catch((error) => {});
      });
    },

    leaveSession() {
      this.OV = null;
      this.session = null;
      this.mainStreamManager = null;

      // 스트림 매니저 삭제
      this.subscribers.forEach((subscriber, index) => {
        subscriber.stream.disposeVideoElement();
        subscriber.unsubscribe();
        this.subscribers.splice(index, 1);
      });

      // 빨간 팀 사용자와 파란 팀 사용자 배열 초기화
      this.redUsers = [];
      this.blueUsers = [];

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
      return response.data;
    },

    async delay2(ms) {
      return new Promise((resolve) => {
        setTimeout(resolve, ms);
      });
    },
  },

  async mounted() {
    window.addEventListener("beforeunload", this.leaveSession);

    this.myUserName = useUserStore().userInfo.nickname;
    this.mySessionId = useUserStore().currentRoomInfo.roomCode;

    useUserStore().showModalSide = false;

    // 여기서 순서 생각하기.
    const red = useGameStore().redUser;
    const blue = useGameStore().blueUser;

    console.log("내팀은????????????????????")
    console.log(useGameStore().myTeam);

    setTimeout(async () => {
      if (useGameStore().myTeam === 1) {
        for (let i = 1; i <= 3; i++) {
          if (this.myUserName === red[i - 1].nickname) {
            this.joinSession();
            break;
          }
        }
      } else {
        for (let i = 1; i <= 3; i++) {
          if (this.myUserName === blue[i - 1].nickname) {
            this.joinSession();
            break;
          }
        }
      }
    }, 1500);
  },
};
</script>

<style scoped>
@import "@/assets/css/game/gameView.css";
</style>
