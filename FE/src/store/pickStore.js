import { defineStore } from "pinia";

export const usePickStore = defineStore("pick", {
  state: () => ({
    code: "",

    userInfo: [],

    unitInfo: [],

    subscription: {},

    receivedMessage: {},

    publisher: {},

    nowPickPlayerInfo: {
      email: "",
      time: "",
    },

    finished: false,
    pickFinished: false,
    pickRealFinished: false,
    pickSpyWait: false,
    pickFinalFinished: false,
  }),

  persist: {
    enabled: true, //storage 저장유무
    strategies: [
      {
        key: "pick",
        storage: localStorage,
      },
    ],
  },
});
