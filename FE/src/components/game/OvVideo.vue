<template>
  <video autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  data(){
    return{
      priority: null,
    }
  },

  mounted() {
    const data = JSON.parse(this.streamManager.stream.connection.data);
    const jsonData = JSON.parse(data.data);
    const teamValue = jsonData.team;
    
    if (teamValue === 1) {
      this.$el.style.border = "5px solid red";
    } else {
      this.$el.style.border = "5px solid blue";
    }

    this.streamManager.addVideoElement(this.$el);
  },
};
</script>

<style scoped>
video {
  max-width: 25vw;
  max-height: 16vh;
  border: 5px solid red;
}
</style>
