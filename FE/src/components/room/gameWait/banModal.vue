<template>
  <div id="" class="modal">
    <div class="modal-content">
      <h4 class="password-text">추방하시겠습니까?</h4>
      <div class="btn">
        <button @click="banGame()" class="confirm-btn" >확인</button>
        <button @click="$emit('closeBanModal')" class="cancel-btn">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    methods: {
      // 추방 인원 받아서 추방
      banGame() {

      },
    }
  }
</script>

<style scoped>
@import '@/assets/css/room/banModal.css';
</style>