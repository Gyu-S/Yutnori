<template>
  <!-- 탈퇴 모달 창 -->
  <div id="drop-out" class="modal">
    <div class="modal-content">
      <img src="@/assets/img/exclamation.png" alt="exclamation" class="exclamation">
      <h4 class="nickname-text">탈퇴 하시겠습니까?</h4>
      <button @click="confirmDropOut" class="ok">확인</button>
      <button @click="closeModal('Drop')" class="cancel">취소</button>
    </div>
  </div>

</template>
  
<script>
import axios from 'axios'
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


    // 탈퇴 확인 버튼 눌렀을 때 로그인 풀리는 로직
    async confirmDropOut() {
      try {
        // 서버로 로그아웃 요청 보내기
        await axios.post('/api/logout');

        // 로그아웃이 성공하면 모달 닫기
        this.closeModal('Drop');
        // ... 추가적인 로직 수행
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>
  
<style scoped>
@import "@/assets/css/profile/changeNick.css";
</style>