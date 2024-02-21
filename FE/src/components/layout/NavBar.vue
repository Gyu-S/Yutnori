<script>
import NavPro from "./NavPro.vue";

// 프로필은 따로 가져오기 위해 선언
export default {
  components: {
    NavPro,
  },

  methods: {
    goToHome() {
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<template>
  <div class="nav-bar">
    <!-- 로고 -->
    <div>
      <img src="../../assets/logo/logo.png" @click="goToHome" class="img-log" />
    </div>
    <!-- 홈, 게임, 게시판 클릭시 화면 이동 -->
    <!-- Named Routes로 변경 -->

    <div class="text-box">
      <router-link
        :to="{ name: 'home' }"
        class="nav-text"
        exact
        active-class="active-link"
        >홈</router-link
      >
      <router-link
        :to="{ name: 'room' }"
        class="nav-text"
        exact
        active-class="active-link"
        >게임</router-link
      >
      <router-link
        :to="{ name: 'user' }"
        class="nav-text"
        exact
        active-class="active-link"
        >내정보</router-link
      >
    </div>
    <!-- 프로필 가져와서 보여주기 -->
    <transition name="fade">
      <div class="pro-box">
        <NavPro />
      </div>
    </transition>
  </div>
</template>

<style scoped>
@import "../../assets/css/layout/navBar.css";
</style>