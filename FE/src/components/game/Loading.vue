<template>
  <div class="modal">
    <div class="modal-content">
      <h2 class="title">로딩중입니다... {{ loadingPercent }}%</h2>
    </div>
  </div>
</template>

<script>
import { library } from "@fortawesome/fontawesome-svg-core";
import { faX } from "@fortawesome/free-solid-svg-icons";
library.add(faX);

export default {
  data() {
    return {
      loadingPercent: 0, // 로딩 상태를 나타내는 변수
      intervalId: null, // setInterval 메서드의 반환값을 저장할 변수
    };
  },
  methods: {
    startLoadingAnimation() {
      // 각 시간에 맞는 로딩 퍼센트 설정
      setTimeout(() => {
        this.loadingPercent = 10;
      }, 200);
      setTimeout(() => {
        this.loadingPercent = 12;
      }, 500);
      setTimeout(() => {
        this.loadingPercent = 24;
      }, 1000);
      setTimeout(() => {
        this.loadingPercent = 25;
      }, 1200);
      setTimeout(() => {
        this.loadingPercent = 31;
      }, 1400);
      setTimeout(() => {
        this.loadingPercent = 35;
      }, 1600);
      setTimeout(() => {
        this.loadingPercent = 37;
      }, 1800);
      setTimeout(() => {
        this.loadingPercent = 45;
      }, 2000);
      setTimeout(() => {
        this.loadingPercent = 52;
      }, 2200);
      setTimeout(() => {
        this.loadingPercent = 60;
      }, 2500);
      setTimeout(() => {
        this.loadingPercent = 66;
      }, 2800);
      setTimeout(() => {
        this.loadingPercent = 72;
      }, 3000);
      setTimeout(() => {
        this.loadingPercent = 86;
      }, 3500);
      setTimeout(() => {
        this.loadingPercent = 88;
      }, 3900);
      setTimeout(() => {
        this.loadingPercent = 90;
      }, 4300);
      setTimeout(() => {
        this.loadingPercent = 92;
      }, 4700);
      setTimeout(() => {
        this.loadingPercent = 97;
      }, 5000);
      setTimeout(() => {
        this.loadingPercent = 100;
        this.$emit("close-start-modal", "start");
      }, 5300);
    },
  },
  mounted() {
    this.startLoadingAnimation(); // 컴포넌트가 마운트되면 로딩 애니메이션 시작
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
  /* 애니메이션 설정 */
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
  color: white;
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
