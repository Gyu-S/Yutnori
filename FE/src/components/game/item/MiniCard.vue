<template>
  <div class="card-game">
    <span class="card-title">카드를 선택하시오.</span>
    <div class="card-container">
      <div
        v-for="card in cards"
        :key="card.value"
        class="card-item"
        @click="selectCard(card)"
        :class="{'card-select' : selectedCardValue === card.value, }"
      >
        <img class="card-img" 
          src="@/assets/img/game/taegeuk.png" />
      </div>
    </div>
    <button class="card-select-btn" @click="finalSelect">선택</button>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      cards: [
        { name: "파리잡기", value: 1 },
        { name: "케이크먹기", value: 2 },
        // { name: "광물때리기", value: 3 },
        { name: "참참참", value: 3 },
      ],
      selectedCard: null,
      selectedCardValue: null,
    };
  },
  methods: {
    shuffleCards() {
      this.cards = this.cards
        .map((a) => ({ sort: Math.random(), value: a }))
        .sort((a, b) => a.sort - b.sort)
        .map((a) => a.value);
    },
    selectCard(card) {
      this.selectedCard = card;
      this.selectedCardValue = card.value
    },
    finalSelect(){
      if(this.selectedCard !== null){
        this.$emit('selectCard',this.selectedCard);
      }
    }
  },
};
</script>
  
  <style>
@import "@/assets/css/game/miniCard.css";
</style>
  