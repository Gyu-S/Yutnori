<template>
  <!-- 패치노트 모달창 -->
  <div class="modal">
    <div class="modal-content">
      <h2 class="title">밀정을 선택하세요.</h2>
      <div class="spy-container-wrapper">
        <div
          v-for="character in filteredCharacters"
          :key="character.selectUnitId"
          @click="openModal(character)"
        >
          <img
            :class="{
              'character-list-selected': selectedCharacter === character,
            }"
            :src="getCharacterImage(character.unitId)"
            class="character-list"
          />
          <p class="character-name">{{ character.name }}</p>
        </div>
      </div>

      <div class="modal-btn">
        <div class="select-container">
          <transition name="fade">
            <button
              v-if="isSelected"
              @click="selectComplete"
              class="select-ready"
            >
              선택 완료
            </button></transition
          >
        </div>
      </div>
    </div>
  </div>
</template>
    
<script>
import { library } from "@fortawesome/fontawesome-svg-core";
import { faX } from "@fortawesome/free-solid-svg-icons";
library.add(faX);

import { pubPickInfo } from "@/util/socket";

import { usePickStore } from "@/store/pickStore";
import cavalry from "@/assets/img/game/pick/cavalry.png";
import peasant from "@/assets/img/game/pick/peasant.png";
import slave from "@/assets/img/game/pick/slave.png";
import spearman from "@/assets/img/game/pick/spearman.png";
import { useUserStore } from "@/store/userStore";

export default {
  data() {
    return {
      selectedCharacter: null,
      isSelected: true,
    };
  },
  methods: {
    // 선택한 유닛 아이디 확인
    getCharacterImage(selectedUnitId) {
      if (selectedUnitId === 1) {
        return king;
      } else if (selectedUnitId === 2) {
        return spearman;
      } else if (selectedUnitId === 3) {
        return cavalry;
      } else if (selectedUnitId === 4) {
        return peasant;
      } else if (selectedUnitId === 5) {
        return slave;
      }
    },

    openModal(character) {
      this.selectedCharacter = character;
    },

    selectComplete() {
      const teamName = usePickStore().code.includes("red") ? "홍팀" : "청팀";

      const content = {
        team: teamName,
        unitId: this.selectedCharacter.unitId,
      };

      pubPickInfo(
        "/pub/pick/" + useUserStore().currentRoomInfo.roomCode + "/spy",
        content
      );

      this.isSelected = false;
    },
  },
  computed: {
    // unitInfo 반환하는 computed 속성
    getUnitInfo() {
      return usePickStore().unitInfo;
    },
    filteredCharacters() {
      return this.getUnitInfo.filter((character) => character.name !== "대왕");
    },
  },
};
</script>
  
  
<style scoped>
/* 모달 애니메이션 */
.modal {
  /* 기본 스타일 유지 */
  border-color: black;
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
  position: fixed;
  left: 0;
  top: 0px;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.879);
  z-index: 3;
}

.modal-content {
  /* 기본 스타일 유지 */
  background-color: #00000035;
  margin: 5% auto;
  padding: 20px;
  border: 2px solid #888;
  border-color: white;
  border-radius: 30px;
  width: 50vw;
  height: 40vh;
  text-align: center;
  /* 애니메이션 설정 */
  transform: translateY(-50px);
  transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
}

.title {
  margin-bottom: 20px;
  top: -40px; /* 위로 20픽셀 이동, 필요에 따라 조절 가능 */
  color: white;
  font-family: "Palatino Linotype", "Book Antiqua", palatino, serif; /* Press Start 2P 글꼴 적용 */
}

.spy-container-wrapper {
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
}

.character-list {
  width: 7vw;
  height: 14vh;
  margin: 0px 20px;
  border: 3px solid black;
  border-radius: 50px;
}

.character-list:hover {
  border: 4px solid red;
}

.character-list-selected {
  border: 4px solid red;
}

.character-name {
  color: rgb(255, 255, 255);
  font-weight: bold;
  flex: 1;
  text-align: center;
  font-size: 20px;
}

.select-ready {
  border: 2px solid rgb(255, 255, 255);
  border-radius: 20px;
  font-weight: bold;
}

.select-ready:hover {
  border: 3px solid rgb(255, 0, 0);
}
</style>
  