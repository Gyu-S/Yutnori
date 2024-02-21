<template>
  <div v-if="myTeamIdx === 1" class="video-display">
    <div id="session" v-if="session">
      <!-- (시작) 카메라 영역 -->
      <div class="rtc-container">
        <div id="video-container">
          <user-video
            v-for="sub in filteredBlueSubscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
          />
        </div>
      </div>
      <!-- (끝) 카메라 영역 -->
    </div>
  </div>

  <div v-else-if="myTeamIdx === 2" class="video-display">
    <div id="session" v-if="session">
      <!-- (시작) 카메라 영역 -->
      <div class="rtc-container">
        <div id="video-container">
          <user-video
            :stream-manager="publisher"
            :nickname="currentUserNickname"
          />

          <user-video
            v-for="sub in filteredBlueSubscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
          />
        </div>
      </div>
      <!-- (끝) 카메라 영역 -->
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/game/UserVideo.vue";

import { useUserStore } from "@/store/userStore";

export default {
  components: {
    UserVideo,
  },

  data() {
    return {
      // OpenVidu objects
      // OV: undefined,
      // session: undefined,
      // mainStreamManager: undefined,
      // publisher: undefined,
      // subscribers: [],

      // mySessionId: "",
      // myUserName: "",
      // myTeamIdx: null,

      // currentUserNickname: "",
    };
  },

  created() {
    this.myTeamIdx = useUserStore().myTeamIdx;
  },

  computed: {
    currentUserNickname() {
      return useUserStore().userInfo.nickname;
    },

    // 현재 팀에 따라 구독자를 필터링
    filteredBlueSubscribers() {
      console.log(this.subscribers);
      return this.subscribers.filter((sub) => sub.teamNumber === 2);
    },
  },

  methods: {
    // joinSession() {
    //   this.OV = new OpenVidu();
    //   this.session = this.OV.initSession();

    //   this.session.on("streamCreated", ({ stream }) => {
    //     const subscriber = this.session.subscribe(stream);
    //     subscriber.teamNumber = useUserStore().myTeamIdx;
    //     subscriber.email = useUserStore().userInfo.email;
    //     this.subscribers.push(subscriber);
    //   });

    //   this.session.on("streamDestroyed", ({ stream }) => {
    //     const index = this.subscribers.indexOf(stream.streamManager, 0);
    //     if (index >= 0) {
    //       this.subscribers.splice(index, 1);
    //     }
    //   });

    //   this.session.on("exception", ({ exception }) => {});

    //   this.getToken(this.mySessionId).then((token) => {
    //     this.session
    //       .connect(token, {
    //         clientData: this.myUserName,
    //         teamNumber: useUserStore().myTeamIdx,
    //         email: useUserStore().userInfo.email,
    //       })
    //       .then(() => {
    //         let publisher = this.OV.initPublisher(undefined, {
    //           audioSource: undefined,
    //           videoSource: undefined,
    //           publishAudio: true,
    //           publishVideo: true,
    //           resolution: "640x480",
    //           frameRate: 30,
    //           insertMode: "APPEND",
    //           mirror: false,
    //         });

    //         this.mainStreamManager = publisher;
    //         this.publisher = publisher;

    //         this.session.publish(this.publisher);
    //       })
    //       .catch((error) => {});
    //   });

    //   window.addEventListener("beforeunload", this.leaveSession);
    // },

    // leaveSession() {
    //   if (this.session) this.session.disconnect();

    //   this.session = undefined;
    //   this.mainStreamManager = undefined;
    //   this.publisher = undefined;
    //   this.subscribers = [];
    //   this.OV = undefined;

    //   window.removeEventListener("beforeunload", this.leaveSession);
    // },

    // updateMainVideoStreamManager(stream) {
    //   let publisher = this.OV.initPublisher(undefined, {
    //     audioSource: undefined,
    //     videoSource: undefined,
    //     publishAudio: true,
    //     publishVideo: false,
    //     resolution: "640x480",
    //     frameRate: 30,
    //     insertMode: "PREPEND",
    //     mirror: false,
    //   });

    //   if (this.publisher === publisher) return;
    //   this.publisher = publisher;
    // },

    // async getToken(mySessionId) {
    //   const sessionId = await this.createSession(mySessionId);
    //   return await this.createToken(sessionId);
    // },

    // async createSession(sessionId) {
    //   const response = await axios.post(
    //     "https://i10d205.p.ssafy.io/api/v1" + "/sessions",
    //     { customSessionId: sessionId },
    //     {
    //       headers: { "Content-Type": "application/json" },
    //     }
    //   );
    //   return response.data;
    // },

    // async createToken(sessionId) {
    //   const response = await axios.post(
    //     "https://i10d205.p.ssafy.io/api/v1" +
    //       "/sessions/" +
    //       sessionId +
    //       "/connections",
    //     {},
    //     {
    //       headers: { "Content-Type": "application/json" },
    //     }
    //   );
    //   return response.data;
    // },
  },

  mounted() {
    // if (this.myTeamIdx === 2) {
    //   this.myUserName = useUserStore().userInfo.nickname;
    //   this.mySessionId = "A";
    //   this.joinSession();
    // }
  },
};
</script>

<style scoped>
.test {
  width: 180px;
  height: 130px;
  background-color: black;
  border: 1px solid white;
  border-radius: 10px;
  margin-top: 5px;
}
</style>
