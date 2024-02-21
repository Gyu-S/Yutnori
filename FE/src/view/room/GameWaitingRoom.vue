<template>
  <div class="wait-div">
    <!-- 왼쪽 사용자 화면이다 참가한 유저가 보이고 채팅이 보인다 -->
    <div class="wait-left">
      <LeftComponentsVue />
    </div>
    <!-- 방 상세 정보와 준비, 나가기 버튼이 있다 -->
    <div class="wait-right">
      <RightComponentsVue />
      <button v-if="isOwner" class="wait-btn" @click="gameStart">시작</button>
      <button v-else class="wait-btn" @click="doReady">준비</button>
      <button class="wait-btn" @click="goTolist">나가기</button>
    </div>
    <!-- <banModal @close-ban-modal="closeBanModal" v-if="ban"/> -->
  </div>
</template>

<script>
import { connectRoom, pubRoom, pubPick } from "@/util/socket.js";
import { useUserStore } from "@/store/userStore";
import { useRoomStore } from "@/store/roomStore";

// 자식 컴포넌트
import RightComponentsVue from "@/components/room/gameWait/RightComponents.vue";
import LeftComponentsVue from "@/components/room/gameWait/LeftComponents.vue";
// import banModal from '@/components/room/gameWait/banModal.vue';

export default {
  components: {
    RightComponentsVue, // 방 상세
    LeftComponentsVue, // 참여 유저 정보
    // banModal // 금지 모달
  },

  data() {
    return {
      // 방 주인이지 확인
      isOwner: false,

      // 방을 나갔는지 확인
      isOut: false,
    };
  },

  created() {
    setTimeout(() => {
      let state = 0;

      Object.keys(useRoomStore().seatInfo).forEach((seatKey) => {
        if (
          useRoomStore().seatInfo[seatKey].nickname ===
          useUserStore().userInfo.nickname
        ) {
          state = useRoomStore().seatInfo[seatKey].state;
        }
      });

      if (state == 2) this.isOwner = true;
    }, 100);
  },

  methods: {
    /* 방장이 게임 시작을 누름 */
    gameStart() {
      // 게임 시작 가능한 상태인지 확인 (시작)
      let canStart = true;
      const seatInfo = useRoomStore().seatInfo;
      const seatKeys = Object.keys(seatInfo);

      for (let i = 1; i < seatKeys.length; i++) {
        const seatKey = seatKeys[i];
        if (!seatInfo[seatKey].nickname) {
          alert("빈 자리가 존재합니다.");
          canStart = false;
          return;
          break;
        }
      }

      for (let i = 1; i < seatKeys.length; i++) {
        const seatKey = seatKeys[i];
        if (!seatInfo[seatKey].ready) {
          alert("모두 준비완료가 되지 않았습니다.");
          canStart = false;
          break;
        }
      }

      if(!canStart) return;
      // 게임 시작 가능한 상태인지 확인 (끝)

      // 게임 방 사람에게 알린다.
      pubRoom(
        "/pub/room/" + useUserStore().currentRoomInfo.roomCode + "/start",
        useUserStore().userInfo.email
      );
    },

    // 준비완료
    doReady() {
      pubRoom(
        "/pub/room/" + useUserStore().currentRoomInfo.roomCode + "/ready",
        useUserStore().userInfo.email
      );
    },

    // 나갈 때 로직
    goTolist() {
      const confirmMessage = "정말 나가시겠습니까?";

      if (confirm(confirmMessage)) {
        // 방을 나가기 위해 pub로 알린다.
        pubRoom(
          "/pub/room/" + useUserStore().currentRoomInfo.roomCode + "/exit",
          useUserStore().userInfo.email
        );

        // 구독 취소한 뒤 방 정보에 대해 모두 리셋한다.
        useRoomStore().subscription.room.unsubscribe();
        const initialStateRoom = useRoomStore().$reset();
        Object.assign(this, initialStateRoom);

        this.isOut = true;

        this.$router.push({ name: "home" });
      }
    },

    // 새로고침 방지
    leave(event) {
      // 나가기로 알림.
      pubRoom(
        "/pub/room/" + useUserStore().currentRoomInfo.roomCode + "/exit",
        useUserStore().userInfo.email
      );

      // 구독 취소한 뒤 방 정보에 대해 모두 리셋한다.
      useRoomStore().subscription.room.unsubscribe();
      useUserStore().initData();

      // 홈으로 이동
      event.preventDefault();
      event.returnValue = "홈으로..";
      window.location.href = "/home";
      return event.returnValue;
    },
  },

  mounted() {
    // 새로고침 방지 이벤트를 추가한다.
    window.addEventListener("beforeunload", this.leave);
  },

  // mounted에 설정한 새로고침 방지 이벤트 리스너를 삭제한다.
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.leave);
  },
};
</script>

<style scoped>
@import "@/assets/css/room/waitingRoom.css";
</style>