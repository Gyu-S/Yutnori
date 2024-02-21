<template>
  <!-- 로그인 모달 창 -->
  <div id="loginModal" class="modal">
    <div class="modal-content">
      <button class="close" @click="closeModal">&times;</button>
      <h2 class="login-text">Login</h2>
      <input type="text" placeholder="이메일" v-model="loginUser.email" />
      <input
        type="password"
        placeholder="패스워드"
        v-model="loginUser.password"
      />
      <br />
      <label for="rememberMeCheckbox" @click="toggleCheckbox()">
        <input class="login-text" type="checkbox" id="rememberMeCheckbox" />
        <span style="color: white">로그인 유지</span>
      </label>
      <br />
      <button @click="login()" class="modal-login-btn">로그인</button>
      <button @click="find()" class="modal-find-btn">비밀번호발급</button>
      <span>|</span>
      <button @click="join()" class="modal-join-btn">회원가입</button>
    </div>
  </div>
</template>
  
<script>
import { useUserStore } from "@/store/userStore";
import { sendLoginEvent } from '@/util/socket.js';

export default {
  name: "LoginComponent",
  data() {
    return {
      loginUser: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    async login() {
      const userStore = useUserStore();
      await userStore.userLogin(this.loginUser);
      if (userStore.isLogin) {
        userStore.getUserInfo();
        setTimeout(() => {
          switch (useUserStore().userInfo.email) {
            case "123":
              useUserStore().myTeamIdx = 2;
              break;
            case "4":
              useUserStore().myTeamIdx = 2;
              break;
            case "5":
              useUserStore().myTeamIdx = 2;
              break;
            case "1":
              useUserStore().myTeamIdx = 1;
              break;
            case "2":
              useUserStore().myTeamIdx = 1;
              break;
            case "3":
              useUserStore().myTeamIdx = 1;
              break;
          }
          this.closeModal();
          userStore.showModalSide = true;
          this.$router.push("/home");
        }, 200);
        await userStore.getUserInfo();
        this.closeModal();
        userStore.showModalSide = true;
        // 로그인 메시지 서버에 전달
        const event = {
        fromUserEmail: userStore.userInfo.email,
        eventCategory: '4',
        eventAction: 'LOGIN',
        message: `${userStore.userInfo.nickname}님이 로그인했습니다.`
        }
        sendLoginEvent(event)
        this.$router.push("/home");
      } else {
        userStore.isLogin = false;
        userStore.initData();
        this.$router.push("/");
      }
    },
    join() {
      this.closeModal();
      useUserStore().openModal("join");
    },
    find() {
      this.closeModal();
      useUserStore().openModal("find");
    },
    closeModal() {
      useUserStore().closeModal("login");
    },
  },
};
</script>

<style scoped>
@import "@/assets/css/user/login.css";
</style>