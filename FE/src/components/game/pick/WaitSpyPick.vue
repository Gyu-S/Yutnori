<template>
  <!-- 패치노트 모달창 -->
  <div class="modal">
    <div class="modal-content">
      <!-- <h2 ref="titleElement" class="title">
        우리 팀 리더가 스파이 픽을 하고 있습니다...
      </h2> -->
      <h2 class="title">
        우리 팀 리더가 스파이 픽을 하고 있습니다...
      </h2>
    </div>
  </div>
</template>

<script>
// import { library } from "@fortawesome/fontawesome-svg-core";
// import { faX } from "@fortawesome/free-solid-svg-icons";
// library.add(faX);

export default {
  data() {
    return {
      loadingText: ".",
      loadingPercent: 0, 
      intervalId: null, 
    };
  },
  methods: {
    startLoadingAnimation() {
    //   const dots = [".", "..", "...", "....", "....."];
    //   let index = 0; 
    //   this.intervalId = setInterval(() => {
    //     this.loadingText = dots[index];
    //     index = (index + 1) % dots.length; 
    //     this.$refs.titleElement.innerText =
    //       "우리 팀 리더가 스파이 픽을 하고 있습니다" + this.loadingText;
    //   }, 800);
    //   this.$emit("close-modal", "start");
    },
  },
  mounted() {
    // this.startLoadingAnimation(); 
  },
  beforeDestroy() {
    // clearInterval(this.intervalId);
    // this.intervalId = null;
  },
};
</script>

<style scoped>
/* 모달 애니메이션 */
.modal {
  /* 기본 스타일 유지 */
  border-color: black;
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
  position: fixed;
  left: 0;
  top: 0px;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.879);
  z-index: 3;
}

.modal-content {
  /* 기본 스타일 유지 */
  background-color: #00000035;
  margin: 5% auto;
  padding: 20px;
  border: 2px solid #888;
  border-color: white;
  border-radius: 30px;
  width: 600px;
  text-align: center;
  transform: translateY(-50px);
  transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
}

.close {
  background-color: #ffffff00;
  position: absolute;
  top: 0;
  right: 0;
  padding: 10px;
  margin-right: 10px;
  color: white;
  z-index: 999; /* 높은 값으로 설정 */
}

.title {
  margin-bottom: 20px;
  top: -40px; /* 위로 20픽셀 이동, 필요에 따라 조절 가능 */
  color: rgba(77, 70, 213, 0.833);
  font-family: "Palatino Linotype", "Book Antiqua", palatino, serif; /* Press Start 2P 글꼴 적용 */
}

.modal-cancel-btn {
  margin-left: 10px;
}

/* 취소 버튼 호버 효과 */
.modal-cancel-btn:hover {
  background-color: #f44336;
  color: white;

  /* 트랜지션 효과 */
  transition: background-color 0.3s ease, color 0.3s ease;
}
</style>
