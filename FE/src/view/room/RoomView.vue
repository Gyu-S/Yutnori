<template>
  <div>
    <transition name="fade"> <router-view /></transition>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
/* 모달창 애니메이션 */
.fade-enter-from {
  opacity: 0;
}
.fade-enter-active {
  transition: all 0.5s;
}
.fade-enter-to {
  opacity: 1;
}
.fade-leave-from {
  opacity: 1;
}
.fade-leave-active {
  transition: all 0.5s;
}
.fade-leave-to {
  opacity: 0;
}
</style>