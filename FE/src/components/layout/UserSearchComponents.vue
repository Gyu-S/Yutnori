<template>
  <div class="search-main">
    <form class="search-form" @submit.prevent="searchUserInfo(searchName)">
      <label for="search-input"></label>
      <input
        v-model="searchName"
        placeholder="닉네임을 입력하시오"
        type="text"
        id="search-input"
        class="search-input"
      />
      <font-awesome-icon
        :icon="['fas', 'magnifying-glass']"
        style="color: #000000"
        class="search-btn"
        @click="searchUserInfo(searchName)"
      />
    </form>
    <div v-if="searchedUser.length > 0">
      <div v-for="user in searchedUser" class="search-output">
        <img src="@/assets/img/profile.png" alt="friend-profile" class="friend-profile">
        <h4>{{ user.nickname }}님</h4>
        <button class="friend-plus-btn" @click="plusFriend(user)">친구 추가</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useUserStore } from "@/store/userStore";
import { searchUser, requestFriend } from "@/api/friend";
import { sendEvent } from '@/util/socket.js';

export default {
  data() {
    return {
      searchName: "",
      searchedUser: []
    };
  },
  methods: {
    async searchUserInfo(searchName) {
      try {
        await searchUser(searchName, (response) => {
          this.searchedUser = response.data
        })
      } catch (error) {
        console.log(error);
      }
    },

    async plusFriend(user) {
      const userStore = useUserStore();
      const userInfo = {
        toUserEmail: user.email,
        toUserNickname: user.nickname
      }
      try {
        await requestFriend(userInfo, (response) => {
          alert('친구 추가에 성공했습니다.')
          const event = {
            fromUserEmail: userStore.userInfo.email, 
            toUserEmail: user.email,
            eventCategory: '2',
            eventAction: 'REQUEST',
            message: `${userStore.userInfo.nickname}님이 친구 추가를 요청했습니다.`,
          }
          sendEvent(event)
          }, (error) => {
            console.log('친구 요청 보내기 실패', error)
          }) 
        } catch (error) {
          console.log('친구 요청 보내기 실패', error)
          alert('친구 추가에 에러가 발생했습니다.')
      }
    }
  },
};
</script>

<style>
@import "@/assets/css/layout/search.css";
</style>