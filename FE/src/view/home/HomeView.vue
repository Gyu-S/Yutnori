<template>
  <div class="container">
    <!-- (상단) 배너 슬라이드 -->
    <div class="banner-container">
      <div class="event-container">
        <Carousel :autoplay="5000" :wrap-around="true">
          <Slide v-for="(slide, index) in i18n" :key="index">
            <div class="carousel__item">
              <img id="gallery" :src="slide.name" @click="openModal('event', index)"/>
            </div>
          </Slide>
          
          <template #addons>
            <!-- <Pagination /> -->
            <!-- <Navigation /> -->
          </template>
        </Carousel>
      </div>
    </div>
    <!-- SNS 이벤트 모달창 -->
    <div class="event-detail-modal" v-if="showEventModal && clickedImageIndex === 0">
      <EventFirst :src="i18n[clickedImageIndex].detail" @click="closeModal('event')"/>
    </div>
    <!-- 광부 말 모달창 -->
    <div class="event-detail-modal" v-if="showEventModal && clickedImageIndex === 1">
      <EventSecond :src="i18n[clickedImageIndex].detail" @click="closeModal('event')"/>
    </div>
    <!-- 설 이벤트 모달창 -->
    <div class="event-detail-modal" v-if="showEventModal && clickedImageIndex === 2">
      <EventThird :src="i18n[clickedImageIndex].detail" @click="closeModal('event')"/>
    </div>
    

    <!-- (하단) 패치노트 및 도움말 -->
    <div class="article-container">
      <!-- 패치노트 -->
      <div id="patch">
        <!-- 제목 -->
        <div id="patch-title">
          <h3>패치노트</h3>
          <button id="detail-btn" @click="openModal('patch')">더보기></button>
          <transition name="fade">
            <PatchNoteItem
              v-if="showPatchItemModal"
              @close-modal="closeModal"
            />
          </transition>
        </div>

        <!-- 내용 -->
        <div id="patch-content">
          <div id="patch-list" v-if="patches.length > 0">
            <span v-for="patch in filterPetch" :key="patch.id" @click="showPetchDetail(patch)">
              {{ patch.subject }}
            </span>
            <transition name="fade">
              <PatchDetail 
                v-if="showPatchDetailModal"
                @close-modal="closeModal"
                :patch-detail="patchDetailContent"
              />
            </transition>
          </div>
        </div>
      </div>

      <!-- 도움말 -->
      <div id="help">
        <transition name="fade"
          ><div v-if="showMessage" class="help-message">
            <button class="help-close-btn" @click="closeMessage">
              &times;
            </button>
            <!-- <div class="arrow-up"></div> -->
            여기 도움말을 눌러보세요!
          </div></transition
        >

        <div class="help-icon" @click="openModal('help')">
          <i class="fas fa-question-circle"></i>
          <img src="../../assets/img/help/horse.png" alt="도움말 아이콘" />
        </div>

        <transition name="fade">
          <HelpItem v-if="showHelpItemModal" @close-modal="closeModal" />
        </transition>
      </div>
    </div>
  </div>
</template>
      
<script>
import { defineComponent } from "vue";
import { Carousel, Slide, Navigation } from "vue3-carousel";

import "vue3-carousel/dist/carousel.css";

// Item vue
import PatchNoteItem from "@/components/home/PatchItem.vue";
import PatchDetail from "@/components/home/PatchDetail.vue";
import HelpItem from "@/components/home/HelpItem.vue";
import EventFirst from "@/view/home/EventFirst.vue";
import EventSecond from "@/view/home/EventSecond.vue";
import EventThird from "@/view/home/EventThird.vue";

// 이미지
import event01 from "@/assets/img/home/carousel1.png";
import event02 from "@/assets/img/home/carousel2.png";
import event03 from "@/assets/img/home/carousel3.png";
import SnsEvent from "@/assets/img/home/snsevent.png";
import MalEvent from "@/assets/img/home/malevent.png";
import SulEvent from "@/assets/img/home/sulevent.png";

// 패치노트
import { getPetch } from "@/api/petch";

export default defineComponent({
  name: "Autoplay",
  components: {
    Carousel,
    Slide,
    Navigation,
    PatchNoteItem,
    PatchDetail,
    HelpItem,
    EventFirst,
    EventSecond,
    EventThird,
  },

  methods: {
    openModal(value, index) {
      if (value === "patch") {
        this.showPatchItemModal = true;
      } else if (value === "help") {
        this.showHelpItemModal = true;
      } else if (value === "event") {
        this.showEventModal = true;
        this.clickedImageIndex = index;       // 클릭한 이미지의 인덱스 저장
      } else if (value === "patchDetail") {
        this.showPatchDetailModal = true
      }
    },

    closeModal(value) {
      if (value === "patch") {
        this.showPatchItemModal = false;
      } else if (value === "help") {
        this.showHelpItemModal = false;
      } else if (value === "event") {
        this.showEventModal = false;
      } else if (value === "patchDetail") {
        this.showPatchDetailModal = false
      }
    },

    toggleMessage() {
      this.showMessage = !this.showMessage;
    },

    closeMessage() {
      this.showMessage = false;
    },

    showPetchDetail(patch) {
      this.patchDetailContent = patch
      this.openModal('patchDetail')
    }
  },

  data() {
    return {
      i18n: [{ name: event01, detail: SnsEvent }, { name: event02, detail: MalEvent }, { name: event03, detail: SulEvent }],
      showPatchItemModal: false,
      showHelpItemModal: false,
      showMessage: false,
      showEventModal: false,
      clickedImageIndex: null,    // 클릭한 이미지의 인덱스를 저장할 변수
      patches: [],
      showPatchDetailModal: false,
      patchDetailContent: null,
    };
  },

  computed: {
    filterPetch() {
      return this.patches.slice(0, 3)
    }
  },

  mounted() {
    // 페이지 로드 후 3초 뒤에 메시지를 표시
    setTimeout(() => {
      this.showMessage = true;
    }, 4000);
    // 패치노트 업로드
    getPetch((response) => {
      if (response && response.data) {
        this.patches = response.data
      }
    }, (error) => {
      console.log(error)
    })
    
  },
});
</script>
      
<style scoped>
@import url("../../assets/css/home/home.css");
</style>