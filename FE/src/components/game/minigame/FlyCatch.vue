<template>
    <!-- 라이브 웹캠 화면을 표시하는 컨테이너 -->
    <div id="liveView" class="videoView">
      <div class="media-container" style="position: relative;">
        <!-- 웹캠 비디오 요소 -->
        <video
          class="media"
          id="webcam"
          ref="webcam"
          autoplay
          playsinline
        ></video>
        <!-- 웹캠 비디오 위에 파리 그릴 캔버스 -->
        <canvas
          class="media"
          id="fly-canvas"
          ref="fly_canvas"
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
      <!-- 게임 결과 -->
      <div class="game-result" v-if="gameResult">
        <div v-if="victory">게임 승리!</div>
        <div v-else>게임 패배!</div>
      </div>
      <!-- 남은 파리 개수 -->
      <div class="target-count">
        남은 파리 개수 : {{ targetCount }}
      </div>
    </div>
</template>

<script>
  // 모션 인식 라이브러리
  import { GestureRecognizer, FilesetResolver, DrawingUtils } from '@mediapipe/tasks-vision';
  // 파리 이미지
  import flyImage from '@/assets/img/mission/fly.png';
  export default {
    data() {
      return {
        // 웹 캠 관련
        gestureRocognizer: GestureRecognizer,
        runningMode: "VEDIO", // 웹캠 실행 모드
        webcamButtonText: "제스처 인식 로드 전", // 웹캠 활성화 버튼 텍스트
        webcamRunning: false, // 웹캠 실행 여부
        lastVideoTime: -1, // 비디오의 마지막 시간
        constraints: {
          video: true
        },

        // 제스쳐 인식 결과
        results: undefined,

        // 아웃풋 결과
        gestureOutput: false,
        leftCategoryName: "",
        leftCategoryScore: 0,
        leftXAxis: "",
        leftYAxis: "",
        rightCategoryName: "",
        rightCategoryScore: 0,
        rightXAxis: "",
        rightYAxis: "",
        gestureSequence: [],
        lastGetureTime: Date.now(),

        // 파리 생성 및 초기 위치, 방항, 속도 지정
        flyImg: new Image(),
        flyPosition: { x: Math.random(), y: Math.random() },
        flyDirection: { x: (Math.random() - 0.5) * 50, y: (Math.random() - 0.5) * 50 },
        flies: [],
        numOfFlies: 5,
        targetCount: 5,

        // 게임 카운트
        countdown: 5,
        gameTimer: 10,

        // 게임 결과
        gameResult: false,
        victory: false,
      }
    },
    
    mounted() {
      this.createGestureRecognizer(); // 제스처 인식기 초기화
      this.startCountdown(); // 게임 시작 카운트
      window.addEventListener('resize', this.adjustCanvasSizeToVideo)
    },

    beforeDestroy() {
      window.removeEventListener('resize', this.adjustCanvasSizeToVideo)
    },

    methods: {
      // 캔버스 크기를 비디오 크기에 맞게 조정
      adjustCanvasSizeToVideo() {
        const videoElement = this.$refs.webcam
        const flyCanvasElement = this.$refs.fly_canvas
        if(videoElement && flyCanvasElement) {
          flyCanvasElement.width = videoElement.offsetWidth
          flyCanvasElement.height = videoElement.offsetHeight
        }
      },

      // 제스처 인식기 초기화 및 캔버스 크기 설정
      async createGestureRecognizer() {
        const vision = await FilesetResolver.forVisionTasks(
          "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.3/wasm"
        );
        this.gestureRecognizer = await GestureRecognizer.createFromOptions(
          vision,
          {
            baseOptions: {
              modelAssetPath:
                "/models/gesture_recognizer.task",
              delegate: "GPU"
            },
            runningMode: "VIDEO",
            numHands: 2
          });
        const videoElement = this.$refs.webcam;
        this.toggleWebcam()
        videoElement.addEventListener('loadedmetadata', () => {
          // 로드되면 비디오 사이즈에 맞추어 캔버스 사이즈 맞추기
          this.adjustCanvasSizeToVideo()
        })
      },


      // 카운트다운 시작
      startCountdown() {
        const countdownInterval = setInterval(() => {
          this.countdown -= 1
          if (this.countdown === 0) {
            clearInterval(countdownInterval)
            this.startGameTimer();
          }
        }, 1000)
      },


      // 게임시작
      async startGameTimer() {
        await this.initializeFlies();
        await this.predictWebcam()
        const gameInterval = await setInterval(() => {
          this.gameTimer -= 1
          if (this.gameTimer === 0) {
            console.log("파리잡기 게임 끝");
            this.$emit('endMinigame', this.victory)
            clearInterval(gameInterval)
            this.webcamRunning = false
            this.clearFlyCanvas()
            this.gameResult = true
          }
        }, 1000)
      },


      // 웹 캠 토글
      async toggleWebcam() {
        // 제스처 인식기 초기화 덜 됐을 경우
        if (!this.gestureRecognizer) {
          alert("제스처 인식기가 로드되길 기다려주세요");
          return;
        }
        // 웹 캠 활성화
        this.webcamRunning = true;
        this.gestureOutput = true
        this.webcamButtonText = "제스처 인식 로드 완료";
        try {
          const stream = await navigator.mediaDevices.getUserMedia(this.constraints)
          const videoElement = this.$refs.webcam;
          videoElement.srcObject = stream
        } catch (error) {
          alert("웹 캠에 접근할 수가 없습니다.")
          console.log(error)
        }
      },


      // 모션 인식 시작
      async predictWebcam() {
        // 비디오 요소 가져오기
        const videoElement = this.$refs.webcam;
        // 현재 시간
        let nowInMs = Date.now();
        // 비디오 현재 시간과 마지막 시간과 다르면
        if (videoElement.currentTime !== this.lastVideoTime) {
          // 마지막 시간 현재 시간으로 업데이트
          this.lastVideoTime = videoElement.currentTime;
          this.results = this.gestureRecognizer.recognizeForVideo(videoElement, nowInMs);
        }
        // 손제스쳐 결과 처리
        this.results.gestures.forEach((gesture, index) => {
            const categoryName = gesture[0].categoryName
            const currentTime = Date.now()
            const timeDiff = currentTime - this.lastGetureTime
            if (timeDiff < 50) {
              this.gestureSequence.push(categoryName)
            } else {
              this.gestureSequence = [categoryName];
            }
            this.lastGetureTime = currentTime;
          })
        // 유효한 상태 변화 확인
        if (this.checkFlyCaught()) {
            console.log('파리 잡기 성공')
            this.webcamRunning = false
            this.clearFlyCanvas()
            this.victory = true
            this.gameResult = true
        }
        // 인식된 손 정보
        if (this.results.gestures.length == 1) {
          this.leftCategoryName = this.results.gestures[0][0].categoryName
          this.leftCategoryScore = parseFloat(this.results.gestures[0][0].score * 100).toFixed(2) 
          this.leftXAxis = this.results.landmarks[0][9].x
          this.leftYAxis = this.results.landmarks[0][9].y
        } else if (this.results.gestures.length == 2) {
          this.leftCategoryName = this.results.gestures[0][0].categoryName
          this.leftCategoryScore = parseFloat(this.results.gestures[0][0].score * 100).toFixed(2) 
          this.leftXAxis = this.results.landmarks[0][9].x
          this.leftYAxis = this.results.landmarks[0][9].y
          this.rightCategoryName = this.results.gestures[1][0].categoryName
          this.rightCategoryScore = parseFloat(this.results.gestures[1][0].score * 100).toFixed(2) 
          this.rightXAxis = this.results.landmarks[1][9].x
          this.rightYAxis = this.results.landmarks[1][9].y
        } else {
          this.gestureOutputText = false;
        }
        // 손모양 계속 인식을 위해 계속 호출
        if (this.webcamRunning) {
          window.requestAnimationFrame(this.predictWebcam);
        }
      },

      // 파리 초기화
      initializeFlies() {
        this.flies = Array.from({ length: this.numOfFlies }).map(() => ({
          position: { x: Math.random() * 200, y: Math.random() * 200 },
          direction: { x: (Math.random() - 0.5) * 30, y: (Math.random() - 0.5) * 30 },
          caught: false,
        }))

        this.flyImg.src = flyImage;
        this.flyImg.onload = () => {
          console.log('이미지가 성공적으로 로드되었습니다.');
          this.animateFly();
        };
        this.flyImg.onerror = () => {
          console.log('이미지 로드 중에 오류가 발생했습니다.')
        }
      },


      // 파리 이미지 그리기
      drawFly() {
        if (this.webcamRunning) {
          // 파리 그리기
          const flyCanvas = this.$refs.fly_canvas;
          if(!flyCanvas) {
            return console.log('파리 캔버스 오류')
          }
          const canvasCtx = flyCanvas.getContext("2d");
          canvasCtx.clearRect(0, 0, flyCanvas.width, flyCanvas.height) // 캔버스 클리어

          this.flies.forEach(fly => {
            if (!fly.caught) {
              canvasCtx.save(); // 현재 캔버스 상태 저장
              if (fly.direction.x < 0) {
                canvasCtx.scale(-1, 1); // x축 방향으로 반전
                canvasCtx.drawImage(this.flyImg, -fly.position.x - 50, fly.position.y, 50, 50); // 이미지 그리기 (위치 조정 필요)
              } else {
                canvasCtx.drawImage(this.flyImg, fly.position.x - 50, fly.position.y, 50, 50); // 이미지 그리기 (위치 조정 필요)
              }
              canvasCtx.restore(); // 캔버스 상태 복원
            }
          })
        }
      },
      

      // 파리 움직이는 로직
      animateFly() {
        if (this.webcamRunning) {
          const flyCanvas = this.$refs.fly_canvas
          const canvasWidth = flyCanvas.width;
          const canvasHeight = flyCanvas.height;
          this.flies.forEach((fly, index) => {
            if (!fly.caught) {
              let newX = fly.position.x + fly.direction.x
              let newY = fly.position.y + fly.direction.y

              if (newX <= 0 || newX >= canvasWidth - 50) {
                fly.direction.x *= -1
                newX = fly.position.x + fly.direction.x
              }
              if (newY <= 0 || newY >= canvasHeight - 50) {
                fly.direction.y *= -1
                newY = fly.position.y + fly.direction.y
              }
              // 파리 위치 업데이트
              fly.position.x = newX
              fly.position.y = newY
            }
          })
          this.drawFly();
          requestAnimationFrame(this.animateFly)
        }
      },


      // 움켜잡는 상태 관리
      isValidGestureChange() {
        const validSequence = ['Open_Palm', 'None', 'Closed_Fist']
        return validSequence.every((el) => this.gestureSequence.includes(el))
      },


      // 파리와 손의 위치가 가까운지 판단
      checkFlyCaught() {
        const vedioElement = this.$refs.webcam;
        let caughtAnyFly = false;

        this.flies.forEach(fly => {
          if (!fly.caught) {
            const leftHandX = this.leftXAxis * vedioElement.offsetWidth
            const leftHandY = this.leftYAxis * vedioElement.offsetHeight
            const rightHandX = this.rightXAxis * vedioElement.offsetWidth
            const rightHandY = this.rightYAxis * vedioElement.offsetHeight

            const flyX = fly.position.x
            const flyY = fly.position.y
            const leftDistance = Math.sqrt(Math.pow(leftHandX - flyX, 2) + Math.pow(leftHandY - flyY, 2))
            const rightDistance = Math.sqrt(Math.pow(rightHandX - flyX, 2) + Math.pow(rightHandY - flyY, 2))
            if ((leftDistance < 20 || rightDistance < 20) && this.isValidGestureChange()) {
              console.log('파리 잡힘')
              fly.caught = true
              caughtAnyFly = true
              --this.targetCount
            }
          }
        })

        if (caughtAnyFly) {
          return this.checkAllFliesCaught()
        }
      },


      // 모든 파리 잡혔는지 확인
      checkAllFliesCaught() {
        const allCaught = this.flies.every(fly => fly.caught)
        if (allCaught) {
          console.log('모든 파리가 잡혔습니다.')
          return true
        }
      },


      // 파리 캔버스 클리어
      clearFlyCanvas() {
        const flyCanvasElement = this.$refs.fly_canvas;
        if (flyCanvasElement) {
          const flyCanvasCtx = flyCanvasElement.getContext("2d");
          flyCanvasCtx.clearRect(0, 0, flyCanvasElement.width, flyCanvasElement.height)
        }
      },
    },
  }
</script>

<style scoped>
@import url('@/assets/css/game/minigame/flyCatch.css');
</style>