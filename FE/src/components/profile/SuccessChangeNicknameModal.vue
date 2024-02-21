<template>
  <!-- 닉네임 모달창에서 닉네임 변경 후 확인 버튼 누르면 이 화면으로 넘어오게 해야함 ! 아직 안함 -->
  <!-- 닉네임 변경 모달 창 -->
  <div id="changeNicknameModal" class="modal">
    <div class="modal-content">
      <img src="@/assets/img/check.png" alt="check" class="check">
      <h4 class="nickname-text">닉네임 변경에 성공했습니다</h4>
      <button @click="closeModal('Nick')" class="modal-change-nickname-btn">확인</button>
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
    }
  }
}
</script>
  
<style scoped>
@import "@/assets/css/profile/changeNick.css";
</style>