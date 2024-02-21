<template>
  <div :style="horseStyle">
    <button
      @mouseover="MouseOver"
      @mouseleave="MouseLeave"
      :style="horseBtnStyle"
      @click="selectHorse"
    >
      <img :src="imgSrc" :style="horseImgStyle" />
    </button>
    <div
      class="horse-info-container"
      @mouseover="MouseOver"
      @mouseleave="MouseLeave"
      v-if="isMouseOver"
      :style="horseModalStyle"
    >
      <span class="horse-info-name">{{ horse.name }}</span>
      <div class="horse-info-hr"></div>
      <div class="horse-info-div">
        <span class="horse-info-title"
          >&nbsp;&nbsp;&nbsp;시간&nbsp;&nbsp;&nbsp;:</span
        >
        <span :style="horseInfoSize(horse.time)"
          >&nbsp;&nbsp;{{ horse.time }}</span
        >
      </div>
      <div class="horse-info-div">
        <span class="horse-info-title"
          >&nbsp;&nbsp;&nbsp;장소&nbsp;&nbsp;&nbsp;:</span
        >
        <span :style="horseInfoSize(horse.place)"
          >&nbsp;&nbsp;{{ horse.place }}</span
        >
      </div>
      <div class="horse-info-div">
        <span class="horse-info-title"
          >&nbsp;&nbsp;&nbsp;동료&nbsp;&nbsp;&nbsp;:</span
        >
        <span :style="horseInfoSize(horse.contactor)"
          >&nbsp;&nbsp;{{ horse.contactor }}</span
        >
      </div>
      <div class="horse-info-div">
        <span class="horse-info-title"
          >&nbsp;&nbsp;&nbsp;흉터&nbsp;&nbsp;&nbsp;:</span
        >
        <span :style="horseInfoSize(horse.scal)"
          >&nbsp;&nbsp;{{ horse.scal }}</span
        >
      </div>
      <div class="horse-info-div">
        <span class="horse-info-title"
          >&nbsp;&nbsp;&nbsp;물건&nbsp;&nbsp;&nbsp;:</span
        >
        <span :style="horseInfoSize(horse.stuff)"
          >&nbsp;&nbsp;{{ horse.stuff }}</span
        >
      </div>
    </div>
  </div>
</template>

<script>
import { useGameStore } from "@/store/gameStore";

