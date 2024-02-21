<template>
  <div>
    <div class="side-bar" v-if="showFlag">
      <div class="side-title">
        <span class="side-text" v-if="showMessageFlag">알림</span>
        <span class="side-text" v-if="showChatFlag">채팅방</span>
        <span class="side-text" v-if="showFriendFlag">친구목록</span>
        <span class="side-text" v-if="showUserSearchFlag">사용자 검색</span>
        <button class="btn" @click="closeSidebar">
          <img class="side-close" src="@/assets/icon/close.png" />
        </button>
      </div>
      <div class="side-line"></div>
      <transition name="fade" mode="out-in">
        <MessageComponents v-if="showMessageFlag"
        @open-chat-room="handleOpenChatRoom"
      /></transition>
      <transition name="fade" mode="out-in">
        <ChatComponents v-if="showChatFlag"
        @open-friend-list="handleOpenFriendRoom"
        :friendInfo="chatFriend"
      /></transition>
      <transition name="fade" mode="out-in">
        <FriendComponents v-if="showFriendFlag"
        @open-chat-room="handleOpenChatRoom"
      /></transition>
      <transition name="fade" mode="out-in">
        <UserSearchComponents v-if="showUserSearchFlag"
      /></transition>
    </div>
    <SettingComponents
      v-if="showSettingFlag"
      @close-setting="closeSettingModal"
    />
    <div class="side-btn">
      <button class="btn" @click="showMessage">
        <img class="btn-img" src="@/assets/icon/notification.png" />
      </button>
      <button class="btn" @click="showFriend">
        <img class="btn-img" src="@/assets/icon/friend.png" />
      </button>
      <button class="btn" @click="showFriendSearch">
        <img class="btn-img" src="@/assets/icon/friendAdd.png" />
      </button>
      <button class="btn" @click="showSetting">
        <img class="btn-img" src="@/assets/icon/setting.png" />
      </button>
    </div>
    <SettingComponents v-if="showSettingFlag" @close-setting="closeSetting" />
  </div>
</template>

<script>
import MessageComponents from "./MessageComponents.vue";
import FriendComponents from "./FriendComponents.vue";
import SettingComponents from "./SettingComponents.vue";
import UserSearchComponents from './UserSearchComponents.vue';
import ChatComponents from "./ChatComponents.vue";

export default {
  // 컴포넌트
  components: {
    MessageComponents,
    FriendComponents,
    SettingComponents,
    ChatComponents,
    UserSearchComponents,
  },
  data() {
    return {
      showFlag: true,
      showMessageFlag: false,
      showFriendFlag: true,
      showUserSearchFlag: false,
      showChatFlag: false,
      showSettingFlag: false,
      chatFriend: null,
    };
  },
  methods: {

    // 알림 메시지
    showMessage() {
      this.showFlag = true;
      this.showFriendFlag = false;
      this.showMessageFlag = true;
      this.showChatFlag = false,
      this.showUserSearchFlag = false;
    },
    // 친구 목록
    showFriend() {
      this.showFlag = true;
      this.showMessageFlag = false;
      this.showFriendFlag = true;
      this.showChatFlag = false,
      this.showUserSearchFlag = false;
    },
    // 유저 검색
    showFriendSearch() {
      this.showFlag = true;
      this.showMessageFlag = false;
      this.showFriendFlag = false;
      this.showChatFlag = false,
      this.showUserSearchFlag = true;
    },
    // 유저 채팅
    showChat() {
      this.showFlag = true;
      this.showMessageFlag = false;
      this.showFriendFlag = false;
      this.showChatFlag = true,
      this.showUserSearchFlag = false;
    },

    // 설정 모달 열기
    showSetting() {
      this.showSettingFlag = true;
    },

    // 설정 모달 닫기
    closeSetting() {
      this.showSettingFlag = false;
    },

    // 사이드바 닫기
    closeSidebar() {
      this.showMessageFlag = false;
      this.showFriendFlag = false;
      this.showChatFlag = false;
      this.showFlag = false;
    },

    // 유저 채팅 접근
    handleOpenChatRoom(friend) {
      this.chatFriend = friend
      this.showChat()
    },

    // 친구 목록 접근
    handleOpenFriendRoom() {
      this.showFriend()
    }
  },
};
</script>

<style scoped>
@import "@/assets/css/layout/sideBar.css";
</style>