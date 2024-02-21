<template>
  <div class="game-board">
    <!-- 여기에 추리 말 선택 모달이 나와야한다. -->
    <GameSpySelect v-if="reasoningChoose" />
    <GameHorseSelect v-if="isHorseSelect" />
    <!-- 방향 전환 시 모달로 선택 -->
    <GameModal
      v-if="isShowReasoning || isShowGoDig"
      :modalType="modalType"
      @trueMethod="goDigYes"
      @falseMethod="goDigNo"
      @useTicket="useTicket"
      @notUseTicket="notUseTicket"
    />
    <!-- 차례 메시지  -->
    <div v-if="isShowTurnMessage" class="game-board-turn-message-container">
      <span class="game-board-turn-message">{{ turnMessage }}</span>
    </div>
    <!-- 경고 메시지  -->
    <div
      v-if="isShowWarningMessage"
      class="game-board-warning-message-container"
    >
      <span>{{ warningMessage }}</span>
      <span class="game-board-warning-message">{{ warningMessageSecond }}</span>
    </div>

    <!-- 힌트 메시지 -->
    <div
      class="game-board-hint-container"
      @mouseover="hintMouseOver"
      @mouseleave="hintMouseLeave"
    >
      <img class="game-board-hint-container-img" src="@/assets/icon/hint.png" />
      <div v-if="isShowHint" class="game-board-hint-text-container">
        <div class="game-board-hint-text">
          {{ mySpyHint }}
        </div>
      </div>
    </div>

    <!-- 타이머 -->
    <div class="game-board-timer-container">
      <span
        class="game-board-timer"
        :class="{ 'game-board-timer-five': timerCheck <= 5 }"
        >{{ timerCheck }}</span
      >
    </div>
    <!-- 팀당 다음차례 -->
    <div class="game-board-team-turn">
      <div class="game-board-team-turn-container">
        <span class="game-board-team-turn-title">현재 차례</span>
        <span
          :class="{
            'game-board-team-turn-red': !teamTurn,
            'game-board-team-turn-blue': teamTurn,
          }"
          >{{ nowTunrName }}</span
        >
      </div>
      <div class="game-board-team-turn-container">
        <span class="game-board-team-turn-title">다음 차례</span>
        <span
          :class="{
            'game-board-team-turn-red': teamTurn,
            'game-board-team-turn-blue': !teamTurn,
          }"
          >{{ nextTurnName }}</span
        >
      </div>
    </div>
    <div class="game-board-tile">
      <!-- 윷 던진 결과 -->
      <div class="game-yut-text-div" v-show="isShowResText">
        <span class="game-yut-res-text">{{ yutText }}</span>
      </div>
      <div class="game-yut-res" v-show="isShowRes">
        <GameYut ref="yutThrow" />
      </div>
      <!-- 윷판 -->
      <GameBoardTile v-for="(tile, index) in tiles" :key="index" :tile="tile" />
    </div>
    <!-- 홍팀 말 -->
    <GameHorse
      v-for="(horse, index) in redHorses"
      :key="horse.id"
      :horse="horse"
      :index="index"
      @selectHorse="selectHorse"
    />
    <!-- 청팀 말 -->
    <GameHorse
      v-for="(horse, index) in blueHorses"
      :key="horse.id"
      :horse="horse"
      :index="index"
      @selectHorse="selectHorse"
    />

    <!-- 버튼 임시 -->
    <button
      class="game-board-throw-btn"
      @click="moveHorse"
      :disabled="!isThrowYut"
    >
      던지기
    </button>
  </div>
</template>
                                                                        
<script>
import { useGameStore } from "@/store/gameStore";
import { useUserStore } from "@/store/userStore";
import { socketSend } from "@/util/socket.js";
import GameBoardTile from "./item/GameBoardTile.vue";
import GameHorse from "./item/GameHorse.vue";
import GameYut from "./item/GameYut.vue";
import GameModal from "./item/GameModal.vue";
import GameSpySelect from "./item/GameSpySelect.vue";
import GameHorseSelect from "./item/GameHorseSelect .vue";

