import axios from "axios";

import { defineStore } from "pinia";
import { userConfirm } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

// 토큰을 갱신하는 함수
async function refreshToken() {
  try {
    // 서버에 토큰 갱신 요청을 보냅니다.
    const response = await axios.post("/refresh-token-endpoint", {
      // 기존에 사용했던 refreshToken 등을 서버에 전달할 수 있습니다.
      refreshToken: "your_refresh_token",
    });

    // 새로운 accessToken을 받아와 저장합니다.
    const newAccessToken = response.data.accessToken;
    localStorage.setItem("accessToken", newAccessToken);

    // 만료 시간을 업데이트합니다. (옵션)
    const expirationTime = new Date(response.data.expirationTime);
    localStorage.setItem("tokenExpiration", expirationTime.getTime());

    return newAccessToken;
  } catch (error) {
    console.error("토큰 갱신 실패:", error);
    throw error; // 토큰 갱신에 실패한 경우에는 예외 처리합니다.
  }
}

// 주기적으로 토큰을 갱신하는 타이머 설정 (예시: 50분마다)
setInterval(async () => {
  try {
    await refreshToken();
    console.log("토큰이 갱신되었습니다.");
  } catch (error) {
    console.error("토큰 갱신 실패:", error);
  }
}, 1 * 60 * 1000); // 1분 간격으로 실행 (단위: 밀리초)

// 사용 예시
async function fetchDataWithToken() {
  const accessToken = getAccessToken();

  if (accessToken) {
    // 유효한 토큰이 있다면, 해당 토큰을 헤더에 추가하여 서버에 요청합니다.
    const response = await axios.get("/protected-endpoint", {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });

    console.log("데이터:", response.data);
  } else {
    console.log("토큰이 유효하지 않습니다.");
  }
}

// 초기 실행
// fetchDataWithToken();