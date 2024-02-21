<template>
  <div class="mini-main">
    <MiniCard v-if="isShowCardSelect && isThrowYut" @selectCard="selectCard" />
    <Cake v-if="isShowCake && isThrowYut" @endMinigame="endMinigame"/>
    <cham v-if="isShowCham && isThrowYut" @endMinigame="endMinigame"/>
    <FlyCatch v-if="isShowFlyCatch && isThrowYut" @endMinigame="endMinigame"/>
    <!-- <Mineral v-if="isShowMineral && isThrowYut" @endMinigame="endMinigame"/> -->
    <div class="mini-wait" v-if="!isThrowYut">
      {{ nickName }}님이 미션중입니다.<br />
      잠시만 기다려 주세요
    </div>
  </div>
</template>

<script>
import MiniCard from "./item/MiniCard.vue";
import Cake from "./minigame/Cake.vue";
import Cham from "./minigame/Cham.vue";
import FlyCatch from "./minigame/FlyCatch.vue";
// import Mineral from "./minigame/Mineral.vue";

import { useMiniGameStore } from "@/store/miniGameStore";
import { useGameStore } from "@/store/gameStore";
import { useUserStore } from "@/store/userStore";

import { socketSend } from "@/util/socket.js";

export default {
  data() {
    return {
      isShowCake: false,
      isShowCham: false,
      isShowFlyCatch: false,
      isShowMineral: false,
    };
  },
  components: {
    MiniCard,
    Cake,
    Cham,
    FlyCatch,
    // Mineral,
  },
  computed: {
    isShowCardSelect() {
      const miniStore = useMiniGameStore();
      return miniStore.isShowCardSelect;
    },
    isThrowYut() {
      const gameStore = useGameStore();
      return gameStore.isThrowYut;
    },
    nickName() {
      const gameStore = useGameStore();
      if (!gameStore.teamTurn) return gameStore.redTurnName;
      else return gameStore.blueTurnName;
    },
  },
  methods: {
    selectCard(selectedCard) {
      const miniStore = useMiniGameStore();
      miniStore.isShowCardSelect = false;
      switch (selectedCard.value) {
        case 1:
          this.isShowFlyCatch = true;
          break;
        case 2:
          this.isShowCake = true;
          break;
        case 3:
          this.isShowCham = true;
          break;
      }
    },
    // 게임 결과 전송.
    endMinigame(res){
      if (this.isThrowYut) {
          const msg = {
            email : useUserStore().userInfo.email,
            result : res,
          }
          socketSend(`/pub/game/${useUserStore().currentRoomInfo.roomCode}/mini-game-finish`, msg);
        }
    }
  },
};
</script>

<style>
@import "@/assets/css/game/miniGame.css";
</style>