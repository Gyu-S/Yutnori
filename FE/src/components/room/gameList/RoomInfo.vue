<template>
  <!-- 방 클릭했을 때 나오는 상세 정보 -->
  <div class="room-detail">
    <div class="room-detail-content">
      <div class="room-detail-info">
        <p class="room-detail-subtitle">방 제목</p>
        <p>{{ roomInfo.title }}</p>
        <p class="room-detail-subtitle">공개</p>
        <p v-if="roomInfo.public">공개</p>
        <p v-else>비공개</p>
        <p class="room-detail-subtitle">속도</p>
        <p v-if="roomInfo.gameSpeed === 0">느림</p>
        <p v-else-if="roomInfo.gameSpeed === 1">보통</p>
        <p v-else-if="roomInfo.gameSpeed === 2">빠름</p>
        <p class="room-detail-subtitle">인원</p>
        <p>{{ roomInfo.currentUserCount }} / 6</p>
      </div>
      <div class="room-detail-theme">
        <div>
          <img src="@/assets/img/playboard.png" alt="playboard" />
        </div>
        <div>
          <p class="room-detail-subtitle">맵 테마</p>
          <p>{{ roomInfo.theme }}</p>
        </div>
      </div>
    </div>
    <!-- 예시 방 디테일일 때는 참가 버튼 없애기 -->
    <button @click="join">참가</button>
  </div>
</template>

<script>
import { useRoomStore } from "@/store/roomStore";
import { useUserStore } from "@/store/userStore";

import { connectRoom, pubRoom } from "@/util/socket";

export default {
  // 부모로부터 받아온 방 상세정보 데이터
  props: {
    roomInfo: Object,
  },

  data() {
    return {};
  },

  methods: {
    join() {
      const roomInfo = useRoomStore().roomDetailData;

      if (!roomInfo.public) {
        useRoomStore().openModal("roomPasswordCheck");
      }

      useRoomStore()
        .canEnterRoom()
        .then(() => {
          connectRoom("Room", this.$router, "enter").then(() => {
            // 현재, 자신의 방의 정보를 넣는다.
            // (나갈 때 정보 삭제 필요)
            useUserStore().currentRoomInfo = null;
            useUserStore().currentRoomInfo = {
              ...useRoomStore().roomDetailData,
            };

            pubRoom(
              "/pub/room/" + useUserStore().roomCode + "/enter",
              useUserStore().userInfo.email
            );

            useUserStore().currentRoomInfo.roomCode = useUserStore().roomCode;

            this.$router.push({ name: "wait" });
          });
        });
    },
  },
};
</script>

<style scoped>
@import "../../../assets/css/room/roomInfo.css";
</style>