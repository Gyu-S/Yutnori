<template>
  <div class="setting-modal">
    <h2>게임 설정</h2>
    <div class="background-value">
      <label for="backgroundMusic">배경음 선택</label>
      <select id="backgroundMusic" v-model="selectedOption">
        <option v-for="(option, index) in options" :key="index" :value="option">
          {{ option.name }}
        </option>
      </select>
    </div>
    <div class="setting-value">
      <p class="volume-sound">소리 크기</p>
      <input class="volume" type="range" v-model="volumeValue" min="0" max="100">
      <p>{{ volumeValue }}%</p>
    </div>
    <div class="setting-value">
      <p>효과음</p>
      <div class="radio">
        <label class="radio-label" for="on">활성화</label>
        <input checked class="radio-button" type="radio" value="true" name="sound-effect" id="on" v-model="soundEffect">
      </div>
      <div class="radio">
        <label class="radio-label" for="off">비활성화</label>
        <input class="radio-button" type="radio" value="false" name="sound-effect" id="off" v-model="soundEffect">
      </div>
    </div>
    <div class="btn-box">
      <button class="btn-confirm" @click="closeSetting">변경</button>
      <button @click="cancelSetting" class="btn-cancel">취소</button>
    </div>
  </div>
</template>

<script>
  import { useSettingStore } from '@/store/settingStore';
  export default {
    data() {
      return {
        initSetting: {},
        options: useSettingStore().bgmOptions
      };
    },

    computed: {
      selectedOption: {
        get() {
          return useSettingStore().currentBgmSrc
        },
        set(value) {
          useSettingStore().currentBgmSrc = value.path
        }
      },

      volumeValue: {
        get() {
          return useSettingStore().musicVolume
        },
        set(value) {
          useSettingStore().musicVolume = value
        }
      },

      soundEffect: {
        get() {
          return useSettingStore().soundEffect
        },
        set(value) {
          useSettingStore().soundEffect = value
        }
      }
    },

    methods: {
      // 설정 모달 닫기
      closeSetting() {
        try {
          this.$emit('close-setting');
        } catch (error) {
          console.error('Error in closeSetting:', error);
        }
      },
      
      // 이벤트 취소
      cancelSetting() {
        const settingStore = useSettingStore();
        settingStore.currentBgmSrc = this.initSetting.currentBgmSrc;
        settingStore.musicVolume = this.initSetting.musicVolume;
        settingStore.soundEffect = this.initSetting.soundEffect;
        this.closeSetting()
      },
    },

    mounted() {
      this.initSetting = {
        currentBgmSrc: useSettingStore().currentBgmSrc,
        musicVolume: useSettingStore().musicVolume,
        soundEffect: useSettingStore().soundEffect
      }
    }
  };
</script>

<style scoped>
@import "@/assets/css/layout/setting.css";
</style>