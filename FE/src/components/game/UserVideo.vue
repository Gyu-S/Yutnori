<template>
  <div id="my-container">
    <ov-video
      class="my-video"
      v-if="streamManager"
      :stream-manager="streamManager"
    />
    <!-- <button class="mission-btn" @click="toggleMission">미션</button> -->
  </div>

  <div class="mission-modal">
    <jewelModal v-if="showMissionModal[0]" />
    <cakeModal v-if="showMissionModal[1]" />
    <chamModal v-if="showMissionModal[2]" />
    <flyCatchModal v-if="showMissionModal[3]" />
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";
import jewelModal from "@/components/game/minigame/Mineral.vue";
import cakeModal from "@/components/game/minigame/Cake.vue";
import chamModal from "@/components/game/minigame/Cham.vue";
import flyCatchModal from "@/components/game/minigame/FlyCatch.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
    jewelModal,
    cakeModal,
    chamModal,
    flyCatchModal,
  },

  props: {
    streamManager: Object,
  },

  data() {
    return {
      missionModalNum: -1,
      showMissionModal: [false, false, false, false],
    };
  },

  methods: {
    // 임시 미션 모달 토글
    toggleMission() {
      if (this.missionModalNum === -1) {
        this.missionModalNum = Math.floor(Math.random() * 4);
      } else {
        // 현재 모달의 상태 토글
        this.showMissionModal[this.missionModalNum] =
          !this.showMissionModal[this.missionModalNum];

        // 다른 모달을 표시할 준비가 되면 missionModalNum을 다시 -1로 설정
        if (this.showMissionModal[this.missionModalNum]) {
          this.missionModalNum = -1;
        }
      }
    },
  },
};
</script>

<style scoped>
@import url("@/assets/css/openvidu/userVideo.css");
</style>
