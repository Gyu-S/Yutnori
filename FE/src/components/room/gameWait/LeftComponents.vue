<template>
  <div class="wait-left">
    <!-- 백에서 받아온 정보를 for문을 돌려 나타낸다. -->
    <div
      v-for="team in teams"
      :key="team.name"
      :class="{
        'team-container': true, // 큰 틀
        'team-red': team.name === '홍팀', // 홍팀
        'team-blue': team.name === '청팀', // 청팀 의 색을 다르게 하기 위해 선언
      }"
    >
      <!-- 팀 이름을 나타낸다. -->
      <div class="team-name" @click="changeBtn(team.name)">{{ team.name }}</div>

      <!-- 유저의 정보를 나타낸다 -->
      <div
        v-for="seatKey in Object.keys(team.users)"
        :key="seatKey"
        class="team-container"
      >
        <div v-if="team.users[seatKey].nickname === ''" class="per">
          <div class="img-div">
            <img src="@/assets/img/profile_picture.png" class="team-img" />
          </div>
          <div class="text-container">
          </div>
          <div class="ready-div"></div>
        </div>
        <div v-else class="per">
          <div class="img-div">
            <img src="@/assets/img/profile_picture.png" class="team-img" />
          </div>
          <div class="text-container">
            <span>{{ team.users[seatKey].nickname }}</span>
          </div>

          <div class="ready-div">
            <span v-if="team.users[seatKey].ready">준비완료</span>
            <span v-else-if="team.users[seatKey].state === 2" id="owner"
              >방장</span
            >
            <span v-else>레디하지 않음</span>
          </div>
        </div>
      </div>
    </div>
    <GameWaitChatVue />
  </div>
</template>

<script>
import { useRoomStore } from "@/store/roomStore";
import { pubRoom } from "@/util/socket"

// 아이콘
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons/faUserPlus";
library.add(faUserPlus);

// 자식 컴포넌트
import GameWaitChatVue from "./GameWaitChat.vue";
import { useUserStore } from '@/store/userStore';

export default {
  components: {
    GameWaitChatVue,
  },

  data() {
    return {
      // 팀 정보 기본 세팅
      teams: [
        {
          name: "홍팀",
          users: [],
          maxUser: 3,
        },
        {
          name: "청팀",
          users: [],
          maxUser: 3,
        },
      ],
    };
  },

  created() {
    // seatInfo 데이터를 가져와서 팀에 할당
    let currentSeatInfo = this.getSeatInfo;

    // 홍팀에 1부터 3까지의 seatInfo 할당
    for (let i = 1; i <= 3; i++) {
      this.teams[0].users[`seatnum${i}`] = currentSeatInfo[`seatnum${i}`];
    }

    // 청팀에 4부터 6까지의 seatInfo 할당
    for (let i = 4; i <= 6; i++) {
      this.teams[1].users[`seatnum${i}`] = currentSeatInfo[`seatnum${i}`];
    }
  },

  methods: {
    changeBtn(name) {
      let idx = 0;

      for(let i=1; i<=6; i++){
        if(useUserStore().userInfo.nickname === useRoomStore().seatInfo[`seatnum${i}`].nickname){
          idx = i;
          break;
        }
      }

      if(name === "홍팀"){
        if(useRoomStore().seatInfo[`seatnum${idx}`].team === 1) return;
      }else{
        if(useRoomStore().seatInfo[`seatnum${idx}`].team === 2) return;
      }

      pubRoom("/pub/room/"+useUserStore().currentRoomInfo.roomCode+"/change",
      useUserStore().userInfo.email);
    },
  },

  computed: {
    getSeatInfo() {
      return useRoomStore().seatInfo;
    },
  },
};
</script>

<style scoped>
@import "../../../assets/css/room/waitingRoomLeft.css";
</style>