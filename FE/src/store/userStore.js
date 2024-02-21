import { defineStore } from "pinia";
import { useRoomStore } from "./roomStore";
import { usePickStore } from "./pickStore";
import { useGameStore } from "./gameStore";
import { useFriendStore } from "./friendStore";
import { useSettingStore } from "./settingStore";
import {
  userConfirm,
  userDoJoin,
  findByToken,
  emailCheck,
  nickCheck,
  emailVeificationRequest,
  emailVeification,
  passEmailVeification,
  passEmailVeificationRequest,
  changePass,
  changeNick,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

import { connectWebSocket } from '@/util/socket.js';

export const useUserStore = defineStore("user", {
  id: "myStore",

  state: () => {
    return {
      isLogin: false, // 로그인 체크
      showModalSide: false, // 네비 바, 사이드 바
      showLoginModal: false, // 로그인 모달
      showJoinModal: false, // 회원가입 모달
      showFindModal: false, // 비밀번호 찾기 모달
      showUserInfoNick: false, // 회원정보 닉네임
      showDropOutModal: false, // 탈퇴 모달
      showSuccessPassword: false, // 비밀번호 변경 모달
      userInfo: null, // 회원정보
      accessToken: null, // 어세스 토큰
      refreshToken: null, // 리프레쉬 토큰
      showSpyModal: false, // 밀정 선택 모달

      roomId: null,
      roomCode: null,
      currentRoomInfo: {
        roomCode: "B",
      },
      myTeamIdx: null,

      // 닉네임, 이메일 중복체크
      isEmailCheck: false,
      isNickCheck: false,
      // 이메일 인증
      isEmailCodeCheck: false,
      // 비밀번호 이메일 인증
      isPassEmailCodeCheck: false,

    };
  },

  actions: {
    // 데이터 초기화 로직
    initData() {
      const initialStateUser = useUserStore().$reset();
      const initialStateRoom = useRoomStore().$reset();
      const initialStatePick = usePickStore().$reset();
      const initialStateGame = useGameStore().$reset();
      const initialStateSetting = useSettingStore().$reset();
      Object.assign(this, initialStateUser);
      Object.assign(this, initialStateRoom);
      Object.assign(this, initialStatePick);
      Object.assign(this, initialStateGame);
      Object.assign(this, initialStateSetting)
    },
    
    // 로그인, 회원가입 모달 창을 나타내기 위한 함수 매개변수를 입력받아
    // 로그인, 회원가입 차별을 준다.
    openModal(value) {
      if (value === "login") {
        useUserStore().showLoginModal = true;
      } else if (value === "join") {
        useUserStore().showJoinModal = true;
      } else if (value === "find") {
        useUserStore().showFindModal = true;
      } else if (value === "Nick") {
        useUserStore().showUserInfoNick = true;
      } else if (value === "out") {
        useUserStore().showDropOutModal = true;
      } else if (value === "password") {
        useUserStore().showSuccessPassword = true;
      } else if (value === "spy") {
        useUserStore().showSpyModal = true;
      }
    },

    // 위와 같지만, 닫는 함수.
    closeModal(value) {
      if (value === "login") {
        useUserStore().showLoginModal = false;
      } else if (value === "join") {
        useUserStore().showJoinModal = false;
      } else if (value === "find") {
        useUserStore().showFindModal = false;
      } else if (value === "Nick") {
        useUserStore().showUserInfoNick = false;
      } else if (value === "Drop") {
        useUserStore().showDropOutModal = false;
      } else if (value === "password") {
        useUserStore().showSuccessPassword = false;
      } else if (value === "spy") {
        useUserStore().showSpyModal = false;
      }
    },

    userLogin: async (loginUser) => {
      return new Promise((resolve, reject) => {
        userConfirm(
          loginUser,
          async (response) => {
            if (response.status === 200) {
              let { data } = response;
              let accessToken = data["access-token"];
              let refreshToken = data["refresh-token"];

              useUserStore().accessToken = accessToken;
              useUserStore().refreshToken = refreshToken;

              useUserStore().isLogin = true;
              useUserStore().showLoginModal = false;

              useFriendStore().getFriend();
              
              // 웹 소켓 연결
              try {
                await connectWebSocket(accessToken)
                resolve(); // 작업 완료 후 resolve 호출
              } catch (error) {
                resolve() 
              }
            } else {
              useUserStore().isLogin = false;
              reject(new Error("로그인 실패")); // 실패 시 reject 호출
            }
          },
          (error) => {
            // 로그인 실패 구현
            alert("로그인 실패");
            useUserStore().isLogin = false;
            reject(error); // 실패 시 reject 호출
          }
        );
      });
    },

    // 비밀번호 이메일 인증 요청
    passEmailVerRequest: async (email) => {
      await passEmailVeificationRequest(
        email,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 비밀번호 이메일 인증
    passEmailVer: async (param) => {
      await passEmailVeification(
        param,
        (response) => {
          if(response.status === 200){
            useUserStore().isPassEmailCodeCheck = true;
          }else{
            useUserStore().isPassEmailCodeCheck = false;
          }
          console.log(useUserStore().isPassEmailCodeCheck);
        },
        (error) => {
          useUserStore().isPassEmailCodeCheck = false;
          console.log(error);
        }
      );
    },

    // 이메일 인증 요청
    EmailVerRequest: async (email) => {
      await emailVeificationRequest(
        email,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 이메일 인증
    EmailVer: async (param) => {
      await emailVeification(
        param,
        (response) => {
          if(response.status === 200){
            console.log("성공")
            useUserStore().isEmailCodeCheck = true;
          }else{
            useUserStore().isEmailCodeCheck = false;
          }
          console.log(response);
        },
        (error) => {
          useUserStore().isEmailCodeCheck = false;
          console.log(error);
        }
      );
    },

    // 이메일 중복 체크
    emailCheck: async (email) => {
      await emailCheck(
        email,
        (response) => {
          if (response.status === 200) {
            useUserStore().isEmailCheck = true;
          } else {
            useUserStore().isEmailCheck = false;
          }
        },
        (error) => {
          console.log(error);
          useUserStore().isEmailCheck = false;
        }
      );
    },

    // 닉네임 중복 체크
    nickCheck: async (nickname) => {
      await nickCheck(
        nickname,
        (response) => {
          if (response.status === 200) {
            useUserStore().isNickCheck = true;
          } else {
            useUserStore().isNickCheck = false;
          }
        },
        (error) => {
          console.log(error);
          useUserStore().isNickCheck = false;
        }
      );
    },

    // 회원가입
    userJoin: async (joinUser) => {
      await userDoJoin(
        joinUser,
        (response) => {
          alert("회원가입 성공");
        },
        (error) => {
          alert("회원가입 실패");
          console.log(error);
        }
      );
    },
    
    // 비밀번호 변경
    changePass: async(param) => {
      await changePass(
        param,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      )
    },

    // 닉네임 변경
    changeNick: async(param) => {
      await changeNick(
        param,
        (response) => {
          useUserStore().getUserInfo();
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      )
    },

    getUserInfo: () => {
      return new Promise((resolve, reject) => {
        findByToken(
          (response) => {
            if (response.status === httpStatusCode.OK) {
              useUserStore().userInfo = response.data.userInfo;
              resolve()
            }
          },
  
          async (error) => {
            console.error(
              "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
              error.response.status
            );
  
            await tokenRegenerate();
            // reject(error)
          }
        );
      })
    },

    tokenRegenerate: async () => {
      await tokenRegeneration(
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            useUserStore().accessToken = accessToken;
            console.log("[userStore] : 재발급 완료");
          }
        },

        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            console.log("RefreshToken 기간 만료");

            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                initData();
                router.push({ name: "/" });
              },
              (error) => {
                console.error(error);
                initData();
              }
            );
          }
        }
      );
    },
  },

  persist: {
    enabled: true, //storage 저장유무
    strategies: [
      {
        key: "user", //storage key값 설정
        storage: localStorage, // localStorage, sessionStorage storage 선택 default sessionStorage
      },
    ],
  },
});
