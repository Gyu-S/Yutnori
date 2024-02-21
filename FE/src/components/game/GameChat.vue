<template>
  <div class="board-chat">
    <div class="chat-container">
      <div
        v-for="(message, index) in reversedRoomChat"
        :key="index"
        class="chat-log"
      >
        <span
          :style="{
            color: getColorForMessage(message).color,
          }"
          v-html="getColorForMessage(message).text"
        ></span>
      </div>
    </div>

    <div class="chat-input-div">
      <input
        class="chat-input"
        type="text"
        placeholder="메시지를 입력하시오."
        v-model="msg"
        @keyup.enter="sendLocalMessage"
      />
      <button class="send-btn" @click="sendLocalMessage">
        <span>보내기</span>
      </button>
    </div>
  </div>
</template>

<script>
import { useGameStore } from "@/store/gameStore";
import { useUserStore } from "@/store/userStore";
import { usePickStore } from "@/store/pickStore";
import { socketSend } from "@/util/socket";

export default {
  data() {
    return {
      msg: "",
    };
  },

  computed: {
    gameChatMsg() {
      const gameStore = useGameStore();
      return gameStore.gameChatMsg;
    },
    reversedRoomChat() {
      return this.gameChatMsg.slice().reverse();
    },
    receivedMsg() {
      const gameStore = useGameStore();
      return gameStore.receivedMsg;
    },
  },

  watch: {
    receivedMsg(newVal) {
      const gameStore = useGameStore();
      if (newVal.actionCategory === 6) {
        gameStore.gameChatMsg.push(newVal.nickname + " : " + newVal.message);
      }
    },
  },

  methods: {
    sendLocalMessage() {
      if (this.msg === "") return;

      let teamName = "";
      if (usePickStore().code.includes("red")) teamName = "홍팀";
      else teamName = "청팀";

      let tempMSG = {
        team: teamName,
        nickname: useUserStore().userInfo.nickname,
        message: this.msg,
      };

      socketSend(
        "/pub/game/" + useUserStore().currentRoomInfo.roomCode + "/chat",
        tempMSG
      );

      this.msg = "";
    },

    // 메시지의 종류에 따라 색상을 반환하는 메서드
    getColorForMessage(message) {
      const parts = message.split(" :"); // ":"를 기준으로 메시지를 분할

      return {
        text: `<span style="color: #9bff9bcb; float: left; margin-left: 20px; margin-right: 10px">[${parts[0]}] </span> <span style="color: white; float: left;">${parts[1]}</span>`,
      };
    },
  },
};
</script>

<style scoped>
.board-chat {
  color: white;
  width: 18vw;
  height: 26vh;
  background-color: rgba(90, 90, 90, 0.409);
  border-radius: 10px;
  border: 1px solid white;
}

.chat-container {
  display: flex;
  flex-direction: column-reverse;
  overflow-y: scroll;
  height: 20vh;
  margin-left: 20px;
  scrollbar-width: thin;
  scrollbar-color: #888 transparent;
}

.chat-name {
  width: 80px;
}

.chat-div {
  margin-left: 10px;
  display: flex;
  font-size: 14px;
}
/* 자기 자신은 다른 색으로 구별하였다 */
.my-chat .chat-div {
  color: green;
}

.chat-chat {
  margin-left: 10px;
}
/* 입력 칸 */
.chat-input-div {
  border-radius: 10px;
  border: 1px solid white;
  height: 5vh;
  width: 17vw;
  text-align: left;
  margin-left: 8px;
  color: green;
  display: flex;
  justify-content: space-between;
}

.send-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 100px;
  background-color: rgba(72, 27, 222, 0.637);
}

.send-btn > span {
  display: flex;
}

.send-btn:hover {
  background-color: rgb(150, 33, 33);
}

.send-btn > span {
  align-content: center;
}

.chat-input {
  margin-left: 10px;
  width: 10vw;
  background-color: transparent;
  border: none;
  color: white;
}

/* input을 클릭 시 테두리가 있어 제거 */
.chat-input:focus {
  outline: none;
}

/* Chrome, Edge, Safari에서 스크롤바 스타일 설정 */
.chat-container::-webkit-scrollbar {
  width: 8px;
}

.chat-container::-webkit-scrollbar-track {
  background: transparent;
}

.chat-container::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 10px;
}

.chat-container::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}
</style>
