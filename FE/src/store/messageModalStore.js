import { defineStore } from "pinia";

export const useMsgModalStore = defineStore("msg", {
    state () {
        return {
            isShowModal: false,
            ModalMessage: "",
        };
    },
    actions: {
        printMessage(msg){
            console.log(msg);
            this.isShowModal = true;
            this.ModalMessage = msg;
            setTimeout(()=>{
                this.isShowModal = false;
                this.ModalMessage = "";
            },2000);
        }
    }
})