export default {
  components: {
    GameBoardTile,
    GameHorse,
    GameYut,
    GameModal,
    GameSpySelect,
    GameHorseSelect,
  },
  mounted() {
    // this.connectSocket();
    // gameConnect();
    console.log("게임 시작");
    setTimeout(() => {
      this.gameStart();
    }, 5000);
  },
  data() {
    return {
      selectedHorse: null, // 선택한 말
      isSelectedHorse: false, // 말을 선택했는지.
      canSelectHorse: false, // 말을 선택할 수 있을 때
      isShowRes: true, // 윷 던지고 결과 화면 보여줄 때.
      isShowGoDig: false, // 대각선으로 갈지 말지 선택.
      isShowResText: false, // 윷결과 텍스트 출력
      isHorseSelect: false, // 미션 성공 시 말 선택.
      isShowHint: false,
      yutText: "", // 윷 결과 문자.
      goModalText1: "",
      goModalText2: "",
      recvList: [],
      // 모달 타입 1=> 대각선 2=> 가운데 방향 3=> 추리.
      modalType: 2,
      // 경고 메시지
      isShowWarningMessage: false,
      warningMessage: "홍팀이 밀정잡이에 실패하였습니다.",
      warningMessageSecond: "해당말은 이제 처형입니다. 윷을 한번 더 던지세요!!",
    };
  },
  computed: {
    receivedMsg() {
      const gameStore = useGameStore();
      return gameStore.receivedMsg;
    },
    // 홍팀 말
    redHorses() {
      const gameStore = useGameStore();
      return gameStore.redHorses;
    },
    // 청팀 말
    blueHorses() {
      const gameStore = useGameStore();
      return gameStore.blueHorses;
    },
    // 타일
    tiles() {
      const gameStore = useGameStore();
      return gameStore.tiles;
    },
    // 턴 체크.
    isThrowYut() {
      const gameStore = useGameStore();
      return !this.isShowReasoning &&
        gameStore.isThrowYut &&
        gameStore.throwChance > 0
        ? true
        : false;
    },
    // 타이머 체크
    timerCheck() {
      const gameStore = useGameStore();
      if (gameStore.timer === 0 && gameStore.isThrowYut) {
        // 말 선택
        if (this.canSelectHorse) {
          this.randomHorse();
        }
        // 윷 던지기
        else {
          this.moveHorse();
        }
      }
      return gameStore.timer;
    },
    // 방 코드
    roomCode() {
      const userStore = useUserStore();
      console.log(userStore.currentRoomInfo.roomCode);
      return userStore.currentRoomInfo.roomCode;
      // return "720ca5";
    },
    // 차례 메시지
    turnMessage() {
      const gameStore = useGameStore();
      return gameStore.turnMessage;
    },
    // 차례 메시지 Flag
    isShowTurnMessage() {
      const gameStore = useGameStore();
      return gameStore.isShowTurnMessage;
    },
    // 현재 차례
    nowTunrName() {
      const gameStore = useGameStore();
      // 홍팀
      if (!gameStore.teamTurn) return "홍팀 : " + gameStore.redTurnName + "님";
      else return "청팀 : " + gameStore.blueTurnName + "님";
    },
    // 다음 차례
    nextTurnName() {
      const gameStore = useGameStore();
      if (!gameStore.teamTurn) return "청팀 : " + gameStore.blueTurnName + "님";
      else return "홍팀 : " + gameStore.redTurnName + "님";
    },
    teamTurn() {
      const gameStore = useGameStore();
      return gameStore.teamTurn;
    },
    // 추리 선택 모달 Flag
    isShowReasoning() {
      const gameStore = useGameStore();
      if (gameStore.isShowReasoning) {
        // 내 차례
        if (gameStore.isThrowYut) {
          this.modalType = 3;
        }
        // 홍팀
        else if (!gameStore.teamTurn) {
          // 같은 팀 차례.
          if (gameStore.myTeam === 1) {
            this.modalType = 4;
          }
          // 다른 팀 차례.
          else {
            this.modalType = 5;
          }
        }
        // 청팀
        else {
          // 같은 팀 차례.
          if (gameStore.myTeam === 2) {
            this.modalType = 4;
          }
          // 다른 팀 차례.
          else {
            this.modalType = 5;
          }
        }
      }
      return gameStore.isShowReasoning;
    },
    reasoningChoose() {
      const gameStore = useGameStore();
      return gameStore.reasoningChoose;
    },
    // 힌트 메시지
    mySpyHint() {
      const gameStore = useGameStore();
      console.log("스파이 힌트 : " + gameStore.mySpyHint);
      return gameStore.mySpyHint;
    },
  },
  watch: {
    receivedMsg(newVal) {
      console.log(newVal);
      const gameStore = useGameStore();
      switch (newVal.actionCategory) {
        case 1:
          if (!gameStore.isThrowYut) {
            this.receiveYutRes();
          }
          break;
        case 2: // 말 이동.
          this.receiveSelectHorse();
          // boolean값들 초기화.
          this.isSelectedHorse = false;
          this.canSelectHorse = false;
          gameStore.isSelect = false;
          break;
        // 추리
        case 3:
          gameStore.reasoningChoose = false;

          // 성공
          if (newVal.spy) {
            this.warningMessage = gameStore.teamTurn ? "청팀" : "홍팀";
            this.warningMessage =
              this.warningMessage + "이 밀정잡이에 성공하였습니다.";

            if (newVal.team === gameStore.myTeam) {
              gameStore.isFindSpy = true;
            } else {
              gameStore.isEnemyFindSpy = true;
            }

            // 해당 말이 밀정이면
            if (newVal.team === 1) {
              for (let i = 0; i < gameStore.redHorses.length; i++) {
                if (newVal.selectedUnit === gameStore.redHorses[i].id) {
                  gameStore.redHorses[i].kill = true;
                  gameStore.redHorses[i].status = "end";
                  gameStore.redHorses[i].index = 0;
                  gameStore.redEnd += 1;
                  this.warningMessageSecond = gameStore.redHorses[i].name;
                  break;
                }
              }
            } else {
              for (let i = 0; i < gameStore.blueHorses.length; i++) {
                if (newVal.selectedUnit === gameStore.blueHorses[i].id) {
                  gameStore.blueHorses[i].kill = true;
                  gameStore.blueHorses[i].status = "end";
                  gameStore.blueHorses[i].index = 0;
                  gameStore.blueEnd += 1;
                  this.warningMessageSecond = gameStore.blueHorses[i].name;
                  break;
                }
              }
            }

            this.warningMessageSecond =
              this.warningMessageSecond +
              "말을 사살했습니다.. 윷을 한번 더 던지세요!!";
            this.isShowWarningMessage = true;

            setTimeout(() => {
              this.isShowWarningMessage = false;
              gameStore.startTimer();
            }, 2000);
          }
          // 실패
          else {
            this.warningMessage = gameStore.teamTurn ? "청팀" : "홍팀";
            this.warningMessage =
              this.warningMessage + "이 밀정잡이에 실패하였습니다.";
            // 밀정이 아니면. 3턴 이동 금지
            if (newVal.team === 1) {
              for (let i = 0; i < gameStore.redHorses.length; i++) {
                if (newVal.selectedUnit === gameStore.redHorses[i].id) {
                  gameStore.redHorses[i].stun += 3;
                  this.warningMessageSecond = gameStore.redHorses[i].name;
                  break;
                }
              }
            } else {
              for (let i = 0; i < gameStore.redHorses.length; i++) {
                if (newVal.selectedUnit === gameStore.blueHorses[i].id) {
                  gameStore.blueHorses[i].stun += 3;
                  this.warningMessageSecond = gameStore.blueHorses[i].name;
                  break;
                }
              }
            }

            this.warningMessageSecond =
              this.warningMessageSecond + "말은 3턴간 이동이 불가합니다...";
            this.isShowWarningMessage = true;

            // 여유.
            setTimeout(() => {
              this.isShowWarningMessage = false;
              gameStore.turnChange();
            }, 2000);
          }
          break;
        // 추리권 사용
        case 4:
          gameStore.isShowReasoning = false;
          if (newVal.reasoningChoose) {
            gameStore.reasoningChoose = true;
            if (gameStore.teamTurn) {
              if (gameStore.myTeam === 2) gameStore.ticket -= 1;
              else gameStore.enemyTicket -= 1;
            } else {
              if (gameStore.myTeam === 1) gameStore.ticket -= 1;
              else gameStore.enemyTicket -= 1;
            }
          } else {
            gameStore.startTimer();
          }
          break;
        case 5:
          this.warningMessage = "";
          this.warningMessageSecond = "";
          let spyIndex = null;
          let arr = newVal.unitId;
          for (let i = 0; i < 5; i++) {
            if (
              arr.includes(
                newVal.team === 1 ? this.redHorses[i].id : this.blueHorses[i].id
              )
            ) {
              this.warningMessage +=
                (newVal.team === 1
                  ? this.redHorses[i].name
                  : this.blueHorses[i].name) + " ";
            }
            if (newVal.spy) {
              if (
                arr.includes(
                  newVal.team === 1
                    ? this.redHorses[i].id
                    : this.blueHorses[i].id
                )
              ) {
                spyIndex = i;
              }
            }
          }
          this.warningMessage = this.warningMessage + "말이 도착하였습니다.";
          if (newVal.spy) {
            this.warningMessageSecond =
              newVal.team === 1
                ? this.redHorses[spyIndex].name
                : this.blueHorses[spyIndex].name +
                  "말이 밀정이여서 게임이 종료되었습니다!!";
            this.isShowWarningMessage = true;
            setTimeout(() => {
              this.isShowWarningMessage = false;
              gameStore.spyGoal = true;
            }, 2000);
          } else {
            this.isShowWarningMessage = true;
            setTimeout(() => {
              this.isShowWarningMessage = false;

              if (gameStore.throwChance === 0) {
                gameStore.turnChange();
              } else {
                gameStore.startTimer();
              }
            }, 2000);
          }
          break;
        // 미션 시작.
        case 7:
          console.log("미션 시작 board");
          break;
        // 미션 결과
        case 8:
          console.log("미션 종료 board");
          gameStore.isMission = false;
          this.warningMessage = "미션이 종료되었습니다.";
          if (newVal.result) {
            this.warningMessageSecond = "미션에 성공하여 말을 선택해 주세요.";
            this.isShowWarningMessage = true;
            setTimeout(() => {
              this.isShowWarningMessage = false;
              this.isHorseSelect = true;
            }, 3000);
          } else {
            this.warningMessageSecond =
              "미션에 실패하여 다음 턴으로 넘어갑니다.";
            this.isShowWarningMessage = true;
            setTimeout(() => {
              this.isShowWarningMessage = false;
              gameStore.missionEnd();
            }, 3000);
          }
          break;
        // 미션 성공 후 힌트 얻기
        case 10:
          this.warningMessage = newVal.team === 2 ? "청팀 " : "홍팀 ";
          this.isHorseSelect = false;
          // 해당 말이 밀정이면
          if (newVal.team === 1) {
            for (let i = 0; i < gameStore.redHorses.length; i++) {
              if (newVal.unitId === gameStore.redHorses[i].id) {
                switch (newVal.category) {
                  case 1:
                    gameStore.redHorses[i].scal = newVal.hint;
                    break;
                  case 2:
                    gameStore.redHorses[i].stuff = newVal.hint;
                    break;
                  case 3:
                    gameStore.redHorses[i].contactor = newVal.hint;
                    break;
                  case 4:
                    gameStore.redHorses[i].time = newVal.hint;
                    break;
                  case 5:
                    gameStore.redHorses[i].place = newVal.hint;
                    break;
                }
                this.warningMessage += gameStore.redHorses[i].name;
                break;
              }
            }
          } else {
            for (let i = 0; i < gameStore.blueHorses.length; i++) {
              if (newVal.unitId === gameStore.blueHorses[i].id) {
                switch (newVal.category) {
                  case 1:
                    gameStore.blueHorses[i].scal = newVal.hint;
                    break;
                  case 2:
                    gameStore.blueHorses[i].stuff = newVal.hint;
                    break;
                  case 3:
                    gameStore.blueHorses[i].contactor = newVal.hint;
                    break;
                  case 4:
                    gameStore.blueHorses[i].time = newVal.hint;
                    break;
                  case 5:
                    gameStore.blueHorses[i].place = newVal.hint;
                    break;
                }
                this.warningMessage += gameStore.blueHorses[i].name;
                break;
              }
            }
          }

          this.warningMessage += "말에 대한 힌트를 얻었습니다.";
          this.isShowWarningMessage = true;

          setTimeout(() => {
            this.isShowWarningMessage = false;
            this.warningMessage = "";
            gameStore.missionEnd();
          }, 3000);
          break;
      }
    },
  },
  methods: {
    hintMouseOver() {
      this.isShowHint = true;
    },
    hintMouseLeave() {
      this.isShowHint = false;
    },
    // 로딩 완료 후 게임 시작.
    gameStart() {
      const gameStore = useGameStore();
      gameStore.turnMessage = "게임 시작";
      setTimeout(() => {
        gameStore.isShowTurnMessage = true;
      }, 1000);
      setTimeout(() => {
        gameStore.isShowTurnMessage = false;
        // 턴 시작.
        if (gameStore.myTurn === 0 && gameStore.myTeam === 1) {
          gameStore.isThrowYut = true;
        }
        // gameStore.isShowReasoning = true;
        gameStore.startTimer();
      }, 4000);
    },
    // 윷 결과를 받아 왔을 때.
    receiveYutRes() {
      const gameStore = useGameStore();
      if (gameStore.timerId !== null) {
        clearInterval(gameStore.timerId);
        gameStore.timerId = null;
      }
      gameStore.yutRes = this.receivedMsg.yutRes;
      gameStore.throwRes = this.receivedMsg.throwRes;
      gameStore.setYutText(this.receivedMsg.yutRes);

      if (gameStore.yutRes >= 4) {
        gameStore.throwChance += 1;
      }

      this.yutText = gameStore.yutText;

      // 텍스트와 윷결과 판을 다른 타이밍에 나타나게 한다.
      this.isShowRes = true;
      this.$refs.yutThrow.throwYut();
      setTimeout(() => {
        this.isShowResText = true;
      }, 2000);

      // 윷 결과를 없앤다.
      setTimeout(() => {
        this.isShowRes = false;
      }, 3500);
      // 만약 아무 말도 안나갔는데 백도가 나오면 그냥 넘어간다.
      if (gameStore.yutRes == -1) {
        if (
          this.receivedMsg.team == 1 &&
          gameStore.redHorses[4].check == 4 - gameStore.redEnd &&
          gameStore.redHorses[4].index === 0
        ) {
          setTimeout(() => {
            this.isShowResText = false;
            gameStore.turnChange();
            return;
          }, 3500);
        } else if (
          this.receivedMsg.team == 2 &&
          gameStore.blueHorses[4].check == 4 - gameStore.blueEnd &&
          gameStore.redHorses[4].index === 0
        ) {
          setTimeout(() => {
            this.isShowResText = false;
            gameStore.turnChange();
            return;
          }, 3500);
        }
      }
      setTimeout(() => {
        gameStore.startTimer();
      }, 3500);
    },
    // 말 선택 결과를 받아 왔을 때.
    receiveSelectHorse() {
      const gameStore = useGameStore();
      this.isShowResText = false;
      if (gameStore.timerId !== null) {
        clearInterval(gameStore.timerId);
        gameStore.timerId = null;
      }

      gameStore.isGoDiagonal = this.receivedMsg.goDiagonal;
      gameStore.isCenterDir = this.receivedMsg.centerDir;
      gameStore.moveHorse(this.receivedMsg);
    },
    //pub/game/{code}/start
    // 윷 던지기
    moveHorse() {
      console.log("윷 던지기");
      const gameStore = useGameStore();
      // 내가 던질 차례인가 체크.
      if (!this.isThrowYut) {
        return;
      }
      // 윷 던지기 호출
      gameStore.yutThrow();
      // 윷 던지기 결과 텍스트.
      this.yutText = gameStore.yutText;
      // 소켓 전송
      let msg = {
        yutRes: gameStore.yutRes,
        throwRes: gameStore.throwRes,
        team: gameStore.myTeam,
      };
      console.log(this.roomCode);
      socketSend(`/pub/game/${this.roomCode}/throw-yut`, msg);

      // 텍스트와 윷결과 판을 다른 타이밍에 나타나게 한다.

      this.isShowRes = true;
      this.$refs.yutThrow.throwYut();
      setTimeout(() => {
        this.isShowResText = true;
      }, 2000);

      // 윷 결과를 없앤다.
      setTimeout(() => {
        this.isShowRes = false;
      }, 3500);

      // 만약 아무 말도 안나갔는데 백도가 나오면 그냥 넘어간다.
      if (gameStore.yutRes == -1) {
        if (
          gameStore.myTeam == 1 &&
          gameStore.redHorses[4].check == 4 - gameStore.redEnd &&
          gameStore.redHorses[4].index === 0
        ) {
          setTimeout(() => {
            this.isShowResText = false;
            gameStore.turnChange();
            return;
          }, 3500);
        } else if (
          gameStore.myTeam == 2 &&
          gameStore.blueHorses[4].check == 4 - gameStore.blueEnd &&
          gameStore.redHorses[4].index === 0
        ) {
          setTimeout(() => {
            this.isShowResText = false;
            gameStore.turnChange();
            return;
          }, 3500);
        }
      }
      // 윷 결과가 나오고 나서 부터 선택가능하다.
      setTimeout(() => {
        gameStore.isSelect = true;
        this.canSelectHorse = true;
        // 타이머 다시 시작.
        gameStore.startTimer();
        // 윷 먼저 던지고 선택할때까지 기다린다.
        this.$watch("isSelectedHorse", () => {
          // 선택을 하였다면.
          if (this.isSelectedHorse) {
            // clearInterval(gameStore.timerId);
            // gameStore.timerId = null;
            console.log("소켓 보내기 전");
            console.log(this.selectedHorse);
            // 소켓 전송
            msg = {
              unitIndex: this.selectedHorse.id,
              team: this.selectedHorse.team,
              goDiagonal: gameStore.isGoDiagonal,
              centerDir: gameStore.isCenterDir,
            };
            console.log(msg);
            socketSend(`/pub/game/${this.roomCode}/select-unit`, msg);
            this.isSelectedHorse = false;
            this.canSelectHorse = false;
            gameStore.isSelect = false;
          }
        });
      }, 3500);
      // 말이 이동하고 미션장소인지 아닌지 체크 후 타이머 시작.
    },
    // 제한시간동안 말 선택이 없으면 랜덤으로 선택
    randomHorse() {
      const gameStore = useGameStore();
      while (true) {
        let random = Math.floor(Math.random() * 5);
        // 홍팀 차례면.
        if (!gameStore.teamTurn) {
          if (gameStore.redHorses[random] !== "end") {
            if (
              gameStore.redHorses[random] === "wait" &&
              gameStore.yutRes === -1 &&
              gameStore.redHorses[random].stun > 0
            ) {
              continue;
            }
            if ([5, 10].includes(gameStore.redHorses[random].index)) {
              gameStore.isGoDiagonal = true;
            } else if ([22, 27].includes(gameStore.redHorses[random].index)) {
              gameStore.isCenterDir = true;
            }
          }
          this.selectedHorse = gameStore.redHorses[random];
          this.isSelectedHorse = true;
          break;
        }
        // 청팀
        else {
          if (gameStore.blueHorses[random] !== "end") {
            if (
              gameStore.blueHorses[random] === "wait" &&
              gameStore.yutRes === -1 &&
              gameStore.blueHorses[random].stun > 0
            ) {
              continue;
            }
            if ([5, 10].includes(gameStore.blueHorses[random].index)) {
              gameStore.isGoDiagonal = true;
            } else if ([22, 27].includes(gameStore.blueHorses[random].index)) {
              gameStore.isCenterDir = true;
            }
          }
          this.selectedHorse = gameStore.blueHorses[random];
          this.isSelectedHorse = true;
          break;
        }
      }
    },
    // 말 선택시 이벤트 받기
    selectHorse(horse) {
      // 선택할 수 있을 때만 클릭 이벤트 발생.
      if (this.canSelectHorse) {
        const gameStore = useGameStore();
        const myTeam = gameStore.myTeam;

        // 경고 메시지 없애기.
        if (this.isShowWarningMessage) this.isShowWarningMessage = false;

        // 이동 금지 말 체크.
        if (horse.stun > 0) {
          // 메시지 출력
          this.warningMessage =
            horse.name + "말은 " + horse.stun + "턴간 이동 금지입니다.";
          this.warningMessageSecond = "";
          this.isShowWarningMessage = true;

          setTimeout(() => {
            this.isShowWarningMessage = false;
          }, 2000);
        }
        // 백도인데 대기중인 말을 선택하면 다시.
        else if (horse.index == 0 && gameStore.yutRes == -1) {
          // 메시지 출력
          this.warningMessage = "백도는 대기중인 말을 선택할 수 없습니다...";
          this.warningMessageSecond = "";
          this.isShowWarningMessage = true;

          setTimeout(() => {
            this.isShowWarningMessage = false;
          }, 2000);
        } else if (horse.team == myTeam) {
          // 말 정보를 먼저 담는다.
          this.selectedHorse = horse;
          // 만약 대각선이라면.
          if ([5, 10].includes(horse.index)) {
            // 백도는 모달없이 간다.
            if (gameStore.yutRes === -1) {
              this.isSelectedHorse = true;
            } else {
              // 선택 모달
              this.modalType = 1;
              // 대각선으로 갈지 선택.
              this.isShowGoDig = true;
              this.$watch("isShowGoDig", () => {
                // 선택을 했다면.
                if (!this.isShowGoDig) {
                  this.isSelectedHorse = true;
                }
              });
            }
          }
          // 정 가운데 왼쪽 오른쪽 선택
          else if ([22, 27].includes(horse.index)) {
            // 백도는 모달없이 간다.
            if (gameStore.yutRes === -1) {
              this.isSelectedHorse = true;
            } else {
              // 선택 모달
              this.modalType = 2;
              // 대각선으로 갈지 선택.
              this.isShowGoDig = true;
              this.$watch("isShowGoDig", () => {
                // 선택을 했다면.
                if (!this.isShowGoDig) {
                  this.isSelectedHorse = true;
                }
              });
            }
          } else {
            this.isSelectedHorse = true;
          }
        } else {
          // 메시지 출력
          this.warningMessage = "같은 팀을 선택해 주세요...!!";
          this.warningMessageSecond = "";
          this.isShowWarningMessage = true;

          setTimeout(() => {
            this.isShowWarningMessage = false;
          }, 2000);
        }
      }
    },

    // 대각선으로 갈때
    goDigYes() {
      const gameStore = useGameStore();
      // 5,10번 타일에서 대각선 이동
      if ([5, 10].includes(this.selectedHorse.index)) {
        gameStore.isGoDiagonal = true;
      }
      // 정 가운데 왼쪽 오른쪽 선택.
      else {
        gameStore.isCenterDir = true;
      }
      this.isShowGoDig = false;
    },
    // 대각선으로 안갈때
    goDigNo() {
      this.isShowGoDig = false;
    },
    // 추리권을 사용할때.
    useTicket() {
      const msg = { reasoningChoose: true };
      socketSend(`/pub/game/${this.roomCode}/reason-ticket-use`, msg);
    },
    // 추리권을 사용하지 않을때.
    notUseTicket() {
      const msg = { reasoningChoose: false };
      socketSend(`/pub/game/${this.roomCode}/reason-ticket-use`, msg);
    },
  },

  // 윷 결과 보여주기.
  showRes() {
    this.isShowRes = true;
    this.$refs.yutThrow.throwYut();
    setTimeout(() => {
      this.isShowResText = true;
    }, 2000);

    // 윷 결과를 없앤다.
    setTimeout(() => {
      this.isShowRes = false;
      this.isShowResText = false;
    }, 3500);
  },
};
</script>
  
<style scoped>
@import "@/assets/css/game/gameBoard.css";
</style>
  