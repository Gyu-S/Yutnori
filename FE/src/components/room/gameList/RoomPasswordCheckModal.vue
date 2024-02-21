<template>
  <!-- 비공개방 비밀번호 체크 모달 창 -->
  <div id="checkRoomPasswordModal" class="modal">
    <div class="modal-content">
      <input type="password" placeholder="|" v-model="roomPassword" />
      <h4 class="password-text">비밀번호를 입력하세요</h4>
      <div class="btn">
        <button @click="sendPassword('roomPasswordCheck')" class="confirm-btn">확인</button>
        <button @click="closeModal('roomPasswordCheck')" class="cancel-btn">
          취소
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import { useRoomStore } from '@/store/roomStore';

  export default {
    data() {
      return {
        roomPassword: '',
      };
    },

    methods: {
      sendPassword(modalType){
        useRoomStore().password = this.roomPassword;
        useRoomStore().closeModal(modalType);
      },

      closeModal(modalType) {
        useRoomStore().closeModal(modalType);
      },
    }
  }
</script>

<style scoped>
@import "@/assets/css/room/roomPasswordCheckModal.css";
</style>