<template>
  <!-- 웹캠 화면 -->
  <div id="liveView" class="videoView">
    <div class="media-container" style="position: relative;">
      <!-- 웹캠 비디오 요소 -->
      <video
        class="media"
        id="webcam"
        ref="webcam"
        autoplay
        playsinline
        @loadedmetadata="onGestureLoaded"
      ></video>
      <!-- 캔버스 -->
      <canvas
        class="media"
        id="jewel-canvas"
        ref="jewel_canvas"
        style="position: absolute; left: auto; top: 0px;"
      ></canvas>
    </div>
    <!-- 게임 타이머 -->
    <div class="game-timer" v-if="gestureOutput">
      <h1>Timer : {{ gameTimer }}</h1>
    </div>
    <!-- 시작 전 타이머 -->
    <div class="start-timer" v-if="countdown > 0">
      {{ countdown }}
    </div>
    <!-- 게임 승리 -->
    <div class="victory" v-if="gameResultDp">
      <div v-if="gameResult">게임 승리!</div>
      <div v-else>게임 실패!</div>
    </div>
    <!-- 광물 치기 카운트 -->
    <div class="target-count">
      광물 깨뜨리기까지 남은 횟수 : {{ targetCount }}
    </div>
  </div>
</template>

<script>
// 모션 인식 라이브러리
import { GestureRecognizer, FilesetResolver } from '@mediapipe/tasks-vision';
// 광물 이미지
import jewelImage from '@/assets/img/mission/jewel.png';
export default {
  data() {
    return {
      // 웹 캠 관련
        gestureRecognizer: GestureRecognizer,
        webcamRunning: false, // 웹캠 실행 여부
        lastVideoTime: -1, // 비디오의 마지막 시간:
        countdown: 3,
        
        // 제스쳐 인식 결과
        results: undefined,
        
        // 아웃풋 결과
        gestureOutput: false,
        leftCategoryName: "",
        leftCategoryScore: 0,
        leftXAxis: "",
        leftYAxis: "",
        rightXAxis: "",
        rightYAxis: "",

        // 광물 띄우기, 초기 위치 지정
        jewelImg: new Image(),
        jewelPosition: {x: null, y: null},
        initialJewelPositionSet: false,
        
        // 게임 결과
        gameResultDp: false,    // 게임 결과 표시 여부
        gameResult: false,      // 게임 승패 여부
        
        leftHand: null,
        rightHand: null,
        leftHandX: null,
        leftHandY: null,
        rightHandX: null,
        rightHandY: null,

        targetCount: 10,
        gameTimer: 10,

        // 팔을 뗐다가 붙였다가 하기 위한 변수
        isStraight: false,
      } 
  },

  mounted() {
    if (this.hasGetUserMedia()) {
      this.createGestureRecognizer();
    } else {
      console.warn('경고!')
    }
  },
  
  // 메모리 정리
  beforeDestroy() {
  },


  methods: {

    //카메라준비
    hasGetUserMedia() {
      return !!navigator.mediaDevices?.getUserMedia;
    },

    // 모션 인식 작업을 위한 설정
    async createGestureRecognizer() {
      // 모션 인식 작업을 위한 WASM 파일 로드
      const vision = await FilesetResolver.forVisionTasks(
        "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@latest/wasm"
      )
      // 제스처 인식 옵션
      try {
        this.gestureRecognizer = await GestureRecognizer.createFromOptions(
          vision,
          {
            baseOptions: {
              modelAssetPath: "/models/gesture_recognizer.task",    // 모델 저장 경로
              delegate: "GPU"                         // 모션 인식 처리 방식
            },
            runningMode: "VIDEO",       // 비디오 인식
            num_hands: 2                 // 두 손 사용
          }
        )
      } catch (error) {
        console.error("error")
      }
      await this.loadVideo()

      console.log(this.gestureRecognizer)
    },

    
    // 비디오 로드 함수
    async loadVideo() {
      // 제스처 인식기가 로드되지 않았을 때, 알림 표시 후 함수 종료
      if (!this.gestureRecognizer) {
        alert("제스처 인식기가 로드되길 기다려주세요");
        return;
      }
      try {
        // 비디오 스트림 제약 조건: 비디오만 사용
        const constraints = {
          video: true
        }
        const videoEl = this.$refs.webcam;
        const stream = await navigator.mediaDevices.getUserMedia(constraints)     // 비디오 스트림 요청
        videoEl.srcObject = stream
      } catch (error) {
        alert("웹 캠에 접근할 수가 없습니다.")
        console.log(error)
      }
      this.initializerJewel()             // 보석 이미지 초기화
    },

    
    // 캔버스 크기를 비디오 크기에 맞게 조정
    adjustCanvasSizeToVideo() {
      const videoEl = this.$refs.webcam
      const jewelCanvasEl = this.$refs.jewel_canvas
      // 비디오 요소와 보석 캔버스 요소가 모두 존재하면 크기 맞추기
      if (videoEl && jewelCanvasEl) {
        jewelCanvasEl.width = videoEl.offsetWidth
        jewelCanvasEl.height = videoEl.offsetHeight  
      }
    },
    

    // 보석 이미지 초기화
    initializerJewel() {
      this.jewelImg.src = jewelImage;
        this.jewelImg.onload = () => {
          console.log('이미지가 성공적으로 로드되었습니다.');
          this.drawJewel()
        };
        this.jewelImg.onerror = () => {
          console.log('이미지 로드 중에 오류가 발생했습니다.')
        }
    },


    // 보석 이미지 그리기
    async drawJewel() {
        const jewelCanvasEl = this.$refs.jewel_canvas;
        // 캔버스가 없는 경우 "캔버스가 오류났습니다!"
        if (!jewelCanvasEl) {
            return console.log("캔버스가 오류났습니다!")
        }
        const canvasCtx = await jewelCanvasEl.getContext("2d");   // 2d로 그리겠습니다
        // 이미지를 그리기 전에 캔버스 초기화
        canvasCtx.clearRect(0, 0, jewelCanvasEl.width, jewelCanvasEl.height);
        // 보석 이미지의 위치 설정
        let jewelX, jewelY;
        if (!this.initialJewelPositionSet) {
            // 보석 이미지의 초기 위치를 랜덤으로 설정
            jewelX = Math.random() * (jewelCanvasEl.width - 100); // 캔버스 너비 내에서 랜덤한 x 위치
            jewelY = Math.random() * (jewelCanvasEl.height - 100); // 캔버스 높이 내에서 랜덤한 y 위치
            this.jewelPosition.x = jewelX
            this.jewelPosition.y = jewelY
            this.initialJewelPositionSet = true;
        } else {
            // 이후에는 고정된 위치에 그리기
            jewelX = this.jewelPosition.x; // 고정된 x 위치
            jewelY = this.jewelPosition.y; // 고정된 y 위치
        }

        // 보석 이미지 그리기
        canvasCtx.drawImage(this.jewelImg, jewelX, jewelY, 100, 100);

        // 주기적으로 drawJewel 메서드 호출하여 보석 이미지를 계속해서 유지
        setTimeout(() => this.drawJewel(), 1000);
    },


    // 손 모션 인식
    async predictWebcam() {
      const videoElement = this.$refs.webcam;       // 웹캠 비디오 요소 가져오기
      let nowInMs = Date.now();
      if (videoElement.currentTime !== this.lastVideoTime) {
        // console.log(this.gestureRecognizer)
        // 시간 업데이트 ( 마지막 시간을 현재 시간으로 )
        this.lastVideoTime = videoElement.currentTime;
        try {
          this.results = await this.gestureRecognizer.recognizeForVideo(videoElement, nowInMs);
        } catch (error) {
          console.error('손 모션 인식 오류:', error);
        }
      }

      
      if (this.isHit() && !this.isStraight) {
        this.targetCount -= 1
        this.isStraight = true
        console.log("쳤다")
        if (this.targetCount === 0) {
          this.drawJewel()
          this.targetCount = 10
          this.gameResult = true
          this.gameResultDp = true
          alert("축하합니다.")
          this.$emit('endMinigame', this.gameResultDp)
        } 
      }
      if (this.gameTimer < 0) {
        if (this.targetCount > 0) {
          this.gameResult = false;
          this.gameResultDp = true;
          alert("게임 실패!")
          this.$emit('endMinigame', this.gameResultDp)
        } 
      }
      
      window.requestAnimationFrame(this.predictWebcam)
    },
    

    // 광물 좌표와 손 좌표를 비교하여 치는 로직
    isHit() {
      const jewelCanvasEl = this.$refs.jewel_canvas;

      if (this.results.landmarks.length === 1) {
        this.leftHand = this.results.landmarks[0]
        this.leftHandX = this.leftHand[9].x * (jewelCanvasEl.width)
        this.leftHandY = this.leftHand[9].y * (jewelCanvasEl.height)
      } 
      
      if (this.results.landmarks.length === 2) {
        this.rightHand = this.results.landmarks[1]
        this.rightHandX = this.rightHand[9].x * (jewelCanvasEl.width)
        this.rightHandY = this.rightHand[9].y * (jewelCanvasEl.height)
      }
      
      const jewelWidth = 50
      const jewelHeight = 50
      
  

      if (
        (this.leftHandX >= this.jewelPosition.x && 
          this.leftHandX <= this.jewelPosition.x + jewelWidth && 
          this.leftHandY >= this.jewelPosition.y && 
          this.leftHandY <= this.jewelPosition.y + jewelHeight) ||
        (this.rightHandX >= this.jewelPosition.x && 
          this.rightHandX <= this.jewelPosition.x + jewelWidth && 
          this.rightHandY >= this.jewelPosition.y && 
          this.rightHandY <= this.jewelPosition.y + jewelHeight)
      ) {
        return true;
      }
      this.isStraight = false
      return false
    },


    onGestureLoaded() {
      // this.predictWebcam();
      this.startCountdown();
      this.gestureOutput = true;
    },

    // 카운트다운 시작
    startCountdown() {
      const countdownInterval = setInterval(() => {
        this.countdown -= 1;
        if (this.countdown === 0) {
          clearInterval(countdownInterval);
          // 3초 카운트다운 후에 얼굴 인식 시작
          this.startGameTimer();
          this.predictWebcam();
        }
      }, 1000);
    },


    // 게임시작
    startGameTimer() {
      this.webcamRunning = true;
      const gameInterval = setInterval(() => {
        this.gameTimer -= 1
        if (this.gameTimer === 0) {
          clearInterval(gameInterval)
          this.webcamRunning = false
          this.gameResultDp = true
        }
      }, 1000)
    },
  }
};

