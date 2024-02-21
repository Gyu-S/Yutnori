<template>
  <div class="game-modal-main">
    <div class="game-modal-container">
      <span class="game-modal-title">{{ modalTextTitle }}</span>
      <span class="game-modal-text">{{ modalText }}</span>
      <div class="game-modal-btn-container">
        <button v-if="isBtn" class="game-modal-btn-true" @click="trueMethod">
          {{ tureText }}
        </button>
        <button v-if="isBtn" class="game-modal-btn-false" @click="falseMethod">
          {{ falseText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useGameStore } from "@/store/gameStore";
export default {
  props: {
    modalType: Number,
  },
  computed: {
    ticket() {
      const gameStore = useGameStore();
      return gameStore.ticket;
    },
    isBtn(){
      if([1,2,3].includes(this.modalType)){
        return true;
      }else{
        return false;
      }
    },
    modalTextTitle() {
      switch (this.modalType) {
        case 1:
          return "모서리시군요.";
        case 2:
          return "가운데시군요.";
        case 3:
          return `추리권 ${this.ticket}개`;
        case 4:
          return `추리권 사용 선택중입니다...`;
        case 5:
          return `추리권 사용 선택중입니다...`;
      }
    },
    modalText() {
      switch (this.modalType) {
        case 1:
          return "대각선으로 가시겠습니까?";
        case 2:
          return "방향을 선택해 주세요.";
        case 3:
          return "추리 하시겠습니까?";
        case 4:
          return ``;
        case 5:
          return ``;
      }
    },
    tureText() {
      switch (this.modalType) {
        case 1:
          return "네";
        case 2:
          return "왼쪽";
        case 3:
          return "네";
        default:
          return "알 수 없는 모달 타입입니다.";
      }
    },
    falseText() {
      switch (this.modalType) {
        case 1:
          return "아니오";
        case 2:
          return "오른쪽";
        case 3:
          return "아니오";
        default:
          return "알 수 없는 모달 타입입니다.";
      }
    },
  },
  methods: {
    trueMethod() {
      if ([1, 2].includes(this.modalType)) {
        this.$emit("trueMethod");
      } else {
        this.$emit("useTicket");
      }
    },
    falseMethod() {
      if ([1, 2].includes(this.modalType)) {
        this.$emit("falseMethod");
      } else {
        this.$emit("notUseTicket");
      }
    },
  },
};
</script>

<style>
@import "@/assets/css/game/item/gameModal.css";
</style>