<template>
  <div class="nav-pro">
    <!-- 사용자가 설정한 사진을 보여준다 -->
    <div class="nav-img">
      <img src="../../assets/icon/user.png" class="img" />
    </div>
    <!-- 사용자의 정보를 간략히 보여준다 -->
    <div class="nav-info">
      <div class="nav-name">
        <span>{{ userName }}님</span>
      </div>
      <!-- 클릭 시 프로필로 화면을 이동한다 -->
      <div class="nav-my">
        <router-link @click="initializeData" to="/" class="nav-btn"
          >로그아웃</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import { useUserStore } from "@/store/userStore";
import { sendLogoutEvent } from '@/util/socket.js';
import { useSettingStore } from "@/store/settingStore";

export default {
  data() {
    return {};
  },

  methods: {
    // 로그아웃하기.
    async initializeData() {
      const confirmMessage = "정말 로그아웃 하시겠습니까?";
      
      if (confirm(confirmMessage)) {
        const userStore = useUserStore();
        // 로그아웃 메시지 서버에 전달 및 소켓 연결 끊기
        const event = {
          fromUserEmail: userStore.userInfo.email,
          eventCategory: '5',
          eventAction: 'LOGOUT',
          message: `${userStore.userInfo.nickname}님이 로그아웃했습니다.`
        }
        
        sendLogoutEvent(event)

        // 유저 정보 초기화
        useUserStore().initData();

        useSettingStore().isMusicPlaying = false

        this.$router.push("/");
      }
    },
  },

  computed: {
    userName() {
      const userInfo = useUserStore().userInfo;

      // userInfo를 이용한 로직
      return userInfo ? userInfo.nickname : "로딩 중...";
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/layout/navPro.css";
</style>
