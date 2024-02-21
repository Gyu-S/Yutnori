<template>
  <div class="user-profile">
    <div class="left-section">
      <img
        src="@/assets/img/profile_picture.png"
        alt="profile-img"
        class="profile-picture"
      />
      <h3>{{ userInfo.nickname }}</h3>
      <button
        @type="submit"
        @click="showUserInfoNick = true"
        class="nickname-change-btn"
      >
        닉네임 변경
      </button>
    </div>

    <div class="right-section">
      <!-- 닉네임 변경 모달 -->
      <transition name="fade">
        <ChangeNicknameModal v-if="showUserInfoNick" @close="handleClose"/>
      </transition>

      <div>
        <span id="title">이메일</span>
        <span id="content">{{ userInfo.email }}</span>
      </div>

      <div>
        <span id="title">비밀번호</span> <span id="content">********</span>
      </div>

      <div>
        <span id="title">생년월일</span>
        <span id="content">{{ userInfo.birthDate }}</span>
      </div>

      <div>
        <span id="title">성별</span>
        <span id="content">{{ userInfo.gender }}성</span>
      </div>
      <button @type="submit" @click="openModal('out')" class="memberout-btn">
        회원 탈퇴
      </button>
    </div>
    <transition>
      <DropoutModal v-if="showDropOutModal" @close="showDropOutModal = false" />
    </transition>
  </div>
</template>

<script>
import ChangeNicknameModal from "@/components/profile/ChangeNicknameModal.vue";
import DropoutModal from "@/components/profile/DropoutModal.vue";
import { useUserStore } from "@/store/userStore";
import { storeToRefs } from "pinia";

export default {
  components: {
    ChangeNicknameModal,
    DropoutModal,
  },
  setup() {
    const store = useUserStore();
    const { showDropOutModal } = storeToRefs(store);

    return {
      showDropOutModal,
      openModal: store.openModal,
    };
  },

  data() {
    return {
      user: {
        nickname: "",
        email: "",
        password: "********",
        birthdate: "",
        gender: "",
      },
      showUserInfoNick: false,
    };
  },

  computed: {
    userInfo() {
      const userInfo = useUserStore().userInfo;
      return userInfo ? userInfo : "";
    },
  },

  methods: {
    // 회원 탈퇴 메서드
    withdraw() {},
    handleClose() {
      this.showUserInfoNick = false; // 모달 닫을 때 상태 변경
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/profile/userModify.css";
</style>