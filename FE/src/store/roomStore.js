import { defineStore } from "pinia";
import {
  getRoomList,
  getRoomDetail,
  getCanEnterRoom,
  axiosCreateRoom,
} from "@/api/room";

import { useUserStore } from "./userStore";

export const useRoomStore = defineStore("room", {
  /* 반응형 DATA */
  state: () => ({
    /* 소켓 통신을 위한 DATA */
    password: null,
    jsonPassword: null,

    /* 게임 방 요약 정보 */
    roomList: [],

    /* 게임 방 상세 정보 */
    roomDetailData: null,

    /* 게임 방 생성 정보 */
    createRoomInfo: null,

    sendRoomMessage: {
      nickname: "임시1",
      message: null,
    },

    /* 입장 시, 받을 정보들 */
    seatInfo: {
      // 방 정보
      seatnum1: {
        nickname: "",
        ready: false,
      },
      seatnum2: {
        nickname: "",
        ready: false,
      },
      seatnum3: {
        nickname: "",
        ready: false,
      },
      seatnum4: {
        nickname: "",
        ready: false,
      },
      seatnum5: {
        nickname: "",
        ready: false,
      },
      seatnum6: {
        nickname: "",
        ready: false,
      },
    },

    /* 메시지 관련 상태값 */
    receivedMessage: null,
    roomChatMessages: [],

    /* 구독 관리 */
    subscription: {
      room: null,
      pick: null,
    },

    /* 모달 관련 */
    showRoomMakingModal: false, // 방 생성
    showRoomPasswordCheckModal: false, // 비공개방 비밀번호 체크 모달
  }),

  actions: {
    /* 방에 들어갈 수 있는지 확인하고 가능하다면 구독과 발행 실행 */
    canEnterRoom() {
      return new Promise((resolve, reject) => {
        const roomInfo = useRoomStore().roomDetailData;
        if (roomInfo.currentUserCount === 6) {
          alert("방의 빈 자리가 없습니다.");
        } else {
          if (!roomInfo.public) {
            useRoomStore().jsonPassword = {
              password: useRoomStore().password,
            };
          } else {
            useRoomStore().jsonPassword = {
              password: "",
            };
          }

          // 방에 들어갈 수 있는지 확인
          // 입력값 (roomId, jsonPassword)
          getCanEnterRoom(
            useUserStore().roomId,
            useRoomStore().jsonPassword,
            ({ data }) => {
              useUserStore().roomCode = data;
              resolve();
            }
          );
        }
      });
    },

    /* 방 생성 */
    async createRoom() {
      try {
        await new Promise((resolve, reject) => {
          axiosCreateRoom(useRoomStore().createRoomInfo, ({ data }) => {
            // 방 생성 코드 받기.
            useRoomStore().createRoomInfo.roomCode = data;
            resolve();
          });
        });
      } catch (error) {
        console.log("방 생성 오류: " + error);

        // 토큰이 만료된 경우
        if (error.response.status === 406) {
          await useUserStore().tokenRegenerate();
        }
      }
    },

    // 여는 모달
    openModal(value) {
      if (value === "roomMaking") {
        useRoomStore().showRoomMakingModal = true;
      } else if (value === "roomPasswordCheck") {
        useRoomStore().showRoomPasswordCheckModal = true;
      }
    },

    // 닫는 모달
    closeModal(value) {
      if (value === "roomMaking") {
        useRoomStore().showRoomMakingModal = false;
      } else if (value === "roomPasswordCheck") {
        useRoomStore().showRoomPasswordCheckModal = false;
      }
    },

    // 방 리스트 조회
    getRoomSomeListData: async function () {
      return new Promise((resolve, reject) => {
        getRoomList(
          (response) => {
            const { data } = response;
            useRoomStore().roomList = data;
            resolve();
          },
          (error) => {
            console.log(error);
            console.log("방 데이터 조회 실패");
            reject(error);
          }
        );
      });
    },

    // 방 상세 정보 조회
    getRoomDetailData: async function (roomId) {
      return new Promise((resolve, reject) => {
        getRoomDetail(
          roomId,
          (response) => {
            const { data } = response;
            useRoomStore().roomDetailData = data;
            resolve();
          },
          (error) => {
            console.log(error);
            console.log("방 상세 정보 조회 실패");
            reject(error);
          }
        );
      });
    },
  },

  persist: {
    enabled: true, //storage 저장유무
    strategies: [
      {
        key: "room",
        storage: localStorage,
        reducer: (state) => ({
          isConnected: state.isConnected,
          seatInfo: state.seatInfo,
        }),
      },
    ],
  },
});
