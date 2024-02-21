<template>
  <div class="container">
    <div class="box">
      <!-- 회원정보 수정 -->
      <router-link to="/user/info" class="router-link"
        >회원정보 수정</router-link
      >

      <!-- 비밀번호 변경 -->
      <router-link to="/user/password" class="router-link"
        >비밀번호 변경</router-link
      >

      <!-- 전적 조회 -->
      <router-link to="/user/records" class="router-link"
        >전적 조회</router-link
      >

      <transition name="fade">
        <router-view />
      </transition>
    </div>
  </div>
</template>

<script>
export default {
};
</script>

<style scoped>
@import url("../../assets/css/profile/profile.css");
</style>
