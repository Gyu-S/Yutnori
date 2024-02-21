<template>
  <!-- 패치노트 모달창 -->
  <div class="modal">
    <div class="modal-content">
      <font-awesome-icon :icon="['fas', 'x']" style="color: #ffffff;" class="close" @click="closeModal('patchDetail')" />
      <h2 @click="showMe" class="title">{{patchDetail.subject}}</h2>
      <p style="color: #ffffff;" v-html="patchDetail.content"></p>
    </div>
  </div>
</template>

<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { faX } from '@fortawesome/free-solid-svg-icons';
library.add(faX)

export default {
  props: {
    patchDetail: Object
  },

  methods: {
    closeModal(modalId) {
      this.$emit("close-modal", modalId);
    },

    showMe() {
      console.log(this.patchDetail)
    }
  },
};
</script>

<style scoped>
@import url("../../assets/css/home/patch.css")
</style>