export default {
  props: {
    horse: Object,
    index: Number,
  },
  data() {
    return {
      imgSrc: "",
      isMouseOver: false, // 마우스 오버
    };
  },
  // watch:{
  //   'horse':{
  //     deep: true,
  //     handler(){
  //       this.loadImage();
  //     }
  //   }
  // },
  computed: {
    // 말 스타일
    horseStyle() {
      // 피니아 가져오기
      const gameStore = useGameStore();
      const horsesIndex = gameStore.horsesIndex;

      // 기본 스타일 설정.
      let styles = {
        position: "absolute",
        zIndex: "1000",
        transition: "all 0.5s",
      };

      // 말이 대기 상태이면.
      if (this.horse.status === "wait") {
        // 홍팀
        if (this.horse.team == 1) {
          // 앞에 대기 중인 말이 몇개 인지 체크
          const temp = this.horse.id - this.horse.check;
          let sub = 0;
          if (temp > 1) {
            sub = temp - 1;
          }

          // 위치를 미리 저장한 배열에서 가져온다.
          styles.bottom = horsesIndex[0][this.horse.id - 1 - sub].bottom;
          styles.left = horsesIndex[0][this.horse.id - 1 - sub].left;
        }
        // 청팀
        else {
          // 앞에 대기 중인 말이 몇개 인지 체크
          const temp = this.horse.id - this.horse.check;
          let sub = 0;
          if (temp > 1) {
            sub = temp - 1;
          }

          // 위치를 미리 저장한 배열에서 가져온다.
          styles.bottom = horsesIndex[1][this.horse.id - 1 - sub].bottom;
          styles.left = horsesIndex[1][this.horse.id - 1 - sub].left;
        }
      }
      // 말이 이동 중이면.
      else if (this.horse.status === "ing") {
        const tileHorse = gameStore.tileHorse;
        const tiles = gameStore.tiles;
        // 같이 이동하는 말의 개수가 2개 이상이면 위치를 수정.
        const len = tiles[this.horse.index].horse.length;
        if (len > 1) {
          let bot = tileHorse[this.horse.index].bottom;
          let rig = tileHorse[this.horse.index].right;

          // 각각의 위치를 바꿔준다.
          switch (len) {
            case 2:
              if (tiles[this.horse.index].horse[0].id === this.horse.id) {
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              }
              styles.bottom = bot;
              styles.right = rig;
              break;
            case 3:
              if (tiles[this.horse.index].horse[0].id === this.horse.id) {
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              } else if (
                tiles[this.horse.index].horse[2].id === this.horse.id
              ) {
                bot = parseInt(bot.match(/\d+/g), 10) - 15 + "px";
                rig = parseInt(rig.match(/\d+/g), 10) + 15 + "px";
              }
              styles.bottom = bot;
              styles.right = rig;
              break;
            case 4:
              if (tiles[this.horse.index].horse[1].id === this.horse.id) {
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              } else if (
                tiles[this.horse.index].horse[2].id === this.horse.id
              ) {
                bot = parseInt(bot.match(/\d+/g), 10) - 15 + "px";
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              } else if (
                tiles[this.horse.index].horse[3].id === this.horse.id
              ) {
                bot = parseInt(bot.match(/\d+/g), 10) - 15 + "px";
              }
              styles.bottom = bot;
              styles.right = rig;
              break;
            case 5:
              if (tiles[this.horse.index].horse[4].id === this.horse.id) {
                rig = parseInt(rig.match(/\d+/g), 10) - 5 + "px";
              } else if (
                tiles[this.horse.index].horse[1].id === this.horse.id
              ) {
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              } else if (
                tiles[this.horse.index].horse[2].id === this.horse.id
              ) {
                bot = parseInt(bot.match(/\d+/g), 10) - 15 + "px";
                rig = parseInt(rig.match(/\d+/g), 10) + 25 + "px";
              } else if (
                tiles[this.horse.index].horse[0].id === this.horse.id
              ) {
                rig = parseInt(rig.match(/\d+/g), 10) - 5 + "px";
                bot = parseInt(bot.match(/\d+/g), 10) - 15 + "px";
              } else if (
                tiles[this.horse.index].horse[3].id === this.horse.id
              ) {
                rig = parseInt(rig.match(/\d+/g), 10) + 10 + "px";
                bot = parseInt(bot.match(/\d+/g), 10) - 7 + "px";
              }
              styles.bottom = bot;
              styles.right = rig;
              break;
          }
        }
        // 하나만 움직이면 변화없다.
        else {
          styles.bottom = tileHorse[this.horse.index].bottom;
          styles.right = tileHorse[this.horse.index].right;
        }
      }
      // 말이 들어 왔다면.
      else {
        if(this.horse.kill){
          styles.background = "red";
          styles.opacity = "0.5";
        }
        // 홍팀이면
        if (this.horse.team === 1) {
          styles.bottom = horsesIndex[2][this.horse.endOrder].bottom;
          styles.left = horsesIndex[2][this.horse.endOrder].left;
        }
        // 청팀이면
        else {
          styles.bottom = horsesIndex[3][this.horse.endOrder].bottom;
          styles.left = horsesIndex[3][this.horse.endOrder].left;
        }
      }
      // console.log(styles);
      return styles;
    },

    // 말 버튼 스타일
    horseBtnStyle() {
      let styles = {
        background: "none",
        border: "none",
        padding: "0",
        margin: "0",
        cursor: "pointer",
      };

      return styles;
    },

    // 말 정보 모달 스타일
    horseModalStyle() {
      let styles = {
        position: "fixed",
        zIndex: "1000",
        top: "500px",
        width: "150px",
        height: "200px",
        borderRadius: "10px",
      };
      if (this.horse.team === 1) {
        styles.left = "200px";
        styles.backgroundColor = "rgba(255, 2, 2, 0.5)";
      } else {
        styles.right = "200px";
        styles.backgroundColor = "rgba(36, 2, 255, 0.5)";
      }
      return styles;
    },

    // 말 이미지 스타일
    horseImgStyle() {
      let styles = {
        width: "50px",
        height: "50px",
        borderRadius: "50%", // 원 모양 만들기
      };
      const gameStore = useGameStore();
      const tiles = gameStore.tiles;
      // 그룹에 따라 크기 조정.
      if (tiles[this.horse.index].horse.length > 1) {
        styles.width = "30px";
        styles.height = "30px";
      }

      if (this.isMouseOver) {
        styles.transform = "scale(1.2)"; // 확대
        styles.transition = "transform 0.3s ease";
      }
      if (gameStore.myTeam === this.horse.team && gameStore.isSelect && this.horse.status !== 'end') {
        styles.boxShadow = "0px 8px 5px rgba(255, 0, 0, 0.5)";
      }
      return styles;
    },
  },

  // 이미지를 동적으로 가져오기.
  async mounted() {
    setTimeout(() => {
      this.loadImage();
    }, 4000);
  },
  methods: {
    horseInfoSize(text) {
      let styles = { "font-size": "16px",}; // 기본 스타일
      const len = text.length;

      if (len === 3) {
        styles["font-size"] = "15px"; // 글자 길이가 10 이하일 때 폰트 사이즈
      } else if (len >= 3 && len < 5) {
        styles["font-size"] = "13px"; // 글자 길이가 10 초과 20 이하일 때 폰트 사이즈
      } else if(len >= 5){
        styles["font-size"] = "10px"; // 글자 길이가 20 초과일 때 폰트 사이즈
      }

      return styles;
    },
    MouseOver() {
      this.isMouseOver = true;
    },
    MouseLeave() {
      this.isMouseOver = false;
    },
    selectHorse() {
      if(this.horse.status !== 'end'){
        this.$emit("selectHorse", this.horse);
      }
    },
    async loadImage() {
      try {
        const gameStore = useGameStore();
        console.log("이미지");
        switch (this.horse.name) {
          case "대왕":
            if (
              gameStore.myTeam != this.horse.team &&
              gameStore.mySpyId === this.horse.id
            ) {
              if (this.horse.team === 1) {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/red_king_spy.png")
                ).default;
              } else {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/blue_king_spy.png")
                ).default;
              }
            } else if (this.horse.team === 1) {
              this.imgSrc = (
                await import("@/assets/img/game/horse/red_king.png")
              ).default;
            } else {
              this.imgSrc = (
                await import("@/assets/img/game/horse/blue_king.png")
              ).default;
            }
            break;
          case "기병":
            if (
              gameStore.myTeam != this.horse.team &&
              gameStore.mySpyId === this.horse.id
            ) {
              if (this.horse.team === 1) {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/red_cavalry_spy.png")
                ).default;
              } else {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/blue_cavalry_spy.png")
                ).default;
              }
            } else if (this.horse.team === 1) {
              this.imgSrc = (
                await import("@/assets/img/game/horse/red_cavalry.png")
              ).default;
            } else {
              this.imgSrc = (
                await import("@/assets/img/game/horse/blue_cavalry.png")
              ).default;
            }
            break;
          case "창병":
            if (
              gameStore.myTeam != this.horse.team &&
              gameStore.mySpyId === this.horse.id
            ) {
              if (this.horse.team === 1) {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/red_spearman_spy.png")
                ).default;
              } else {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/blue_spearman_spy.png")
                ).default;
              }
            } else if (this.horse.team === 1) {
              this.imgSrc = (
                await import("@/assets/img/game/horse/red_spearman.png")
              ).default;
            } else {
              this.imgSrc = (
                await import("@/assets/img/game/horse/blue_spearman.png")
              ).default;
            }
            break;
          case "농민":
            if (
              gameStore.myTeam != this.horse.team &&
              gameStore.mySpyId === this.horse.id
            ) {
              if (this.horse.team === 1) {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/red_peasant_spy.png")
                ).default;
              } else {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/blue_peasant_spy.png")
                ).default;
              }
            } else if (this.horse.team === 1) {
              this.imgSrc = (
                await import("@/assets/img/game/horse/red_peasant.png")
              ).default;
            } else {
              this.imgSrc = (
                await import("@/assets/img/game/horse/blue_peasant.png")
              ).default;
            }
            break;
          case "노비":
            if (
              gameStore.myTeam != this.horse.team &&
              gameStore.mySpyId === this.horse.id
            ) {
              if (this.horse.team === 1) {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/red_slave_spy.png")
                ).default;
              } else {
                this.imgSrc = (
                  await import("@/assets/img/game/horse/blue_slave_spy.png")
                ).default;
              }
            } else if (this.horse.team === 1) {
              this.imgSrc = (
                await import("@/assets/img/game/horse/red_slave.png")
              ).default;
            } else {
              this.imgSrc = (
                await import("@/assets/img/game/horse/blue_slave.png")
              ).default;
            }
            break;
          default:
            this.imgSrc = "/path/to/default-image.png"; // 기본 이미지 경로
            break;
        }
      } catch (error) {
        console.error("Error loading image:", error);
        this.imgSrc = "/path/to/default-image.png"; // 오류 발생 시 기본 이미지 경로
      }
    },
  },
};
</script>

<style>
@import "@/assets/css/game/item/gameHorse.css";
</style>
