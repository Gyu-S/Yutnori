<template>
  <div id="liveView" class="videoView">
    <div class="media-container" id="media-container" style="position: relative;">
      <video 
        class="media" 
        ref="webcam" 
        id="webcam"
        autoplay 
        playsinline 
        @loadedmetadata="onVideoLoaded"
      ></video>
      <canvas
        ref="pointerCanvas"
        class="media"
        id="handCanvas"
      ></canvas>
    </div>
    <!-- 시작 전 타이머 -->
    <div v-if="countdown > 0" class="start-timer">
      {{ countdown }}
    </div>
    <!-- 게임 승리 -->
    <div v-if="gameResult" class="game-result">
      <div v-if="victory">게임 승리!</div>
      <div v-else>게임 실패</div>
    </div>
  </div>
</template>

<script>
import { FaceDetector, FilesetResolver } from "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.0";
import pointerImage from '@/assets/img/mission/pointer.png';
export default {
  data() {
    return {
      children: [],
      faceDetector: null,
      countdown: 3,

      // 손 모양
      pointerImg: new Image(),
      pointerPosition: { x: null, y: null, angle: null },
      isPointerMove: false,

      // 게임 결과
      victory: false,
      gameResult: false


    };
  },

  methods: {
    // 사용자 카메라 준비
    hasGetUserMedia() {
      return !!navigator.mediaDevices?.getUserMedia;
    },


    // 캔버스 크기 비디오 크기에 맞게 조정
    adjustCanvasSizeToVideo() {
      const webcam = this.$refs.webcam;
      const pointerCanvas = this.$refs.pointerCanvas;
      if (webcam && pointerCanvas) {
        pointerCanvas.width = webcam.offsetWidth
        pointerCanvas.height = webcam.offsetHeight
      }
    },


    // 초기화
    async initializeFaceDetector() {
      const vision = await FilesetResolver.forVisionTasks(
        "https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.0/wasm"
      );
      this.faceDetector = await FaceDetector.createFromOptions(vision, {
        baseOptions: {
          modelAssetPath: `/models/blaze_face_short_range.tflite`,
          delegate: "GPU"
        },
        runningMode: "VIDEO"
      });
      // 로드되면 캔버스 사이즈 조절
      const webcam = this.$refs.webcam
      webcam.addEventListener('loadedmetadata', () => {
        this.adjustCanvasSizeToVideo()
      })
      // 초기화 후 비디오 활성화
      this.enableCam();
    },


    // 캠 사용
    async enableCam() {
      if (!this.faceDetector) {
        alert("Face Detector is still loading. Please try again.");
        return;
      }
      const webcam = this.$refs.webcam;
      const constraints = { video: true };
      try {
        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        webcam.srcObject = stream;
      } catch (err) {
        console.error(err);
      }
    },

    // 비디오 로딩 완료 핸들러
    onVideoLoaded() {
      // 비디오 메타데이터 로딩 시점에서 카운트다운 시작
      this.startCountdown();
      this.initializePointer()
    },


    // 카운트다운 시작
    startCountdown() {
      const countdownInterval = setInterval(() => {
        this.countdown -= 1;
        if (this.countdown === 0) {
          clearInterval(countdownInterval);
          // 3초 카운트다운 후에 얼굴 인식 시작
          this.predictWebcam();
        }
      }, 1000);
    },


    // 포인터 초기화
    initializePointer() {
      console.log("포인터 초기화")
      this.pointerImg.src = pointerImage
      const webcam = this.$refs.webcam
      const webcamWidth = webcam.offsetWidth
      const webcamHeight = webcam.offsetHeight

      // 포인터의 초기 위치 설정 (캔버스 중앙 하단)
      this.pointerPosition.x = webcamWidth / 2;
      this.pointerPosition.y = webcamHeight - 100;
      this.pointerPosition.angle = 0;

      this.updatePointer();
    },


    // 포인터 업데이터
    updatePointer() {
      const pointerCanvas = this.$refs.pointerCanvas
      const ctx = pointerCanvas.getContext('2d')
      const imgWidth = 100
      const imgHeight = 100

      ctx.clearRect(0, 0, pointerCanvas.width, pointerCanvas.height)
      ctx.save()

      ctx.translate(this.pointerPosition.x , this.pointerPosition.y);
      ctx.rotate(this.pointerPosition.angle)
      ctx.drawImage(this.pointerImg, -imgWidth / 2, -imgHeight / 2, imgWidth, imgHeight); // 이미지 그리기

      ctx.restore()

      if (!this.isPointerMove) {
        requestAnimationFrame(this.updatePointer);
      }
    },


    // 제스처 인식 시작
    async predictWebcam() {
      const webcam = this.$refs.webcam;
      if (!webcam || webcam.readyState !== HTMLMediaElement.HAVE_ENOUGH_DATA) {
        window.requestAnimationFrame(this.predictWebcam);
        return;
      }

      let startTimeMs = performance.now();
      if (webcam.videoWidth > 0 && webcam.videoHeight > 0) {
        try {
          const detections = await this.faceDetector.detectForVideo(webcam, startTimeMs).detections;
          this.checkFaceDirection(detections)
        } catch (error) {
          console.error("얼굴 인식 중 오류 발생:", error);
        }
      } else {
        console.log("유효하지 않은 비디오 프레임");
      }
    },


    // 사용자의 방향 확인
    async checkFaceDirection(detections) {
      // 기계 방향 (0.5 보다 크면 오른쪽, 작으면 왼쪽)
      const machineDirection = Math.random() > 0.5;
      const machineRight = machineDirection;
      const machineLeft = !machineDirection;
      // 사람 방향
      const keypoints = detections[0].keypoints
      const noseX = keypoints[2].x
      const rightEarX = keypoints[4].x
      const leftEarX = keypoints[5].x

      // 왼쪽 귀와 코 사이
      const leftDistance = Math.abs(leftEarX - noseX)
      // 오른쪽 귀와 코 사이
      const rightDistance = Math.abs(rightEarX - noseX)

      // 고개에 따라 평가
      const userDirection = rightDistance < leftDistance ? 'right' : 'left';

      // 포인터 중단
      this.isPointerMove = true

      // 포인터 각도 조절
      if (machineRight) {
        this.pointerPosition.angle = - Math.PI / 4
      } else {
        this.pointerPosition.angle = Math.PI / 4
      }

      // 기계와의 게임 승리 여부 판단
      this.isPointerMove = true
      if ((machineRight && userDirection === 'left') || (machineLeft && userDirection === 'right')) {
        this.victory = true
      }
      this.gameResult = true
      this.$emit('endMinigame', this.victory)
    }
  },

  mounted() {
    if (this.hasGetUserMedia()) {
      this.initializeFaceDetector();
    } else {
      console.warn("getUserMedia() is not supported by your browser");
    }
  },
};
</script>

<style scoped>
@import url('@/assets/css/game/minigame/cham.css');
</style>