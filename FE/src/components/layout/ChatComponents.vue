<template>
  <div class="chat-box">
    <div class="chat-top">
      <h4 class="chat-partner">{{ friendNickname }}님과의 채팅방</h4>
      <font-awesome-icon 
      :icon="['fas', 'rotate-left']" size="lg" style="color: #ffffff;" 
      @click="goFriend"
      />
    </div>
    <div class="personal-chat">
      <!-- 채팅 로그 -->
      <div class="chat-container">
        <div
          v-for="(message, index) in reversedpersonalChat"
          :key="index"
          class="chat-log"
        >
          <span v-if="me === message[0]" class="nick">[나] </span>
          <span v-else class="nick">[{{ message[0] }}] </span>
          <span class="chat-message">{{ message[1] }}</span>
        </div>
        <!-- 입력 부분 -->
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
          <font-awesome-icon :icon="['fas', 'paper-plane']" size="lg" style="color: #e9eaed;" />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
// 아이콘
import { faPaperPlane, faRotateLeft } from "@fortawesome/free-solid-svg-icons";
import { library } from "@fortawesome/fontawesome-svg-core";
library.add(faPaperPlane, faRotateLeft);

import { useFriendStore } from "@/store/friendStore";
import { useUserStore } from "@/store/userStore";
import { sendEvent } from "@/util/socket";

export default {
  props: {
    friendInfo: Object
  },

  data() {
    return {
      msg: "",
      friendNickname: "",
      me: useUserStore().userInfo.nickname
    }
  },
  computed: {
    personalChat() {
      return useFriendStore().chatMessages[this.friendInfo.email] || []
    },

    reversedpersonalChat() {
      return this.personalChat.slice().reverse()
    }
  },

  methods: {
    // 메시지 보내기
    sendLocalMessage() {
      const friendStore = useFriendStore()
      if (this.msg === "") return;

      const event = {
        fromUserEmail: useUserStore().userInfo.email,
        toUserEmail: this.friendInfo.email,
        eventCategory: "1", 
        eventAction: "메시지 보내기",
        message : this.msg,
      }
      console.log(this.friendInfo)
      sendEvent(event)

      friendStore.addChatMyMessage(this.friendInfo.email, useUserStore().userInfo.nickname, this.msg)

      this.msg =""
    },

    // 닉네임에 따라 색 변경
    getNicknameColor(nickname) {
      if (nickname === useUserStore().userInfo.nickname) {
        return 'color-red'
      } else {
        return 'color-green'
      }
    },

    // 친구 있는 모달 띄우기
    goFriend(){
      this.$emit('open-friend-list')
    }
  },

  mounted() {
    this.friendNickname = this.friendInfo.nickname
  }
}
</script>

<style scoped>
@import "@/assets/css/layout/chat.css";
</style>