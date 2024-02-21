<template>
  <!-- 닉네임 변경 모달 창 -->
  <div id="changeNicknameModal" class="modal">
    <div class="modal-content">
      <input
        type="text"
        placeholder="|"
        v-model="nickName"
        :class="{ 'shake-animation': showError }"
      />
      <div v-if="!isValidNickname" class="error-msg">{{ errorMsg }}</div>
      <h4 class="nickname-text">변경할 닉네임을 입력하시오</h4>
      <button @click="closeModal" class="modal-change-nickname-btn">
        취소
      </button>
      <button
        @click="nicknameChange"
        class="modal-change-nickname-btn margin-left"
      >
        확인
      </button>
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
      errorMsg: "",
    };
  },
  data() {
    return {
      nickName: "",
      showError: false,
    };
  },
  methods: {
    nicknameChange() {
      if (this.nickName === "" || !this.isValidNickname) {
        this.showError = true;

        setTimeout(() => {
          this.showError = false;
        }, 500);
        return;
      }
      const userStore = useUserStore();
      userStore.changeNick(this.nickName);
      this.$emit("close");
    },
  },
  computed: {
    // 닉네임 유효성 검사
    isValidNickname() {
      let flag = this.nickName.length > 2 || this.nickName === "";
      if (flag && this.nickName !== "") {
        const userStore = useUserStore();
        userStore.nickCheck(this.nickName);
        if (!userStore.isNickCheck) {
          flag = false;
          this.errorMsg = "닉네임이 중복되었습니다.";
        }
      } else {
        this.errorMsg = "닉네임을 3글자 이상 입력해주세요.";
      }
      return flag;
    },
    // 창 닫기
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
@import "@/assets/css/profile/changeNick.css";
</style>