<template>
  <video autoplay />
</template>

<script>
export default {
  name: "OvVideo",
  
  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>

<style scoped>
video {
  max-width: 25vw;
  max-height: 25vh;
}
</style>