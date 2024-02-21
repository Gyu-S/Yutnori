<template>
  <div class="notification-container">
    <div v-for="friend in friendRequests" :key="friend.toUserEmail" class="friend-invite">
      <div class="frined-invite">
        <h4 class="title">친구 초대가 왔습니다.</h4>
        <p class="content">{{ friend.fromUserNickname }}님으로부터 친구 신청이 왔습니다.</p>
        <button class="accept" @click="acceptFriendRequest(friend)">수락</button>
        <button class="refuse" @click="rejectFriendRequest(friend)">거절</button>
      </div>
    </div>
  
    <!-- <div v-for="chat in chatMessages" :key="chat" class="chat-notification">
      <div class="chating" @click="goChat(chat)">
        <h4 class="title">채팅이 왔습니다</h4>
        <p class="content">{{ chat }}님으로부터 채팅이 왔습니다. 확인해보세요!</p>
      </div>
    </div> -->
  </div>
</template>

<script>
import { acceptFriend, rejectFriend } from "@/api/friend";
import { useFriendStore } from '@/store/friendStore';

export default {
  data() {
    return {
    };
  },

  computed: {
    friendRequests() {
      return useFriendStore().friendRequests
    },

    chatMessages() {
      return Array.from(useFriendStore().chatAlert)
    },
  },

  methods: {

    // 친구 요청 수락
    async acceptFriendRequest(user) {
      try {
        const response = await acceptFriend(user);
        alert('친구 요청을 수락했습니다.')
        const userIndex = this.friendRequests.findIndex(obj => obj.toUserEmail === user.toUserEmail)
        this.friendRequests.splice(userIndex, 1)
      } catch (error) {
        console.log("친구 요청 수락 중 에러 발생", error)
      }
    },

    // 친구 요청 거절
    async rejectFriendRequest(user) {
      try {
        const response = await rejectFriend(user);
        alert('친구 요청을 거절했습니다.')
        const userIndex = this.friendRequests.findIndex(obj => obj.toUserEmail === user.toUserEmail)
        this.friendRequests.splice(userIndex, 1)
      } catch (error) {
        console.log("친구 요청 수락 중 에러 발생", error)
      }
    },

    // 채팅
    async goChat(user) { 
      const friends = useFriendStore().friends
      const friend = friends.find(obj => obj.nickname === user)
      this.$emit('open-chat-room', friend)
      this.useFriendStore().chatAlert.delete(user)
    },
  },

  mounted() {
    useFriendStore().receiveFriendRequest()
  }
}
</script>

<style scoped>
  @import "@/assets/css/layout/message.css";
</style>