</script>

<style scoped>
.videoView {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 30px;
  padding-bottom: 30px;
  background-color: rgb(10, 10, 10);
  border-radius: 15px;
  width: 50%;
  height: 70%;
  margin: 0 auto;
  margin-top: 50px;
  /* border: 3px solid rgb(245, 202, 12); */
}

.media-container {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* 거울모드 */
.media {
  transform: rotateY(180deg);
  -webkit-transform:rotateY(180deg); 
}

#webcam {
  width: 70%;
  height: auto;
  border-radius: 20px;
  margin-top: 10px;
}

.victory {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: rgb(5, 5, 5);
  font-size: 5rem;
  background-color: rgb(245, 202, 12);
  width: 23vw;
  height: 10.5vh;
  text-align: center;
  font-weight: bold;
  border-radius: 50px;
  border: 5px solid black;
  font-size: 50px;
}

/* 캔버스 스타일링 */
canvas.media {
  position: absolute;
  left: 0;
  top: 0;
  z-index: 1;
}

.game-timer {
  color: white;
}

.target-count {
  color: yellow;
  padding-bottom: 10px;
}

.start-timer {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: rgb(255, 255, 255);
  font-stretch: expanded;
  font-size: 5rem;
  background-color: rgba(255, 22, 6, 0.863);
  width: 100px;
  height: 100px;
  text-align: center;
  font-weight: bold;
  padding-bottom: 15px;
  border-radius: 30px;
}


</style>