<template>
  <div class="game-spy-select-main">
    <div v-if="isThrowYut">
      <span class="game-spy-select-title">힌트를 얻고 싶은 말을 선택하시오.</span>
      <div class="game-spy-select-container">
        <div
          class="game-spy-select-item"
          v-for="horse in horses"
          :key="horse.id"
          @click="selectHorse(horse)"
        >
          <div class="game-spy-select-item-container">
            <img
              class="game-spy-select-item-img"
              :class="{
                'game-spy-select-item-img-selected':
                  selectedHorseId === horse.id,
              }"
              :src="horse.imgSrc"
              :alt="horse.name"
            />
            <span>{{ horse.name }}</span>
          </div>
        </div>
      </div>
      <button class="game-spy-select-btn" @click="clickHorse">선택</button>
    </div>
    <div v-else>
      <span class="game-spy-select-title"
        >{{ nickName }}님이 힌트를 얻고 싶은 말을 선택중입니다.</span
      >
      <br /><br />
      <span class="game-spy-select-text">잠시만 기다려주세요....</span>
    </div>
  </div>
</template>
  
  <script>
import { useGameStore } from "@/store/gameStore";
import { useUserStore } from "@/store/userStore";
import { socketSend } from "@/util/socket.js";

export default {
  data() {
    return {
      horses: [],
      selectedHorseId: null,
    };
  },
  computed: {
    teamTurn() {
      const gameStore = useGameStore();
      return gameStore.teamTurn;
    },
    // 턴 체크.
    isThrowYut() {
      const gameStore = useGameStore();
      return gameStore.isThrowYut;
    },
    nickName() {
      const gameStore = useGameStore();
      if (!this.teamTurn) return gameStore.redTurnName;
      else return gameStore.blueTurnName;
    },
    // 방 코드
    roomCode() {
      const userStore = useUserStore();
      return userStore.currentRoomInfo.roomCode;
      // return "720ca5";
    },
  },
  watch: {
    // teamTurn 변경을 감시합니다.
    async teamTurn() {
      await this.loadImages();
    },
  },
  async created() {
    await this.loadImages();
  },
  methods: {
    clickHorse() {
      const gameStore = useGameStore();
      if (this.selectedHorseId === null) {
        alert("말을 선택해주세요.");
        return;
      }
      const msg = {
        team : gameStore.myTeam,
        unitId  : this.selectedHorseId ,
      }
      socketSend(`/pub/game/${this.roomCode}/hint`, msg);
    },
    selectHorse(horse) {
      this.selectedHorse = horse;
      this.selectedHorseId = horse.id;
    },
    async loadImages() {
      const gameStore = useGameStore();
      const horses = gameStore.teamTurn
        ? gameStore.blueHorses
        : gameStore.redHorses;
      for (const horse of horses) {
        horse.imgSrc = await this.getImageSrc(horse);
      }
      this.horses = horses;
    },
    async getImageSrc(horse) {
      try {
        switch (horse.name) {
          case "대왕":
            return horse.team === 1
              ? (await import("@/assets/img/game/horse/red_king.png")).default
              : (await import("@/assets/img/game/horse/blue_king.png")).default;
          case "기병":
            return horse.team === 1
              ? (await import("@/assets/img/game/horse/red_cavalry.png"))
                  .default
              : (await import("@/assets/img/game/horse/blue_cavalry.png"))
                  .default;
          case "창병":
            return horse.team === 1
              ? (await import("@/assets/img/game/horse/red_spearman.png"))
                  .default
              : (await import("@/assets/img/game/horse/blue_spearman.png"))
                  .default;
          case "농민":
            return horse.team === 1
              ? (await import("@/assets/img/game/horse/red_peasant.png"))
                  .default
              : (await import("@/assets/img/game/horse/blue_peasant.png"))
                  .default;
          case "노비":
            return horse.team === 1
              ? (await import("@/assets/img/game/horse/red_slave.png")).default
              : (await import("@/assets/img/game/horse/blue_slave.png"))
                  .default;
          default:
            return "/path/to/default-image.png"; // 기본 이미지 경로
        }
      } catch (error) {
        console.error("Error loading image:", error);
        return "/path/to/default-image.png"; // 오류 발생 시 기본 이미지 경로
      }
    },
  },
};
</script>
  
  <style>
@import "@/assets/css/game/item/gameSpySelect.css";
</style>
  