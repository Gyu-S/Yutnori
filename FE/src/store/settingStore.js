import { defineStore } from "pinia";

export const useSettingStore = defineStore("setting", {
  state: () => ({
    isMusicPlaying: false,
    soundEffect: true,
    musicVolume: 5,
    currentBgmSrc: "/sound/OnceUponATime.mp3",
    bgmOptions: [
      { path: "/sound/BeautifulKorea.mp3", name: "Beautiful Korea" },
      { path: "/sound/BlackBox.mp3", name: "Black Box" },
      { path: "/sound/Blumenlied.mp3", name: "Blumenlied" },
      { path: "/sound/MorningAtThePalace.mp3", name: "Morning At The Palace" },
      { path: "/sound/OnceUponATime.mp3", name: "Once Upon A Time" },
    ],
  }),

  actions: {
    setVolum(volume) {
      this.volume = volume
    },
    setCurrentBgmSrc(path) {
      this.currentBgmSrc = path
    }
  },

  persist: {
    enabled: true, //storage 저장유무
    strategies: [
      {
        key: "setting", //storage key값 설정
        storage: localStorage, // localStorage, sessionStorage storage 선택 default sessionStorage
      },
    ],
  },
  
});
