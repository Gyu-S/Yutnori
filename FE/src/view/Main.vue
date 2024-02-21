<template>
  <transition name="fade" mode="out-in">
    <!-- 게임 중이거나 로그인 전이면 숨기기 위해 if문 사용 -->
    <header v-if="showModalSide">
      <NavBar />
      <SideBar />
    </header>
  </transition>
  <main>
    <transition name="fade" mode="out-in">
      <router-view />
    </transition>
  </main>
</template>

<script>
import NavBar from "@/components/layout/NavBar.vue";
import SideBar from "@/components/layout/SideBar.vue";
import { useSettingStore } from "@/store/settingStore";
import { useUserStore } from "@/store/userStore";
import { storeToRefs } from "pinia";
import { connectWebSocket } from "@/util/socket";
import { onMounted, watch, ref } from "vue";
import { useRoute } from "vue-router";

export default {
  components: {
    NavBar,
    SideBar,
  },

  setup() {
    const route = useRoute()
    const userStore = useUserStore()
    const settingStore = useSettingStore()
    const { showModalSide } = storeToRefs(userStore)

    // 배경음악
    const backgroundMusic = ref(new Audio(settingStore.currentBgmSrc))
    backgroundMusic.value.loop = true
    backgroundMusic.value.volume = settingStore.musicVolume / 100

    // 페이드인
    const fadeMusicIn = () => {
      let step = 0.01
      let interval = 5000 * step
      let targetVolume = settingStore.musicVolume / 100
      backgroundMusic.value.play().catch(error => console.error('배경음악 플레이 에러', error))
      const fade = setInterval(() => {
        if (backgroundMusic.value.volume < targetVolume) {
          let newVolume = backgroundMusic.value.volume + step
          if (newVolume > targetVolume) {
            newVolume = targetVolume;
          }
          backgroundMusic.value.volume = newVolume;
        } else {
          clearInterval(fade)
        }
      }, interval)
    }

    // 배경음악 재생 시 라우터 이동 감시
    watch(() => route.path, (newPath) => {
      if (newPath !== '/' && !settingStore.isMusicPlaying) {
        setTimeout(() => {
          fadeMusicIn(backgroundMusic.value)
          settingStore.isMusicPlaying = true
        }, 5000)
      } else if (['/', '/pick', '/game'].includes(newPath)) {
        settingStore.isMusicPlaying = false
      }
    });

    // 스토어의 musicVolume 상태 변경 감시
    watch(() => settingStore.musicVolume, (newVolume) => {
      backgroundMusic.value.volume = newVolume / 100
    })

    // 스토어의 currentBgmSrc 상태 변경 감시
    watch(() => settingStore.currentBgmSrc, (newSrc) => {
      backgroundMusic.value.src = newSrc
      if (settingStore.isMusicPlaying) {
        backgroundMusic.value.play().catch(error => console.error('배경음악 플레이 에러', error));
      }
    })

    // 스토어에 isMusicPlaying 감시
    watch(() => settingStore.isMusicPlaying, (isPlaying) => {
      if (isPlaying) {
        backgroundMusic.value.play().catch(error => console.error('배경음악 플레이 에러', error));
      } else {
        backgroundMusic.value.pause();
        backgroundMusic.value.currentTime = 0; 
      }
    })

    onMounted(() => {
      // 새로고침 할 때 소켓 재연결
      const userString = localStorage.getItem('user')
      if (userString) {
        const user = JSON.parse(userString)
        if (user && user.accessToken)
        connectWebSocket(user.accessToken).then(() => {
        }).catch((error) => {
          console.error("WebSocket 재연결 실패:", error);
        })
      }
    })


    return {
      showModalSide, // 네비 바와 사이드 바를 숨기고 나타내기 위해 선언
    };
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.85s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>