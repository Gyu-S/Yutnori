<template>
  <div class="password-box">
    <div>
      <label for="current-password" class="change-password"
        >현재 비밀번호
      </label>
      <input
        v-model="currentPassword"
        type="password"
        id="current-password"
        class="change-password-input"
      />
    </div>

    <div>
      <label for="new-password" class="change-password">새 비밀번호 </label>
      <input
        v-model="newPassword"
        type="password"
        id="new-password"
        class="change-password-input"
      />
      <div v-if="newPasswordError" class="error-message">
        새 비밀번호는 영문, 숫자, 특수문자를 조합하여 8자 이상이어야 합니다.
      </div>
    </div>

    <div>
      <label for="checked-new-password" class="change-password"
        >새 비밀번호 확인
      </label>
      <input
        v-model="checkedNewPassword"
        type="password"
        id="checked-new-password"
        class="change-password-input"
      />
      <div v-if="passwordMismatch" class="error-message">
        새 비밀번호와 일치하지 않습니다.
      </div>
    </div>

    <button @type="submit" @click="changePass" class="change-password-btn">
      확인
    </button>
    <SuccessChangePasswordModal v-if="showSuccessPassword" />
  </div>
</template>

<script>
import SuccessChangePasswordModal from "@/components/profile/SuccessChangePasswordModal.vue";
import { useUserStore } from "@/store/userStore";
import { storeToRefs } from "pinia";

export default {
  components: {
    SuccessChangePasswordModal,
  },
  setup() {
    const store = useUserStore();
    const { showSuccessPassword } = storeToRefs(store);

    return {
      showSuccessPassword,
      openModal: store.openModal,
    };
  },
  data() {
    return {
      currentPassword: "",
      newPassword: "",
      checkedNewPassword: "",
      newPasswordError: false,
      passwordMismatch: false,
      showSuccessModal: false,
    };
  },
  methods: {
    async changePass() {
      if (
        this.currentPassword === "" ||
        this.passwordMismatch ||
        this.newPasswordError
      )
        return;

      const param = {
        previousPassword: this.currentPassword,
        nextPassword: this.newPassword,
      };

      const userStore = useUserStore();
      await userStore.changePass(param);

      this.openModal("password");
    },
    // 비밀번호 유효성 검사
    validateNewPassword() {
      const passwordRegex =
        /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      this.newPasswordError = !passwordRegex.test(this.newPassword);
    },

    // 새 비밀번호와 일치하는지 확인
    validatePasswordMatch() {
      this.passwordMismatch = this.newPassword !== this.checkedNewPassword;
    },
  },
  watch: {
    newPassword: "validateNewPassword",
    checkedNewPassword: "validatePasswordMatch",
  },
};
</script>

<style scoped>
@import "../../assets/css/profile/changePassword.css";
</style>