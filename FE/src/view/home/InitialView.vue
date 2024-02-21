<template>
  <img
    src="@/assets/logo/TitleText.png"
    class="game-image"
    :class="{ animated: isAnimated }"
  />

  <div>
    <!-- 로그인 및 회원 가입 -->
    <div class="button-container">
      <button class="login-btn" @click="openModal('login')">Login</button>
      <button class="join-btn" @click="openModal('join')">Join</button>

      <!-- 로그인 모달 -->
      <transition name="fade">
        <LoginModal v-if="showLoginModal" />
      </transition>

      <!-- 회원 가입 모달 -->
      <transition name="fade">
        <JoinModal v-if="showJoinModal" />
      </transition>
      <!-- 비밀번호 찾기 모달 -->
      <transition name="fade">
        <FindModal v-if="showFindModal" />
      </transition>
    </div>
  </div>
</template>

<script>
import LoginModal from "@/components/user/LoginModal.vue";
import JoinModal from "@/components/user/JoinModal.vue";
import FindModal from '@/components/user/FindModal.vue';
import { useUserStore } from "@/store/userStore";
import { storeToRefs } from "pinia";

export default {
  components: {
    LoginModal,
    JoinModal,
    FindModal,
  },
  data() {
    return {
      isAnimated: true,
    };
  },
  setup() {
    const store = useUserStore();
    const {  showLoginModal, showJoinModal,showFindModal } = storeToRefs(store);

    return {
      showLoginModal,
      showJoinModal,
      showFindModal,
      openModal: store.openModal,
      closeModal: store.closeModal,
    };
  },
};
</script>

<style scoped>
@import url("../../assets/css/home/initial.css");
</style>