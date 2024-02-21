import { defineStore } from "pinia";

export const useMiniGameStore = defineStore("mini", {
    state: () => {
        return{
            // 카드 선택 Flag
            isShowCardSelect : false,
        };
    },
});