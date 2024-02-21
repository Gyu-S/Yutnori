<template>
  <div id="liveView" class="videoView">
    <div class="media-container">
      <video 
      ref="webcam" 
      class="media"
      id="webcam"  
      autoplay 
      playsinline
      ></video>
      <canvas 
      ref="faceCanvas" 
      class="media" 
      id="faceCanvas" 
      style="position: absolute; left: auto; top: 0px;"
      ></canvas>
      <canvas 
      ref="cakeCanvas" 
      class="media" 
      id="cakeCanvas" 
      style="position: absolute; left: auto; top: 0px;"
      ></canvas>
    </div>
    <!-- 시작 전 타이머 -->
    <div v-if="countdown > 0" class="start-timer">
      {{ countdown }}
    </div>
    <!-- 게임 타이머 -->
    <div v-if="showGameCount" class="game-timer">
      <h1>Timer : {{ gameTimer }}</h1>
    </div>
    <!-- 남은 케이크 개수 -->
    <div class="target-count">
      남은 케이크 개수 : {{ targetCount }}
    </div>
    <!-- 게임 결과 -->
    <div class="game-result" v-if="gameResult">
      <div v-if="victory">게임 승리!</div>
      <div v-else>게임 실패</div>
    </div>
  </div>
</template>

<script>
import vision from "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.3";
const { FaceLandmarker, FilesetResolver, DrawingUtils } = vision;
import cakeImage from '@/assets/img/mission/cake.png';
export default {
  data() {
    return {
      webcamRunning: false,
      faceLandmarker: null,
      blendShapes: [],
      webcamButtonText: '웹캠 활성화',
      lastVideoTime: -1,
      results: null,

      // 케이크
      cakeImg: new Image(),
      cakes: [],
      cakeDropInterval: null,
      cakeDropping: false,
      targetCount: 5,

      // 카운트다운
      countdown: 3,
      gameTimer: 10,


      // 게임 결과
      gameResult: false,
      showGameCount: false,
      victory: false

      
    };
  },

  async created() {
    await this.createFaceLandmarker();
  },

  async mounted() {
    this.cakeImg.src = cakeImage
    window.addEventListener('resize', () => this.adjustCanvasSizeToVideo())
  },

  methods: {
    // 비디오 사이즈에 캔버스 사이즈 맞추기
    adjustCanvasSizeToVideo() {
      const webcam = this.$refs.webcam
      const faceCanvas = this.$refs.faceCanvas
      const cakeCanvas = this.$refs.cakeCanvas
      if (webcam && faceCanvas && cakeCanvas) {
        faceCanvas.width = webcam.offsetWidth
        faceCanvas.height = webcam.offsetHeight
        cakeCanvas.width = webcam.offsetWidth
        cakeCanvas.height = webcam.offsetHeight
      }
    },


    // 모션 인식 라이브러리 호출
    async createFaceLandmarker() {
      const filesetResolver = await FilesetResolver.forVisionTasks(
        "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.3/wasm"
      );
      this.faceLandmarker = await FaceLandmarker.createFromOptions(filesetResolver, {
        baseOptions: {
          modelAssetPath: `/models/face_landmarker.task`,
          delegate: "GPU"
        },
        outputFaceBlendshapes: true,
        runningMode: "VIDEO",
        numFaces: 1
      });
      const webcam = this.$refs.webcam
      await webcam.addEventListener('loadedmetadata', () => {
        this.adjustCanvasSizeToVideo()
      })
      await this.enableCam()
    },


    // 캠에 접근
    async enableCam() {
      if (!this.faceLandmarker) {
        console.log("faceLandmarker가 아직 로드되지 않았습니다.");
        return;
      }
      this.webcamRunning = true
      const webcam = this.$refs.webcam
      const constraints = {
        video: true, 
      };
      try {
        const stream = await navigator.mediaDevices.getUserMedia(constraints)
        webcam.srcObject = stream
        webcam.addEventListener("loadeddata", () => {
          this.predictWebcam()
          this.startCountdown()
        });
      } catch (error) {
        console.log('에러입니다.', error)
      }
    },


    // 숫자 카운트 다운
    startCountdown() {
      const countdownInterval = setInterval(() => {
        this.countdown -= 1;
        if (this.countdown <= 0) {
          clearInterval(countdownInterval);
          // 3초 카운트다운 후에 케이크 떨어짐
          this.startGameTimer()
          this.startCakeDropping()
        }
      }, 1000);
    },


    // 게임 시작
    startGameTimer() {
      this.showGameCount = true
      const gameInterval = setInterval(() => {
        --this.gameTimer
        if (this.gameTimer === 0) {
          clearInterval(gameInterval)
          this.stopWebcam()
          this.gameResult = true
          this.$emit('endMinigame', this.victory)
        }
      }, 1000)
    },


    // 얼굴 모션 인식
    async predictWebcam() {
      if (!this.webcamRunning || !this.faceLandmarker) return;
      const webcam = this.$refs.webcam;
      const faceCanvas = this.$refs.faceCanvas;
      const ctx = faceCanvas.getContext("2d");
      ctx.clearRect(0, 0, faceCanvas.width, faceCanvas.height)
      const drawingUtils = new DrawingUtils(ctx);

      if (this.lastVideoTime !== webcam.currentTime) {
        this.lastVideoTime = webcam.currentTime;
        this.results = await this.faceLandmarker.detectForVideo(webcam, performance.now());
      }
      if (this.results && this.results.faceLandmarks) {
        for (const landmarks of this.results.faceLandmarks) {
          // 입술 감지
          drawingUtils.drawConnectors(
            landmarks,
            FaceLandmarker.FACE_LANDMARKS_LIPS,
            { color: "red" }
          );
          this.detectCakeCollision(landmarks)
        }
      }
      if (this.webcamRunning === true) {
        window.requestAnimationFrame(this.predictWebcam);
      }
    },


    // 케이크 충돌 감지
      detectCakeCollision(landmarks) {
        const webcam = this.$refs.webcam
        this.cakes.forEach((cake, index) => {
          if (
            cake.x >= landmarks[57].x * webcam.offsetWidth &&
            cake.x <= landmarks[287].x * webcam.offsetWidth &&
            cake.y >= landmarks[164].y * webcam.offsetHeight &&
            cake.y <= landmarks[18].y * webcam.offsetHeight
          ) {
            this.eatCake(index)
          }
        })
    },


    // 케이크 먹은 경우 처리
    eatCake(index) {
      this.cakes.splice(index, 1)
      --this.targetCount
      
      if (this.targetCount <= 0) {
        this.stopWebcam()
        this.victory = true
        this.gameResult = true
      }
    },


    // 케이크 떨어뜨리기
    startCakeDropping() {
      this.cakes = []
      this.cakeDropping = true
      const cakeCanvas = this.$refs.cakeCanvas;

      if (this.cakeDropInterval) clearInterval(this.cakeDropInterval)
      this.cakeDropInterval = setInterval(() => {
        this.cakes.push({
          x: Math.random() * (cakeCanvas.width - 50),
          y: -50
        })
      }, 500)

      const cakeDrop = () => {
        if (!this.cakeDropping) {
          clearInterval(this.cakeDropInterval)
          return
        }
        this.drawCake()
        requestAnimationFrame(cakeDrop)
      }
      cakeDrop()
    },


    // 케이크 그리기
    drawCake() {
      const cakeCanvas = this.$refs.cakeCanvas
      const ctx = cakeCanvas.getContext('2d')
      ctx.clearRect(0, 0, cakeCanvas.width, cakeCanvas.height)
      this.cakes.forEach(cake => {
        cake.y += 6;
        ctx.drawImage(this.cakeImg, cake.x, cake.y, 20, 20)
        // 화면 밖으로 나간 케이크 제거
        if (cake.y > cakeCanvas.height) {
          this.cakes.shift()
        }
      })
    },


    // 캠 중단
    stopWebcam() {
      this.webcamRunning = false
      // 입술 모션 중단
      const faceCanvas = this.$refs.faceCanvas;
      const ctx = faceCanvas.getContext("2d");
      ctx.clearRect(0, 0, faceCanvas.width, faceCanvas.height)
      // 케이크 떨어지는 것 중단
      this.stopCakeDropping()
    },


    // 케이크 떨어뜨리기 중단
    stopCakeDropping() {
      this.cakeDropping = false
      if (this.cakeDropInterval) {
        clearInterval(this.cakeDropInterval)
        this.cakeDropInterval = null
      }
      const cakeCanvas = this.$refs.cakeCanvas
      const ctx = cakeCanvas.getContext('2d')
      ctx.clearRect(0, 0, cakeCanvas.width, cakeCanvas.height)
    },

  },

  // 끝내면 다 버리기
  beforeDestroy() {
    window.removeEventListener('resize', this.adjustCanvasSizeToWindow);
  },
};
</script>

<style scoped>
@import url('@/assets/css/game/minigame/cake.css');
</style>
