<template>
  <div :style="tileStyle">
    <!-- {{ tile.id }} -->
  </div>
</template>
  
  <script>
import taegeukImage from "@/assets/img/game/taegeuk.png";
import { useGameStore } from "@/store/gameStore";
import missionTile from "@/assets/icon/missionTile.png";

export default {
  props: ["tile"],
  data() {
    return {
      // 이미지 경로를 데이터 프로퍼티로 저장합니다.
      taegeukImageUrl: taegeukImage,
      missionTileUrl: missionTile,
    };
  },
  computed: {
    tileStyle() {
      let styles = {
        position: "absolute",
        width: "50px",
        height: "50px",
        borderRadius: "50%",
        textAlign: "center",
        transform: "rotateX(5deg)",
        boxShadow: `
        0 7px 10px rgba(0, 0, 0, 0.466), /* Edge shadow */
        inset 0 5px 10px rgba(255, 255, 255, 0.862) /* Inner shadow (highlight effect) */
      `,
      };

      // 특정 타일 ID에 대한 크기 변경
      if ([0, 5, 10, 15, 22, 27].includes(this.tile.id)) {
        styles.width = "100px";
        styles.height = "100px";
      }

      console.log("미션 타일 : " + useGameStore().missionTiles);
      if ([22, 27].includes(this.tile.id)) {
        styles.backgroundImage = `url(${this.taegeukImageUrl})`;
        styles.backgroundSize = "cover"; // This will ensure that your image covers the entire tile
        styles.backgroundPosition = "center";
      } else if(useGameStore().missionTiles.includes(this.tile.id)){
        styles.backgroundImage = `url(${this.missionTileUrl})`;
        styles.backgroundSize = "cover"; // This will ensure that your image covers the entire tile
        styles.backgroundPosition = "center";
      }
      else {
        styles.backgroundColor = "#D2691E"; // 기본 색
      }

      if(useGameStore().missionTiles.includes(this.tile.id)){
        styles.backgroundImage = `url(${this.missionTileUrl})`;
      }

      // 오른쪽 0~5
      if (this.tile.position === "right") {
        const bottomValue =
          this.tile.id === 0 ? 0 : 140 + 90 * (this.tile.id - 1);
        const rightValue = this.tile.id === 0 || this.tile.id === 5 ? 0 : 25;
        styles.bottom = bottomValue + "px";
        styles.right = rightValue + "px";
        // 위 6~10
      } else if (this.tile.position === "top") {
        const topValue = this.tile.id === 10 ? 0 : 25;
        const rightValue = 140 + (this.tile.id - 6) * 90;
        styles.top = topValue + "px";
        styles.right = rightValue + "px";
        // 왼쪽 11~15
      } else if (this.tile.position === "left") {
        const topValue = 140 + (this.tile.id - 11) * 90;
        const leftValue = this.tile.id === 15 ? 0 : 25;
        styles.top = topValue + "px";
        styles.left = leftValue + "px";
        // 아래 16~19
      } else if (this.tile.position === "bottom") {
        const bottomValue = 25;
        const leftValue = 140 + (this.tile.id - 16) * 90;
        styles.bottom = bottomValue + "px";
        styles.left = leftValue + "px";
        // 오른쪽 상단 20~21
      } else if (this.tile.position === "top-right") {
        const topValue = 116 + (this.tile.id - 20) * 66;
        const rightValue = 116 + (this.tile.id - 20) * 66;
        styles.top = topValue + "px";
        styles.right = rightValue + "px";
        // 왼쪽 하단 23~24
      } else if (this.tile.position === "bottom-left") {
        const bottomValue = 116 + (this.tile.id === 23 ? 66 : 0);
        const leftValue = 116 + (this.tile.id === 23 ? 66 : 0);
        styles.bottom = bottomValue + "px";
        styles.left = leftValue + "px";
        // 왼쪽 상단 25~26
      } else if (this.tile.position === "top-left") {
        const topValue = 116 + (this.tile.id - 25) * 66;
        const leftValue = 116 + (this.tile.id - 25) * 66;
        styles.top = topValue + "px";
        styles.left = leftValue + "px";
        // 오른쪽 하단 28~29
      } else if (this.tile.position === "bottom-right") {
        const bottomValue = 116 + (this.tile.id === 28 ? 66 : 0);
        const rightValue = 116 + (this.tile.id === 28 ? 66 : 0);
        styles.bottom = bottomValue + "px";
        styles.right = rightValue + "px";
        // 가운데 22,27
      } else {
        styles.top = 250 + "px";
        styles.left = 250 + "px";
      }
      return styles;
    },
  },
};
</script>
  
  <style scoped>
/* @import "@/assets/css/game/gameBoardTile.css"; */
</style>
  