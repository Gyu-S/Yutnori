<template>
  <!-- 패치노트 모달창 -->
  <div class="modal">
    <div class="modal-content">
      <font-awesome-icon :icon="['fas', 'x']" style="color: #ffffff;" class="close" @click="closeModal('patch')" />
      <h2 class="title">패치노트</h2>
      <br />
      <p v-for="patch in patches">
        {{ patch.created_date}} {{ patch.subject }}
      </p>
      <br />
    </div>
  </div>
</template>

<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { faX } from '@fortawesome/free-solid-svg-icons';
library.add(faX)

// 패치노트
import { getPetch } from "@/api/petch";

export default {
  data() {
    return {
      patches: []
    };
  },
  methods: {
    closeModal(modalId) {
      this.$emit("close-modal", modalId);
    },
  },

  mounted() {
    getPetch((response) => {
      if (response && response.data) {
        this.patches = response.data
      }
    }, (error) => {
      console.log(error)
    })
  }
};
</script>

<style scoped>
@import url("../../assets/css/home/patch.css")
</style>