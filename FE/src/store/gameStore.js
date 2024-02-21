import { defineStore } from "pinia";
import { useMsgModalStore } from "./messageModalStore.js";
import { socketSend } from "@/util/socket.js";
import { useUserStore } from "@/store/userStore";

export const useGameStore = defineStore("game", {
  // 반응형 상태 (data)
  state: () => {
    return {
      // 채팅 메시지
      gameChatMsg: [],
      // 소켓 메시지
      receivedMsg: null,
      // 타이머
      timerId: null,
      timer: 20,
      // 내 팀 정보. 1 == 홍 2 == 청
      myTeam: 1,
      // 턴. idx 0 = 홍팅, 1 = 청팀
      turn: [0, 0],
      // false = 홍팀, true = 청팀.
      teamTurn: false,
      // 다시 던지기
      throwChance: 1,
      // 내 차례
      myTurn: 0,
      // 윷을 던질 수 있는가. 내 턴인가.
      isThrowYut: false,
      // 들어온 말의 개수.
      redEnd: 0,
      blueEnd: 0,
      isHorseEnd: false,
      // 윷 던진 결과
      throwRes: [false, false, false, false],
      yutText: "도",
      yutRes: 0,
      // 말 선택 flag.
      isSelect: false,
      // 대각선으로 갈지 말지 선택
      isGoDiagonal: false,
      // 가운데 방향 선택
      isCenterDir: false,
      // 미션 장소
      missionTiles: [],
      isMission: false,
      // 기존에 있는말 말고 새로가는 말만 이동하기 위해 카운트 변수.
      toCnt: 0,
      // 유저 정보
      redUser: [],
      blueUser: [],
      // 스파이 말
      mySpyId: 0,
      isFindSpy: false,
      isEnemyFindSpy: false,
      spyGoal: false,
      // 힌트 메시지
      mySpyHint: "",
      // 다음 차례 메시지.
      turnMessage: "",
      redTurnName: "",
      blueTurnName: "",
      isShowTurnMessage: false,
      // 추리권
      isShowReasoning: false,
      reasoningChoose: false,
      ticket: 0,
      ticketTemp: 0,
      // 적팀 추리권,.
      enemyTicket: 0,
      enemyTicketTemp: 0,
      // 몇라운드마다 티켓을 얻을 지
      gameSpeed: 0,
      // 스턴
      isStunFlag: false,
      // 말
      redHorses: [
        {
          id: 1,
          index: 0,
          team: 1,
          status: "wait",
          img: "horse1",
          check: 0,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 2,
          index: 0,
          team: 1,
          status: "wait",
          img: "horse2",
          check: 1,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 3,
          index: 0,
          team: 1,
          status: "wait",
          img: "horse3",
          check: 2,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 4,
          index: 0,
          team: 1,
          status: "wait",
          img: "horse4",
          check: 3,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 5,
          index: 0,
          team: 1,
          status: "wait",
          img: "horse5",
          check: 4,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
      ],
      blueHorses: [
        {
          id: 1,
          index: 0,
          team: 2,
          status: "wait",
          img: "horse1",
          check: 0,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 2,
          index: 0,
          team: 2,
          status: "wait",
          img: "horse2",
          check: 1,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 3,
          index: 0,
          team: 2,
          status: "wait",
          img: "horse3",
          check: 2,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 4,
          index: 0,
          team: 2,
          status: "wait",
          img: "horse4",
          check: 3,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
        {
          id: 5,
          index: 0,
          team: 2,
          status: "wait",
          img: "horse5",
          check: 4,
          endOrder: 0,
          name: "",
          age: 1,
          skill: "",
          contactor: "",
          place: "",
          scal: "",
          stuff: "",
          time: "",
          stun: 0,
          kill: false,
        },
      ],
      horsesIndex: [
        [
          { bottom: "160px", left: "70px" },
          { bottom: "160px", left: "140px" },
          { bottom: "90px", left: "70px" },
          { bottom: "90px", left: "140px" },
          { bottom: "30px", left: "105px" },
        ],
        [
          { bottom: "160px", left: "1356px" },
          { bottom: "160px", left: "1426px" },
          { bottom: "90px", left: "1356px" },
          { bottom: "90px", left: "1426px" },
          { bottom: "30px", left: "1391px" },
        ],
        [
          { bottom: "10px", left: "380px" },
          { bottom: "10px", left: "430px" },
          { bottom: "10px", left: "480px" },
          { bottom: "10px", left: "530px" },
          { bottom: "10px", left: "580px" },
        ],
        [
          { bottom: "10px", left: "820px" },
          { bottom: "10px", left: "770px" },
          { bottom: "10px", left: "720px" },
          { bottom: "10px", left: "670px" },
          { bottom: "10px", left: "620px" },
        ],
      ],
      // 말이 이동할 위치
      tileHorse: [
        { bottom: "90px", right: "610px" }, // 0
        { bottom: "220px", right: "610px" }, // 1
        { bottom: "310px", right: "610px" }, // 2
        { bottom: "400px", right: "610px" }, // 3
        { bottom: "490px", right: "610px" }, // 4
        { bottom: "600px", right: "610px" }, // 5
        { bottom: "600px", right: "730px" }, // 6
        { bottom: "600px", right: "820px" }, // 7
        { bottom: "600px", right: "910px" }, // 8
        { bottom: "600px", right: "1000px" }, // 9
        { bottom: "600px", right: "1110px" }, // 10
        { bottom: "490px", right: "1110px" }, // 11
        { bottom: "400px", right: "1110px" }, // 12
        { bottom: "310px", right: "1110px" }, // 13
        { bottom: "220px", right: "1110px" }, // 14
        { bottom: "90px", right: "1110px" }, // 15
        { bottom: "90px", right: "1000px" }, // 16
        { bottom: "90px", right: "910px" }, // 17
        { bottom: "90px", right: "820px" }, // 18
        { bottom: "90px", right: "730px" }, // 19
        { bottom: "510px", right: "700px" }, // 20
        { bottom: "440px", right: "770px" }, // 21
        { bottom: "350px", right: "860px" }, // 22
        { bottom: "255px", right: "955px" }, // 23
        { bottom: "190px", right: "1020px" }, // 24
        { bottom: "510px", right: "1020px" }, // 25
        { bottom: "440px", right: "955px" }, // 26
        { bottom: "350px", right: "860px" }, // 27
        { bottom: "255px", right: "770px" }, // 28
        { bottom: "190px", right: "700px" }, // 29
        { bottom: "90px", right: "610px" }, // 30
      ],
      // 타일
      tiles: [
        { id: 0, position: "right", horse: [] },
        { id: 1, position: "right", horse: [] },
        { id: 2, position: "right", horse: [] },
        { id: 3, position: "right", horse: [] },
        { id: 4, position: "right", horse: [] },
        { id: 5, position: "right", horse: [] },
        { id: 6, position: "top", horse: [] },
        { id: 7, position: "top", horse: [] },
        { id: 8, position: "top", horse: [] },
        { id: 9, position: "top", horse: [] },
        { id: 10, position: "top", horse: [] },
        { id: 11, position: "left", horse: [] },
        { id: 12, position: "left", horse: [] },
        { id: 13, position: "left", horse: [] },
        { id: 14, position: "left", horse: [] },
        { id: 15, position: "left", horse: [] },
        { id: 16, position: "bottom", horse: [] },
        { id: 17, position: "bottom", horse: [] },
        { id: 18, position: "bottom", horse: [] },
        { id: 19, position: "bottom", horse: [] },
        { id: 20, position: "top-right", horse: [] },
        { id: 21, position: "top-right", horse: [] },
        { id: 22, position: "center", horse: [] },
        { id: 23, position: "bottom-left", horse: [] },
        { id: 24, position: "bottom-left", horse: [] },
        { id: 25, position: "top-left", horse: [] },
        { id: 26, position: "top-left", horse: [] },
        { id: 27, position: "center", horse: [] },
        { id: 28, position: "bottom-right", horse: [] },
        { id: 29, position: "bottom-right", horse: [] },
        { id: 30, position: "right", horse: [] },
      ],
    };
  },

  // 메서드 (function)
  actions: {
    // 말 이동
    moveHorse(selectedHorse) {
      const horseInfo =
        selectedHorse.team === 1
          ? this.redHorses.find((horse) => horse.id === selectedHorse.unitIndex)
          : this.blueHorses.find(
              (horse) => horse.id === selectedHorse.unitIndex
            );

      // 말의 능력.
      if (horseInfo.name === "기병") {
        if (this.yutRes === -1) {
          this.yutRes -= 1;
        } else this.yutRes += 1;
      } else if (horseInfo.name === "노비") {
        if (this.yutRes === -1) {
          this.yutRes += 1;
        } else this.yutRes -= 1;
      }
      // 목적지 설정.
      let target = horseInfo.index + this.yutRes;

      // 노비의 능력으로 0값이면.
      if (this.yutRes !== 0) {
        // 처음 출발할때는 상태를 바꿔야한다.
        if (horseInfo.status === "wait") {
          // 처음 출발한 말을 0번에 넣어 시작한다.
          this.tiles[0].horse.push(horseInfo);

          // 대기 중인 말이 빠지면 대기 말 재배열.
          for (var i = horseInfo.id; i < 5; i++) {
            if (selectedHorse.team === 1) this.redHorses[i].check -= 1;
            else this.blueHorses[i].check -= 1;
          }

          // 출발한 말의 상태를 바꾼다.
          horseInfo.status = "ing";
        }

        // 5, 10 모서리 출발
        if (this.isGoDiagonal) target += 14;

        // 가운데 방향 설정.
        if (horseInfo.index == 22 && !this.isCenterDir) target += 5;
        if (horseInfo.index == 27 && this.isCenterDir) target -= 5;

        if (
          horseInfo.index <= 24 &&
          target > 24 &&
          target === horseInfo.index + this.yutRes
        )
          if (target !== 30) {
            target -= 10;
          }
        if (horseInfo.index == 27 && this.isCenterDir && target > 24)
          target -= 10;

        // 말이 들어왔을 때
        // 마지막 위치에서 출발할 때는 그냥 끝.
        if (horseInfo.index === 30) {
          let arr = [];
          for (var i = 0; i < this.tiles[horseInfo.index].horse.length; i++) {
            const horseTemp =
              horseInfo.team === 1
                ? this.redHorses.find(
                    (horse) =>
                      horse.id === this.tiles[horseInfo.index].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) =>
                      horse.id === this.tiles[horseInfo.index].horse[i].id
                  );
            // 말 상태를 바꾼다.
            horseTemp.status = "end";
            arr.push(horseTemp.id);
            // 카운트 한다.
            if (horseTemp.team === 1) horseTemp.endOrder = this.redEnd++;
            else horseTemp.endOrder = this.blueEnd++;
          }
          // 비워준다.
          this.tiles[horseInfo.index].horse = [];
          // 그냥 끝낸다.
          // 초기화.
          this.isHorseEnd = false;
          this.isGoDiagonal = false;
          this.isCenterDir = false;

          const msg = {
            team: horseInfo.team,
            unitId: arr,
          };
          // 말 들어오면 밀정인지 체크하기 위헤 서버 전송 이따.
          if (this.isThrowYut) {
            socketSend(
              `/pub/game/${useUserStore().currentRoomInfo.roomCode}/unit-gole`,
              msg
            );
          }

          // 턴 바꿈.
          if (this.throwChance === 0) {
            this.turnChange();
          }
          return;
        }
        // 15번 부터 마지막 타일 또는 들어왔다면
        if (
          (horseInfo.index >= 15 && target > 19 && horseInfo.index < 20) ||
          ((horseInfo.index === 22 || horseInfo.index >= 25) && target > 29)
        ) {
          // 마지막 타일
          if (target === 20 || target === 30) target = 30;
          // 그냥 들어와서 끝나면.
          else {
            this.isHorseEnd = true;
            // 카운트 한다.
            // if (horseInfo.team === 1) horseInfo.endOrder = this.redEnd++;
            // else horseInfo.endOrder = this.blueEnd++;
          }
        }

        // 만약 백도가 나왔을 때.
        if (this.yutRes === -1) {
          if (horseInfo.index === 20 || horseInfo.index === 25) {
            target -= 14;
          } else if (horseInfo.index == 1) {
            target = 30;
          }
        }

        // 만약 기병 백도가 나왔을 때.
        if (this.yutRes === -2) {
          if (horseInfo.index === 20 || horseInfo.index === 25) {
            target -= 14;
          } else if (horseInfo.index == 1) {
            target = 29;
          } else if (horseInfo.index == 2) {
            target = 30;
          }
        }

        // 창병 앞 뒤 적은 1턴간 이동 금지.
        if (horseInfo.name === "창병") {
          this.turnMessage = "";
          this.isStunFlag = false;
          this.horseStun(target, horseInfo);
          if (this.isStunFlag) {
            this.turnMessage =
              this.turnMessage + "말들은 1턴간 이동 불가입니다.";
            this.isShowTurnMessage = true;
            setTimeout(() => {
              this.isShowTurnMessage = false;
            });
          }
        }
        // 다른 말 체크
        if (!this.isHorseEnd) {
          // 도착지 말 카운트
          this.toCnt = this.tiles[target].horse.length;
          this.horseCheck(horseInfo, target);
        }

        // 말 이동
        this.moveTo(horseInfo.index, target);
      }

      // 미션장소 체크
      if (this.missionTiles.includes(target)) {
        this.turnMessage = "미션 장소 도착";
        this.isShowTurnMessage = true;
        if (this.isThrowYut) {
          const msg = { email: useUserStore().userInfo.email };
          socketSend(
            `/pub/game/${
              useUserStore().currentRoomInfo.roomCode
            }/mini-game-start`,
            msg
          );
        }
        setTimeout(() => {
          this.isMission = true;
          this.isShowTurnMessage = false;
        }, 2000);
      } else {
        // 턴 바꿈.
        if (this.throwChance === 0 && !this.isHorseEnd) {
          this.turnChange();
        } else if (this.throwChance > 0) {
          // 한번 더 메시지 출력.
          setTimeout(() => {
            this.turnMessage = "한번 더!!!";
            this.isShowTurnMessage = true;
            this.startTimer();
          }, 1000);

          setTimeout(() => {
            this.turnMessage = "";
            this.isShowTurnMessage = false;
          }, 4000);
        }
      }
      this.isGoDiagonal = false;
      this.isCenterDir = false;
      // 초기화.
      this.isHorseEnd = false;
    },
    // 미션 끝
    missionEnd() {
      if (this.throwChance > 0) {
        // 한번 더 메시지 출력.
        setTimeout(() => {
          this.turnMessage = "한번 더!!!";
          this.isShowTurnMessage = true;
          this.startTimer();
        }, 1000);

        setTimeout(() => {
          this.turnMessage = "";
          this.isShowTurnMessage = false;
        }, 4000);
      } else {
        this.turnChange();
      }
    },
    // 스턴
    horseStun(target, horseInfo) {
      // 24,29번이 아닌 타일만.
      if (![24, 30].includes(target)) {
        // 앞 스턴.
        if (this.tiles[target + 1].horse.length !== 0) {
          for (let i = 0; i < this.tiles[target + 1].horse.length; i++) {
            const horsedel =
              this.tiles[target + 1].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[target + 1].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[target + 1].horse[i].id
                  );
            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
      } else if (target === 24) {
        // 앞 스턴.
        if (this.tiles[target - 9].horse.length !== 0) {
          for (let i = 0; i < this.tiles[target - 9].horse.length; i++) {
            const horsedel =
              this.tiles[target - 9].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[target - 9].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[target - 9].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;

            horsedel.stun += 1;
          }
        }
      }
      // 20,25 제외
      if (![20, 25].includes(target)) {
        if (this.tiles[target - 1].horse.length !== 0) {
          for (let i = 0; i < this.tiles[target - 1].horse.length; i++) {
            const horsedel =
              this.tiles[target - 1].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[target - 1].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[target - 1].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;

            horsedel.stun += 1;
          }
        }
      } else {
        if (this.tiles[target - 15].horse.length !== 0) {
          for (let i = 0; i < this.tiles[target - 15].horse.length; i++) {
            const horsedel =
              this.tiles[target - 15].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[target - 15].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[target - 15].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;

            horsedel.stun += 1;
          }
        }
      }

      // 각 모서리에 도착한다면.
      if ([5, 10].includes(target)) {
        if (this.tiles[target + 15].horse.length !== 0) {
          for (let i = 0; i < this.tiles[target + 15].horse.length; i++) {
            const horsedel =
              this.tiles[target + 15].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[target + 15].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[target + 15].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
      } else if (target === 15) {
        if (this.tiles[24].horse.length !== 0) {
          for (let i = 0; i < this.tiles[24].horse.length; i++) {
            const horsedel =
              this.tiles[24].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[24].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[24].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
      }

      // 정 가운데.
      if (target === 22) {
        if (this.tiles[26].horse.length !== 0) {
          for (let i = 0; i < this.tiles[26].horse.length; i++) {
            const horsedel =
              this.tiles[26].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[26].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[26].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
        if (this.tiles[28].horse.length !== 0) {
          for (let i = 0; i < this.tiles[28].horse.length; i++) {
            const horsedel =
              this.tiles[28].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[28].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[28].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
      }

      if (target === 27) {
        if (this.tiles[21].horse.length !== 0) {
          for (let i = 0; i < this.tiles[21].horse.length; i++) {
            const horsedel =
              this.tiles[21].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[21].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[21].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
        if (this.tiles[23].horse.length !== 0) {
          for (let i = 0; i < this.tiles[23].horse.length; i++) {
            const horsedel =
              this.tiles[23].horse[i].team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[23].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[23].horse[i].id
                  );

            if (horsedel.team === horseInfo.team) {
              break;
            }
            this.turnMessage = this.turnMessage + "," + horsedel.name;
            this.isStunFlag = true;
            horsedel.stun += 1;
          }
        }
      }
    },

    // 턴 바꿈.
    turnChange() {
      // 홍팀이였다면,.
      if (!this.teamTurn) {
        if (this.myTeam === 1) {
          this.ticketTemp++;
          if (this.ticketTemp === this.gameSpeed) {
            this.ticketTemp = 0;
            this.ticket++;
          }
          // 같은 팀 이동 시 스턴 감소
          for (let i = 0; i < 5; i++) {
            if (this.redHorses[i].stun > 0) this.redHorses[i].stun--;
          }
        } else {
          this.enemyTicketTemp++;
          if (this.enemyTicketTemp === this.gameSpeed) {
            this.enemyTicketTemp = 0;
            this.enemyTicket++;
          }
        }
        this.turn[0]++;
        if (this.turn[0] > 2) this.turn[0] = 0;
        this.teamTurn = true;
      }
      // 청팀이면.
      else {
        if (this.myTeam === 2) {
          this.ticketTemp++;
          if (this.ticketTemp === this.gameSpeed) {
            this.ticketTemp = 0;
            this.ticket++;
          }
          // 같은 팀 이동 시 스턴 감소
          for (let i = 0; i < 5; i++) {
            if (this.blueHorses[i].stun > 0) this.blueHorses[i].stun--;
          }
        } else {
          this.enemyTicketTemp++;
          if (this.enemyTicketTemp === this.gameSpeed) {
            this.enemyTicketTemp = 0;
            this.enemyTicket++;
          }
        }
        this.turn[1]++;
        if (this.turn[1] > 2) this.turn[1] = 0;
        this.teamTurn = false;
      }

      // 메시지 저장
      this.turnMessage =
        (!this.teamTurn
          ? this.redUser[this.turn[0]].nickname
          : this.blueUser[this.turn[1]].nickname) + "님 차례입니다.";

      // 팀당 다음 차례 닉네임.
      this.redTurnName = this.redUser[this.turn[0]].nickname;
      this.blueTurnName = this.blueUser[this.turn[1]].nickname;

      // 약간의 여유를 준다.
      setTimeout(() => {
        // 메시지 보이기
        this.isShowTurnMessage = true;
      }, 2000);

      // 2초 후 메시지 숨기고 활성화.
      setTimeout(() => {
        this.isShowTurnMessage = false;
        // 내 차례인지 확인한다.
        this.isThrowYut = false;
        if (!this.teamTurn) {
          if (this.turn[0] === this.myTurn && this.myTeam === 1) {
            this.isThrowYut = true;
          }
        } else {
          if (this.turn[1] === this.myTurn && this.myTeam === 2) {
            this.isThrowYut = true;
          }
        }
        this.throwChance = 1;

        // 여기서 추리를 할건지 윷을 던질건지 선택한다.
        // 티켓이 하나 이상은 있어야 한다.
        if (
          (((!this.teamTurn && this.myTeam === 1) ||
            (this.teamTurn && this.myTeam === 2)) &&
            this.ticket > 0 &&
            !this.isFindSpy) ||
          (((!this.teamTurn && this.myTeam === 2) ||
            (this.teamTurn && this.myTeam === 1)) &&
            this.enemyTicket > 0 &&
            !this.isEnemyFindSpy)
        ) {
          this.isShowReasoning = true;
          // 여기에 추리 모달 결과 작성.
        } else {
          this.startTimer();
        }
      }, 5000);
      // 팀 차례 바꿈.
      // this.teamTurn != this.teamTurn;
    },

    // 타이머 함수
    startTimer() {
      // 이미 타이머가 실행 중이라면 중지
      if (this.timerId !== null) {
        clearInterval(this.timerId);
        this.timerId = null;
      }

      this.timer = 20;
      this.timerId = setInterval(() => {
        this.timer--;
        if (this.timer <= 0) {
          clearInterval(this.timerId);
          this.timerId = null;
        }
      }, 1000);
    },

    // 이동하는 곳에 다른 말이 있나 체크.
    horseCheck(horseInfo, target) {
      // console.log(horseInfo + " " + target);
      // 만약 팀이 다르다면.
      if (
        this.tiles[target].horse.length != 0 &&
        this.tiles[horseInfo.index].horse[0].team !=
          this.tiles[target].horse[0].team
      ) {
        for (var i = 0; i < this.tiles[target].horse.length; i++) {
          const horsedel =
            this.tiles[target].horse[i].team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[target].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[target].horse[i].id
                );

          horsedel.index = 0;
          horsedel.status = "wait";
          for (var j = horsedel.id; j < 5; j++) {
            if (horsedel.team === 1) this.redHorses[j].check += 1;
            else this.blueHorses[j].check += 1;
          }
        }
        this.toCnt = 0;
        this.tiles[target].horse = [];
        this.throwChance += 1;
        // 말을 잡았으니 기회 한번더.
        // setTimeout(() => {
        //   this.startTimer();
        //   this.turnMessage = "한번 더!!!";
        //   this.isShowTurnMessage = true;
        // }, 1000);

        // setTimeout(() => {
        //   this.turnMessage = "";
        //   this.isShowTurnMessage = false;
        // }, 4000);
      }

      this.tiles[target].horse.push(...this.tiles[horseInfo.index].horse);
      // 전에 있던 타일의 말 정보를 초기화한다.
      this.tiles[horseInfo.index].horse = [];
    },

    // 말 이동 함수
    moveTo(from, to) {
      // 이동 전 타일에서 말 배열의 크기를 가져온다.
      const len = !this.isHorseEnd
        ? this.tiles[to].horse.length
        : this.tiles[from].horse.length;
      // 팀 정보.
      const team = !this.isHorseEnd
        ? this.tiles[to].horse[0].team
        : this.tiles[from].horse[0].team;

      // 말이 들어왔다면.
      if (this.isHorseEnd) {
        let arr = [];
        for (let i = 0; i < len; i++) {
          const horseInfo =
            team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[from].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[from].horse[i].id
                );
          horseInfo.index = 30;
          arr.push(horseInfo.id);
        }
        setTimeout(() => {
          for (let i = 0; i < len; i++) {
            const horseInfo =
              team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[from].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[from].horse[i].id
                  );
            horseInfo.status = "end";
            // 카운트 한다.
            if (horseInfo.team === 1) horseInfo.endOrder = this.redEnd++;
            else horseInfo.endOrder = this.blueEnd++;
          }
          this.tiles[from].horse = [];

          const msg = {
            team: team,
            unitId: arr,
          };
          // 말 들어오면 밀정인지 체크하기 위헤 서버 전송 이따.
          if (this.isThrowYut) {
            socketSend(
              `/pub/game/${useUserStore().currentRoomInfo.roomCode}/unit-gole`,
              msg
            );
          }
        }, 300);
      }
      // 모서리를 통과할때.
      else if (
        this.yutRes > 0 &&
        Math.trunc(from / 5) != Math.trunc(to / 5) &&
        from < 20
      ) {
        // 순간이동을 방지하기 위해 모서리를 찍고 목적지로 이동한다.
        for (let i = this.toCnt; i < len; i++) {
          const horseInfo =
            team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                );
          horseInfo.index = Math.trunc(to / 5) * 5;
          console.log(to / 5 + " " + Math.trunc(from / 5));
        }
        setTimeout(() => {
          for (let i = this.toCnt; i < len; i++) {
            const horseInfo =
              team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  );

            horseInfo.index = to;
          }
        }, 300);
      } else if (this.yutRes > 0 && from >= 20 && to < 20) {
        // 순간이동을 방지하기 위해 24,15번 찍고 목적지로 이동한다.
        for (let i = this.toCnt; i < len; i++) {
          const horseInfo =
            team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                );
          horseInfo.index = 15;
        }
        setTimeout(() => {
          for (let i = this.toCnt; i < len; i++) {
            const horseInfo =
              team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  );

            horseInfo.index = to;
          }
        }, 300);
      }
      // 기병 백도 -2
      else if (this.yutRes === -2 && from === 1) {
        // 순간이동을 방지하기 위해 30번 찍고 목적지로 이동한다.
        for (let i = this.toCnt; i < len; i++) {
          const horseInfo =
            team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                );
          horseInfo.index = 30;
        }
        setTimeout(() => {
          for (let i = this.toCnt; i < len; i++) {
            const horseInfo =
              team === 1
                ? this.redHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  )
                : this.blueHorses.find(
                    (horse) => horse.id === this.tiles[to].horse[i].id
                  );

            horseInfo.index = to;
          }
        }, 300);
      }

      // 평범한 이동
      else {
        // 그룹을 다 이동 시킨다.0
        for (let i = this.toCnt; i < len; i++) {
          const horseInfo =
            team === 1
              ? this.redHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                )
              : this.blueHorses.find(
                  (horse) => horse.id === this.tiles[to].horse[i].id
                );
          horseInfo.index = to;
        }
      }
    },

    // 윷 던지기
    yutThrow() {
      // 던지기 1회 차감.
      // clearInterval(this.timerId);
      // this.timerId = null;
      this.throwChance--;

      // 총 4번의 랜덤을 발생
       // false가 뒤집어 진거
       let cnt = 0;
       for (var i = 0; i < 4; i++) {
         const rand = Math.floor(Math.random() * 10);

         // 윷은 4개이므로 각각 결과를 저장.
         if (rand < 4) {
           this.throwRes[i] = true;
           cnt++;
         } else {
           this.throwRes[i] = false;
         }
         // console.log("rand = " + rand);
       }

       // 저장된 결과를 계산.
       switch (cnt) {
         case 0:
           this.yutRes = 5;
           this.yutText = "모";
           break;
         case 1:
           this.yutText = "도";
           if (this.throwRes[0]) {
             this.yutText = "백도";123123

             
             this.yutRes = -1;
           } else this.yutRes = 1;
           break;
         case 2:
           this.yutText = "개";
           this.yutRes = 2;
           break;
         case 3:
           this.yutText = "걸";
           this.yutRes = 3;
           break;
         case 4:
           this.yutText = "윷";
           this.yutRes = 4;
           break;
         default:
           break;
       }
     

     if (this.yutRes >= 4) {
       this.throwChance += 1;
     }
     
    },
    setYutText(res) {
      if (this.timerId !== null) {
        clearInterval(this.timerId);
        this.timerId = null;
      }
      this.throwChance--;
      switch (res) {
        case -1:
          this.yutText = "백도";
          break;
        case 5:
          this.yutText = "모";
          break;
        case 1:
          this.yutText = "도";
          break;
        case 2:
          this.yutText = "개";
          break;
        case 3:
          this.yutText = "걸";
          break;
        case 4:
          this.yutText = "윷";
          break;
      }
    },
  },

  persist: {
    enabled: true, //storage 저장유무
    strategies: [
      {
        key: "game",
        storage: localStorage,
      },
    ],
  },
});
