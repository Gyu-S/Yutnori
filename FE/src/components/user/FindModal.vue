<template>
  <!-- 로그인 모달 창 -->
  <div id="loginModal" class="modal">
    <div class="modal-content">
      <button class="close" @click="closeModal">&times;</button>
      <h2 class="login-text">임시 비밀번호 발급</h2>
      <div>
        <input
          type="text"
          placeholder="이메일"
          v-model="email"
          :readonly="flag"
        />
        <button class="email-btn" @click="passEmailVerRequest">요청</button>
      </div>
      <input type="text" placeholder="이메일 인증 코드" v-model="code" />
      <br />
      <span class="find-msg">{{ msg }}</span>
      <br />
      <button @click="passEmailVer()" class="modal-login-btn">확인</button>
      <button @click="login()" class="modal-find-btn">로그인</button>
      <span>|</span>
      <button @click="join()" class="modal-join-btn">회원가입</button>
    </div>
  </div>
</template>
    
  <script>
import { useUserStore } from "@/store/userStore";
import { useStore } from "@/store";

export default {
  name: "LoginComponent",
  data() {
    return {
      email: "",
      code: "",
      msg: "",
      flag: false,
    };
  },
  methods: {
    // 인증코드 발송.
    passEmailVerRequest() {
      const re =
        /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      let flag = re.test(this.email);

      if (!flag) {
        this.msg = "이메일 주소를 정확히 입력해주세요.";
      } else {
        const userStore = useUserStore();
        userStore.passEmailVerRequest(this.email);
        this.msg = "인증코드를 발송하였습니다.";
      }
    },
    // 인증코드 인증.
    passEmailVer() {
      const param = { email: this.email, code: this.code };
      const userStore = useUserStore();
      userStore.passEmailVer(param);
      setTimeout(() => {
        if (useUserStore().isPassEmailCodeCheck) {
          this.flag = true;
          this.msg = "임시 비밀번호를 전송하였습니다.";
        } else {
          this.msg = "인증에 실패하였습니다.";
        }
      }, 3000);
    },
    join() {
      this.closeModal();
      useUserStore().openModal("join");
    },
    login() {
      this.closeModal();
      useUserStore().openModal("login");
    },
    closeModal() {
      useUserStore().closeModal("find");
    },
  },
};
</script>
  
  <style scoped>
@import "@/assets/css/user/login.css";
</style>