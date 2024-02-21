<template>
  <!-- 방 생성 모달 -->
  <div class="room-make-modal">
    <!-- 방 생성 form -->
    <h2>방 생성</h2>
    <form class="room-make-form" @submit.prevent>
      <div class="form-group">
        <label for="title">방 제목</label>
        <input type="text" id="title" maxlength="6" v-model="roomInfo.title" />
      </div>
      <div class="form-group">
        <label for="isPublic">공개 여부</label>
        <div name="theme" id="theme">
          <button
            id="theme-btn"
            @click="selectScope(true)"
            :class="{ selected: roomInfo.public === true }"
          >
            공개
          </button>
          <button
            id="theme-btn"
            @click="selectScope(false)"
            :class="{ selected: roomInfo.public === false }"
          >
            비공개
          </button>
        </div>
      </div>
      <div class="form-group" v-if="!roomInfo.public">
        <label for="password">비밀번호</label>
        <input
          type="text"
          id="password"
          maxlength="8"
          v-model="roomInfo.password"
        />
      </div>
      <div class="form-group">
        <label for="theme">맵 테마</label>
        <div>
          <div name="theme" id="theme">
            <button
              id="theme-btn"
              @click="selectTheme('설날')"
              :class="{ selected: roomInfo.theme === '설날' }"
            >
              설날
            </button>
            <button
              id="theme-btn"
              @click="selectTheme('추석')"
              :class="{ selected: roomInfo.theme === '추석' }"
            >
              추석
            </button>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label for="speed">속도</label>
        <div>
          <button
            id="theme-btn"
            @click="selectSpeed(0)"
            :class="{ selected: roomInfo.gameSpeed === 0 }"
          >
            느림
          </button>
          <button
            id="theme-btn"
            @click="selectSpeed(1)"
            :class="{ selected: roomInfo.gameSpeed === 1 }"
          >
            보통</button
          ><button
            id="theme-btn"
            @click="selectSpeed(2)"
            :class="{ selected: roomInfo.gameSpeed === 2 }"
          >
            빠름
          </button>
        </div>
      </div>
      <div class="button-group">
        <button type="submit" class="create" @click="makeGame(roomInfo)">
          생성
        </button>
        <button type="button" class="cancle" @click="closeModal('roomMaking')">
          취소
        </button>
      </div>
    </form>
  </div>
</template>

<script>
// 방 정보 데이터
import { useRoomStore } from "@/store/roomStore";
import { useUserStore } from "@/store/userStore";

import { connectRoom, pubRoom } from "@/util/socket";

export default {
  data() {
    return {
      // 생성할 방 정보
      roomInfo: {
        title: "",
        public: true,
        password: "",
        theme: "설날",
        gameSpeed: 1,
      },
    };
  },

  methods: {
    /* 방 생성 함수 */
    makeGame(roomInfo) {
      // (1) 로그인 되었는지 확인한다.
      if (!useUserStore().isLogin) {
        alert("연결이 끊어졌습니다.");
        useUserStore().initData();
        router.push("/");
      }

      // 만약 입력된 곳에서 빈 공백이 있다면, 해당 부분 alert 표시
      if (roomInfo.title === "") {
        alert("방 제목을 작성해주세요");
      } else if (roomInfo.public === false && roomInfo.password.trim() === "") {
        alert("비공개 방으로 비밀번호를 입력하세요");
      } else {
        // 생성하기 위한 방 정보를 v-model로 된 객체 가져옴
        useRoomStore().createRoomInfo = {
          ...this.roomInfo,
        };

        // 방 생성을 시작
        useRoomStore()
          .createRoom()
          .then(() => {
            connectRoom("Room", this.$router, "make").then(() => {
              useUserStore().currentRoomInfo = {
                ...useRoomStore().createRoomInfo,
              };

              pubRoom(
                "/pub/room/" +
                  useRoomStore().createRoomInfo.roomCode +
                  "/enter",
                useUserStore().userInfo.email
              );
            });
          })

          // 방 생성 실패시, 방 삭제 요청
          // (아직 삭제 요청 미구현)
          .catch((error) => {
            alert("방 생성 실패");
            console.log(error);
          });
      }
    },

    // 모달 관리
    closeModal(modalType) {
      const roomStore = useRoomStore();
      roomStore.closeModal(modalType);
    },

    selectScope(val) {
      this.roomInfo.public = val;
    },

    selectTheme(val) {
      this.roomInfo.theme = val;
    },

    selectSpeed(val) {
      this.roomInfo.speed = val;
    },
  },

  // 공개 여부를 계속 감시
  watch: {
    "roomInfo.isPublic": function (newValue) {
      if (newValue) {
        this.roomInfo.password = ""; // 공개면 비밀번호를 초기화
      }
    },
  },
};
</script>

<style scoped>
@import "@/assets/css/room/roomMaking.css";
</style>