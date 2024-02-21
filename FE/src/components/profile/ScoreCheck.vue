<template>
  <!-- 나중에 v-for 돌리면 됨. 템플릿만 짜놓은 것임 ! -->
  <div class="container">
    <div class="box">
      <div class="left-section">
        <p>{{ gameInfo.hour }}시간전</p>
        <p>{{ gameInfo.victory }}</p>
        <p>{{ gameInfo.minute }}분 {{ gameInfo.seconds }}초</p>
      </div>
      <div class="img-section">
        <img src="@/assets/img/king.png" alt="character" class="character">
      </div>
      <div class="middle-section">
        <p><strong>미션 성공률</strong> {{ gameInfo.percent }}%</p>
        <p><strong>평균 윷</strong> {{ gameInfo.average }}%</p>
        <p><strong>말 잡은 수</strong> {{ gameInfo.catch }}%</p>
      </div>
    </div>

    
    <div class="right-section">
      <img src="@/assets/img/play.png" alt="play-button">
    </div>
  </div>

  <div class="container">
    <div class="box">
      <div class="left-section">
        <p>{{ gameInfo.hour }}시간전</p>
        <p>{{ gameInfo.victory }}</p>
        <p>{{ gameInfo.minute }}분 {{ gameInfo.seconds }}초</p>
      </div>
      <div class="img-section">
        <img src="@/assets/img/king.png" alt="character" class="character">
      </div>
      <div class="middle-section">
        <p><strong>미션 성공률</strong> {{ gameInfo.percent }}%</p>
        <p><strong>평균 윷</strong> {{ gameInfo.average }}%</p>
        <p><strong>말 잡은 수</strong> {{ gameInfo.catch }}%</p>
      </div>
    </div>

    
    <div class="right-section">
      <img src="@/assets/img/play.png" alt="play-button">
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      gameInfo: {
          hour: 1,
          victory: '승리',
          minute: 25,
          seconds: 28,
          percent: 75,
          average: 3.8,
          catch: 2,
      },
    };
  },
};
</script>

<style scoped>
@import "../../assets/css/profile/scoreCheck.css";
</style>