<template>
  <!-- 닉네임 변경 모달 창 -->
  <div id="changePasswordModal" class="modal">
    <div class="modal-content">
      <img src="@/assets/img/check.png" alt="check" class="check">
      <h4 class="password-text">비밀번호 변경에 성공했습니다.</h4>
      <button @click="closeModal('password')" class="modal-change-password-btn">확인</button>
    </div>
  </div>

</template>
  
<script>
import { useUserStore } from "@/store/userStore";

export default {
  setup() {
    const store = useUserStore();

    return {
      closeModal: store.closeModal,
    };
  },
  data() {
    return {
      nickname: '',
    };
  },
  methods: {
    nicknameChangeTest() {
      this.$emit("nicknameChangeTest")
    },
  }
}
</script>
  
<style scoped>
@import "@/assets/css/profile/successChangePassword.css";
</style>