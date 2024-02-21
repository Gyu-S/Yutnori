import { GestureRecognizer, FilesetResolver, DrawingUtils } from '@mediapipe/tasks-vision';
import flyImage from '@/assets/img/mission/fly.png';

export default (await import('vue')).defineComponent({
data() {
return {
gestureRocognizer: GestureRecognizer,
runningMode: "VEDIO", // 웹캠 실행 모드
webcamButtonText: "웹 캠 활성화", // 웹캠 활성화 버튼 텍스트
webcamRunning: false, // 웹캠 실행 여부
lastVideoTime: -1, // 비디오의 마지막 시간
results: undefined, // 제스처 인식 결과
constraints: {
video: true
},
// 아웃풋 결과
gestureOutput: false,
categoryName: "",
categoryScore: 0,
handedness: "",
xAxis: "",
YAxis: "",

// 비디오 크기
videoHeight: "360px",
videoWidth: "480px",

// 파리 생성 및 초기위치 지정
flyImg: new Image(),
flyPosition: { x: Math.random(), y: Math.random() }
};
},

mounted() {
this.createGestureRecognizer(); // 제스처 인식기 초기화
this.flyImg.src = flyImage; // 파리 이미지 경로

// 이미지 로드 성공
this.flyImg.onload = () => {
console.log('이미지가 성공적으로 로드되었습니다.');
this.flyImg.width = 30;
this.flyImg.height = 30;
this.animateFly();
};
// 이미지 로드 실패
this.flyImg.onerror = () => {
console.error('이미지 로드 중에 오류가 발생했습니다.');
};

},

methods: {
// 제스처 인식기 초기화
async createGestureRecognizer() {
const vision = await FilesetResolver.forVisionTasks(
"https://cdn.jsdelivr.net/npm/@mediapipe/tasks-vision@0.10.3/wasm"
);
this.gestureRecognizer = await GestureRecognizer.createFromOptions(
vision,
{
baseOptions: {
modelAssetPath: "/models/gesture_recognizer.task",
delegate: "GPU"
},
runningMode: this.runningMode,
numHands: 2
}
);
},
// 유저 웹 캠 접근 여부 확인
hasGetUserMedia() {
return !!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia);
},

// 웹 캠 토글
toggleWebcam() {
// 제스처 인식기 초기화 덜 됐을 경우
if (!this.gestureRecognizer) {
alert("제스처 인식기가 로드되길 기다려주세요");
return;
}
if (this.webcamRunning) {
// 웹 캠 비활성화
this.webcamRunning = false;
this.gestureOutput = false;
this.webcamButtonText = "제스처 인식 시작하기";
} else {
// 웹 캠 활성화
this.webcamRunning = true;
this.gestureOutput = true;
this.webcamButtonText = "제스처 인식 끝내기";
}

if (this.webcamRunning) {
// 웹캠 활성화
navigator.mediaDevices.getUserMedia(this.constraints).then((stream) => {
const videoElement = this.$refs.webcam;
videoElement.srcObject = stream;
videoElement.addEventListener("loadeddata", this.predictWebcam);
});
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
this.results = this.gestureRecognizer.recognizeForVideo(
videoElement,
nowInMs
);
}
// 손 제스쳐 그릴 준비
const canvasElement = this.$refs.output_canvas;
const canvasCtx = canvasElement.getContext("2d");
// 현재 캔버스 상태를 저장
canvasCtx.save();
// 캔버스 내용 지우기
canvasCtx.clearRect(0, 0, canvasElement.width, canvasElement.height);
const drawingUtils = new DrawingUtils(canvasCtx);

// 제스처 인식 사이즈 조절
canvasElement.style.height = this.videoHeight;
videoElement.style.height = this.videoHeight;
canvasElement.style.width = this.videoWidth;
videoElement.style.width = this.videoWidth;

if (this.results.landmarks) {
for (const landmarks of this.results.landmarks) {
// 제스처 랜드마크 그리기
drawingUtils.drawConnectors(
landmarks,
GestureRecognizer.HAND_CONNECTIONS,
{
color: "#00FF00",
lineWidth: 5
}
);
drawingUtils.drawLandmarks(landmarks, {
color: "#FF0000",
lineWidth: 2
});
}
}
// 2D 컨텍스트에 대한 상태를 이전 상태로 복원
canvasCtx.restore();

// 인식된 손 정보
if (this.results.gestures.length > 0) {
this.categoryName = this.results.gestures[0][0].categoryName;
this.categoryScore = parseFloat(this.results.gestures[0][0].score * 100).toFixed(2);
this.handedness = this.results.handednesses[0][0].displayName;
// 화면 왼쪽이 0
this.xAxis = this.results.landmarks[0][9].x;
// 화면 위쪽이 0
this.YAxis = this.results.landmarks[0][9].y;
} else {
this.gestureOutputText = false;
}

if (this.webcamRunning) {
window.requestAnimationFrame(this.predictWebcam);
}
},
// 파리 이미지 그리기
drawFly() {
console.log('파리 그리기');
// 비디오 요소 가져오기
const videoElement = this.$refs.webcam;
// 파리 그리기
const canvasElement = this.$refs.fly_canvas;
if (!canvasElement) {
return console.log('파리 캔버스 오류');
}
const canvasCtx = canvasElement.getContext("2d");
canvasCtx.clearRect(0, 0, canvasElement.width, canvasElement.height); // 캔버스 클리어
canvasCtx.drawImage(this.flyImg, this.flyPosition.x, this.flyPosition.y, 30, 30); // 파리그리기

// 파리 인식 사이즈 조절
canvasElement.style.height = this.videoHeight;
videoElement.style.height = this.videoHeight;
canvasElement.style.width = this.videoWidth;
videoElement.style.width = this.videoWidth;
},
// 파리 움직이는 로직
animateFly() {
console.log('파리 움직이기');
const canvasElement = this.$refs.fly_canvas;
const canvasWidth = canvasElement.width;
const canvasHeight = canvasElement.height;

// 현재 파리 위치
const currentX = this.flyPosition.x;
const currentY = this.flyPosition.y;

// 새로운 목표 위치 랜덤 생성
const targetX = Math.random() * canvasWidth;
const targetY = Math.random() * canvasHeight;

// 목표 위치까지의 거리 계산
const dx = targetX - currentX;
const dy = targetY - currentY;
const distance = Math.sqrt(dx * dx + dy * dy);

// 파리 이동 속도
const speed = 2;

if (distance > speed) {
}


this.flyPosition.x += (Math.random() - 0.5) * 20; // 더 큰 이동을 위해 값을 조정
this.flyPosition.y += (Math.random() - 0.5) * 20; // 더 큰 이동을 위해 값을 조정

// 파리 화면 안에 놓기
this.flyPosition.x = Math.max(0, Math.min(canvasElement.width, this.flyPosition.x));
this.flyPosition.y = Math.max(0, Math.min(canvasElement.height, this.flyPosition.y));

this.drawFly(); // 업데이트 된 위치에 파리 그리기

requestAnimationFrame(this.animateFly); // 다음 프레임을 위해 재귀 호출
}
}
});
