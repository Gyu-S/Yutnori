import { createApp } from "vue";
import { useStore } from "./store";
// import { connectWebSocket } from "./util/socket";

import App from "./App.vue";
import router from "./router";
// fontawsome 사용 위한 라이브러리 설정
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import SettingModal from '@/components/layout/SettingComponents.vue'

// 새로고침 할 때 소켓 재연결
// const userString = localStorage.getItem('user')
// const user = JSON.parse(userString)
// document.addEventListener('DOMContentLoaded', () => {
//   if (user.accessToken) {
//     const accessToken = user.accessToken
//     connectWebSocket(accessToken).then(() => {
//     }).catch((error) => {
//       console.error("WebSocket 재연결 실패:", error);
//     })
//   }
// })


const app = createApp(App);
app.use(useStore);
app.use(router);
app.component("font-awesome-icon", FontAwesomeIcon);

// SettingModal 컴포넌트 전역 등록
app.component("setting-modal", SettingModal)

app.mount("#app");