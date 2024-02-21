<script>
import Main from "@/view/Main.vue";
import { initializeSnowAnimation } from "@/animations/snowAnimation";
import { initializeClickSoundEffect } from "@/animations/clickSound";

export default {
  components: {
    Main,
  },

  mounted() {
    // 눈 배경
    initializeSnowAnimation("canv", "./src/assets/img/MainBackground.png");

    // 클릭 사운드
    const clickSound = document.querySelector("#clickSound");

    // 혹시 모를 clickSound를 찾지 못했을 때를 대비.
    if (!clickSound) {
      console.error("Click sound element not found");
      return;
    }

    document.addEventListener("click", function (e) {
      // 버튼, 라우터링크, 입력칸에서 클릭 이벤트가 발생하면 사운드.
      if (
        e.target.matches("a") ||
        e.target.matches("button") ||
        e.target.matches("input")
      ) {
        // 오디오 재생 시도
        clickSound
          .play()
          .catch((error) => console.error("Error playing sound:", error));
      }
    });
  },

  computed: {
    // 사이트 변경될 때마다 배경화면 변경
    backgroundClass() {
      return this.$route.meta.background || "default";
    },
  },
};
</script>

<template>
  <div class="app-container" :class="backgroundClass">
    <Main></Main>
    <!-- 효과음 오디오 요소 추가 -->
    <audio id="clickSound" src="./src/assets/sound/1.mp3"></audio>
    <canvas id="canv"></canvas>
  </div>
</template>
