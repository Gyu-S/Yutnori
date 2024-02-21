<template>
  <div ref="threeJsContainer"></div>
</template>
    
    <script>
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { useGameStore } from "@/store/gameStore";

export default {
  mounted() {
    this.initThreeJS();
  },
  data() {
    return {
      yuts: [],                             // 윷 배열
      throwPower: [0.07, 0.07, 0.07, 0.07], // 초기 속도 => 얼마나 높게 올라가는지 결정
      secondPower: [0.03, 0.03, 0.03, 0.03],  // 두번째 힘.
      positionSpeedX: [0.02, 0.02, 0.02, 0.02], // 회전.
      gravity: -0.003, // 중력 => 윷에 작용하는 중력.
      animationFrameId: null,
      modelLoaded: false, // 모델 로드 상태를 확인하는 변수
    };
  },
  methods: {
    initThreeJS() {
      this.scene = new THREE.Scene();

      // 카메라 위치와 회전 설정
      this.camera = new THREE.PerspectiveCamera(110, 400 / 500, 0.1, 1000);
      this.camera.position.set(0, 0, 3); // 카메라를 살짝 위와 앞쪽으로 이동
      this.camera.lookAt(this.scene.position); // 카메라가 씬의 중심을 바라보도록 설정

      this.renderer = new THREE.WebGLRenderer({ alpha: true });
      this.renderer.setSize(400, 500);
      this.$refs.threeJsContainer.appendChild(this.renderer.domElement);

      const ambientLight = new THREE.AmbientLight(0xF4A460, 1);
      this.scene.add(ambientLight);

      const directionalLight = new THREE.DirectionalLight(0xF4A460, 1);
      directionalLight.position.set(0, 1, 1);
      this.scene.add(directionalLight);

      this.loadYuts();

      // 초기 렌더링
      this.renderer.render(this.scene, this.camera);
    },
    loadYuts() {
      // 로더 생성
      const objLoader = new OBJLoader();
      const textureLoader = new THREE.TextureLoader();

      // 텍스처 불러오기
      const texture = textureLoader.load("/yut/tex/yut_texture.png");
      const backTexture = textureLoader.load("/yut/tex/yut_back_texture.png");

      // OBJ 파일 불러오기
      // 백도 윷
      objLoader.load("/yut/yut_textured.obj", (object) => {
        object.traverse((child) => {
          if (child instanceof THREE.Mesh) {
            // 텍스처 적용
            child.material.map = backTexture;
            child.material.side = THREE.BackSide;
          }
        });
        object.scale.set(0.02, 0.01, 0.02); // 스케일은 요구 사항에 맞게 조정하세요.
        this.scene.add(object);
        this.yuts.push(object);
      });
      // 보통 윷
      for (var i = 0; i < 3; i++) {
        // OBJ 파일 불러오기
        objLoader.load("/yut/yut_textured.obj", (object) => {
          object.traverse((child) => {
            if (child instanceof THREE.Mesh) {
              // 텍스처 적용
              child.material.map = texture;
              child.material.side = THREE.BackSide;
            }
          });
          object.scale.set(0.02, 0.01, 0.02); // 스케일은 요구 사항에 맞게 조정하세요.
          this.scene.add(object);
          this.yuts.push(object);
        });
      }
    },
    throwYut() {
      const gameStore = useGameStore();
      const throwRes = gameStore.throwRes;
      // if (!this.modelLoaded) return;
      // 윷 던지기 시작 위치와 속도 초기화
      // console.log(this.yuts);
      let positions = [];
      let flag = [];
      for (var i = 0; i < 4; i++) {
        let position;
        let isPosition;

        do {
          isPosition = false;
          let num1 = Math.random() * -2;
          let num2 = Math.random() * 2;
          position = new THREE.Vector3(
            Math.abs(num1) > Math.abs(num2) ? num1 : num2,
            0,
            Math.random() * -0.3 + Math.random() * 0.3
          );
          positions.forEach((pos) => {
            if (position.distanceTo(pos) < 0.5) {
              isPosition = true;
            }
          });
        } while (isPosition);

        positions.push(position);

        this.yuts[i].position.set(position.x, 0, 0);
        this.yuts[i].rotation.set(throwRes[i]?Math.PI : 0, 0, Math.random() * 2);
        this.throwPower[i] = 0.07;
        this.secondPower[i] = 0.04;
        this.positionSpeedX[i] = 0.02; // x축 회전 속도

        flag[i] = Math.floor(Math.random() * 4) % 2;
      }
      this.rotationSpeedX = 0.07; // x축 회전 속도

      let secondFlag = [false,false,false,false];

      const animate = () => {
        this.animationFrameId = requestAnimationFrame(animate);

        // 각 윷을 움직인다.
        for (var i = 0; i < 4; i++) {

          // 처음 올라가고 내려오면 들어간다.
          if (secondFlag[i] || this.yuts[i].position.y < 0) {
            // 내려왔으면 0으로 초기화 한 후 움직인다.
            if(!secondFlag[i])
              this.yuts[i].position.y = 0;
            secondFlag[i] = true;

            // 한번만 더 올라가게 한다.
            if (this.yuts[i].position.y >= 0) {
              this.yuts[i].position.y += this.secondPower[i];
              this.secondPower[i] += this.gravity;
            }

            // 회전을 랜덤으로 준다. 오른쪽 or 왼쪽
            this.yuts[i].position.x +=
              flag[i] === 0 ? this.positionSpeedX[i] : -this.positionSpeedX[i];
            this.yuts[i].rotation.y += flag[i] === 0 ? 0.07 : -0.07;

            this.positionSpeedX[i] -= 0.0002;
            if (this.positionSpeedX[i] <= 0)
              cancelAnimationFrame(this.animationFrameId);
          } else {
            // 속도를 위치에 적용s
            this.yuts[i].position.y += this.throwPower[i];
            // 가속도를 속도에 적용
            this.throwPower[i] += this.gravity;
            // 회전
            this.yuts[i].rotation.x += this.rotationSpeedX;
          }
        }

        this.renderer.render(this.scene, this.camera);
      };
      animate();
    },
  },
};
</script>

