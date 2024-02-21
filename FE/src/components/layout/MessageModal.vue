<template>
  <div class="msg-modal">
    <span class="modal-msg">{{ ModalMessage }}</span>
  </div>
</template>

<script>
import { useMsgModalStore } from "@/store/messageModalStore.js";
export default {
  computed: {
    isShowModal() {
      const msgStore = useMsgModalStore();
      console.log(msgStore.isShowModal);
      return msgStore.isShowModal;
    },
    ModalMessage() {
      const msgStore = useMsgModalStore();
      console.log(msgStore.ModalMessage);
      return msgStore.ModalMessage;
    },
  },
};
</script>

<style>
.msg-modal {
  position: fixed;
  top: 50px;
  left: 500px;
}
.modal-msg{
    color: blanchedalmond;
    font-size: 100px;
}
</style>