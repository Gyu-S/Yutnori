<template>
  <div class="end-main">
    <div class="end-container"></div>
    <div :style="{ color: text === '승리' ? 'blue' : 'red' }" class="end-text">{{text}}</div>
    <button class="end-btn" @click="sendToParent">나가기</button>
  </div>
</template>

<script>
import { useUserStore } from "@/store/userStore";
import { useGameStore } from "@/store/gameStore";

export default {
  props: {
    winMessage: Number,
  },
  computed: {
    text() {
      if(this.winMessage === useGameStore().myTeam)
        return "승리";
      else
        return "패배";
    },
  },
  methods: {
    sendToParent() {
      this.$emit("closeModal");
      useUserStore().showModalSide = true;
    },
  },
};
</script>

<style>
@import "@/assets/css/game/gameEnd.css";
</style>