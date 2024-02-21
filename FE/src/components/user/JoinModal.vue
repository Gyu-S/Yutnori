<template>
  <!-- 회원가입 모달 창 -->
  <div id="joinModal" class="modal">
    <div class="modal-content">
      <button class="close" @click="closeModal('join')">&times;</button>
      <h2 class="join-text">Join</h2>
      <div :class="{ 'shake-animation': showError }">
        <input
          id="emailInput"
          type="text"
          placeholder="이메일"
          v-model="email"
          autocomplete="email"
          :readonly="isEmailVer"
        />
        <button
          v-if="isEmailVerRequest"
          class="email-btn"
          @click="EmailVerRequest"
        >
          전송
        </button>
      </div>
      <div class="password-error" v-if="!isValidEmail">
        {{ emailMsg }}
      </div>
      <div v-if="isEmailVerRequest" :class="{ 'shake-animation': showError }">
        <input
          id="emailCode"
          type="text"
          placeholder="이메일인증코드"
          v-model="emailCode"
        />
        <button class="email-btn" @click="EmailVer">확인</button>
      </div>
      <div class="password-error" v-if="isEmailCode">
        {{ emailCodeMsg }}
      </div>
      <input
        type="password"
        placeholder="패스워드"
        id="passwordInput"
        v-model="password"
        :class="{ 'shake-animation': showError }"
      /><br />
      <div class="password-error" v-if="!isValidPassword">
        영문,숫자,특수문자를 조합하여 입력해주세요.(8-16자)
      </div>
      <input
        type="password"
        placeholder="패스워드 확인"
        id="confirmPasswordInput"
        @input="checkPassword"
        v-model="passwordCheck"
        :class="{ 'shake-animation': showError }"
      /><br />
      <div class="password-error" v-if="!isPasswordMatch">
        패스워드가 일치하지 않습니다.
      </div>

      <input
        type="text"
        placeholder="닉네임"
        id="nicknameInput"
        v-model="nickname"
        :class="{ 'shake-animation': showError }"
      /><br />
      <div class="password-error" v-if="!isValidNickname">
        {{ nickMsg }}
      </div>

      <div class="gender-selection" :class="{ 'shake-animation': showError }">
        <button
          id="male-button"
          class="gender-button"
          @click="selectGender('남')"
          :class="{ selected: gender === '남' }"
        >
          남성
        </button>
        <button
          id="female-button"
          class="gender-button"
          @click="selectGender('여')"
          :class="{ selected: gender === '여' }"
        >
          여성
        </button>
      </div>
      <!-- 년도, 월, 일을 나란하게 표시 -->
      <div :class="{ 'shake-animation': showError }">
        <select id="birthdateYear" v-model="year"></select>

        <select id="birthdateMonth" v-model="month"></select>

        <select id="birthdateDay" v-model="day"></select>
      </div>

      <button class="modal-join-btn" @click="performJoin">회원가입</button>
      <button class="modal-cancel-btn" @click="closeModal">취소</button>
    </div>
  </div>
</template>

<script>
import { useUserStore } from "@/store/userStore";

export default {
  data() {
    return {
      gender: "",
      email: "",
      password: "",
      passwordCheck: "",
      nickname: "",
      year: "",
      month: "",
      day: "",
      // 이메일 닉 중복 체크
      isNickCheck: false,
      isEmailCheck: false, // 성공, 실패
      // 이메일 닉 메시지는 따로 정의.
      emailMsg: "",
      nickMsg: "",
      // 이메일 인증
      showEmailMsg: false,
      isEmailVerRequest: true, // 요청
      isEmailVer: false, // 인증 성공, 실패
      emailCode: "",
      isEmailCode: false,
      emailCodeMsg: "",
      showError: false,
    };
  },
  watch: {
    email(newVal) {
      if (this.isValidEmail) {
        this.updateFieldStyle("emailInput", true);
      }
    },
    password(newVal) {
      if (this.isValidPassword) {
        this.updateFieldStyle("passwordInput", true);
      }
    },
    passwordCheck(newVal) {
      if (this.isPasswordMatch) {
        this.updateFieldStyle("confirmPasswordInput", true);
      }
    },
    nickname(newVal) {
      if (this.isValidNickname) {
        this.updateFieldStyle("nicknameInput", true);
      }
    },
    year(newVal) {
      if (this.year !== "") {
        this.updateFieldStyle("birthdateYear", true);
      }
    },
    month(newVal) {
      if (this.month !== "") {
        this.updateFieldStyle("birthdateMonth", true);
      }
    },
    day(newVal) {
      if (this.day !== "") {
        this.updateFieldStyle("birthdateDay", true);
      }
    },
    gender(newVal) {
      if (this.gender !== "") {
        this.updateFieldStyle("male-button", true);
        this.updateFieldStyle("female-button", true);
      }
    },
  },
  methods: {
    // 인증코드 발송.
    EmailVerRequest() {
      this.showEmailMsg = true;
      this.emailMsg = "인증코드를 발송하였습니다.";
      const userStore = useUserStore();
      userStore.EmailVerRequest(this.email);
    },
    // 인증코드 인증.
    EmailVer() {
      const param = { email: this.email, code: this.emailCode };
      const userStore = useUserStore();
      userStore.EmailVer(param);
      console.log(useUserStore().isEmailCodeCheck);
      setTimeout(() => {
        if (useUserStore().isEmailCodeCheck) {
          this.isEmailVer = true;
          this.isEmailVerRequest = false;
          this.isEmailCode = false;
          this.emailMsg = "인증에 성공하였습니다.";
        } else {
          this.isEmailCode = true;
          this.emailCodeMsg = "인증에 실패하였습니다.";
        }
      }, 3000);
    },
    async performJoin() {
      let isValid = true; // 모든 입력 값이 유효한지 추적하는 변수

      // 필드별 유효성 검사
      if (this.email === "" || (!this.isValidEmail && !this.isEmailVer)) {
        this.updateFieldStyle("emailInput", false);
        isValid = false;
      }
      if (this.password === "" || !this.isValidPassword) {
        this.updateFieldStyle("passwordInput", false);
        isValid = false;
      }
      if (this.passwordCheck === "" || !this.isPasswordMatch) {
        this.updateFieldStyle("confirmPasswordInput", false);
        isValid = false;
      }
      if (this.nickname === "" || !this.isValidNickname) {
        this.updateFieldStyle("nicknameInput", false);
        isValid = false;
      }
      if (!this.gender) {
        this.updateFieldStyle("male-button", false);
        this.updateFieldStyle("female-button", false);
        isValid = false;
      }
      if (!this.year) {
        this.updateFieldStyle("birthdateYear", false);
        isValid = false;
      }
      if (!this.month) {
        this.updateFieldStyle("birthdateMonth", false);
        isValid = false;
      }
      if (!this.day) {
        this.updateFieldStyle("birthdateDay", false);
        isValid = false;
      }
      // 이메일 체크.
      // else if (!this.isEmailCheck) {
      // }
      // 모든 정보가 제대로 되어 있을 경우 서버로 넘기기
      if (isValid) {
        const userStore = useUserStore();

        const joinUser = {
          email: this.email,
          nickname: this.nickname,
          password: this.password,
          birthDate: `${this.year}-${this.month
            .toString()
            .padStart(2, "0")}-${this.day.toString().padStart(2, "0")}`,
          gender: this.gender,
        };

        // 회원가입 전송
        useUserStore().userJoin(JSON.stringify(joinUser));

        // 회원가입창 모달 닫기
        userStore.closeModal("join");
      } else {
        this.showError = true;

        setTimeout(() => {
          this.showError = false;
        }, 500);
        return;
      }

      // 비밀번호가 불일치하는 경우 알림을 표시합니다.
      if (this.passwordMismatch) {
        alert("비밀번호 일치 하지 않습니다.");
        return;
      }
    },
    updateFieldStyle(fieldId, isValid) {
      const field = document.getElementById(fieldId);
      if (isValid) {
        field.style.border = "none"; // 기본 스타일로 복귀
      } else {
        field.style.border = "2px solid red"; // 유효하지 않을 때 빨간색 테두리
      }
    },

    selectGender(gender) {
      this.gender = gender;
    },

    populateDateOptions() {
      const birthdateYear = document.getElementById("birthdateYear");
      const birthdateMonth = document.getElementById("birthdateMonth");
      const birthdateDay = document.getElementById("birthdateDay");

      // 년도 옵션 추가 (예: 1980부터 2024까지)
      for (let year = 1980; year <= 2024; year++) {
        const option = document.createElement("option");
        option.value = year;
        option.textContent = year;
        birthdateYear.appendChild(option);
      }

      // 월 옵션 추가 (1월부터 12월까지)
      for (let month = 1; month <= 12; month++) {
        const option = document.createElement("option");
        option.value = month;
        option.textContent = month;
        birthdateMonth.appendChild(option);
      }

      // 일 옵션 추가 (1일부터 31일까지)
      for (let day = 1; day <= 31; day++) {
        const option = document.createElement("option");
        option.value = day;
        option.textContent = day;
        birthdateDay.appendChild(option);
      }
    },

    // 회원가입 창 닫기
    closeModal() {
      const userStore = useUserStore();
      userStore.closeModal("join");
    },

    // 이메일 중복 체크
    emailCheck() {
      const userStore = useUserStore();
      userStore.emailCheck(this.email);
    },
    nickCheck() {
      const userStore = useUserStore();
      userStore.nickCheck(this.nickname);
    },
  },

  computed: {
    // 이메일 유효성 검사
    isValidEmail() {
      const re =
        /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      let flag = re.test(this.email) || this.email === "";

      if (flag && this.email !== "") {
        this.emailCheck();
        const userStore = useUserStore();
        if (!userStore.isEmailCheck) {
          flag = false;
          this.emailMsg = "이메일이 중복되었습니다.";
        } else {
          this.isEmailVerRequest = true;
        }
      } else {
        this.emailMsg = "이메일 주소를 정확히 입력해주세요.";
      }
      return flag;
    },
    // 비밀번호 유효성 검사
    isValidPassword() {
      const validatePassword =
        /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

      return validatePassword.test(this.password) || this.password === "";
    },
    // 비밀번호 확인 일치 검사
    isPasswordMatch() {
      return this.password === this.passwordCheck || this.passwordCheck === "";
    },
    // 닉네임 유효성 검사
    isValidNickname() {
      let flag = this.nickname.length >= 3 || this.nickname === "";
      if (flag && this.nickname !== "") {
        this.nickCheck();
        const userStore = useUserStore();
        if (!userStore.isNickCheck) {
          flag = false;
          this.nickMsg = "닉네임이 중복되었습니다.";
        }
      } else {
        this.nickMsg = "닉네임을 3글자 이상 입력해주세요.";
      }
      return flag;
    },
  },
  mounted() {
    this.populateDateOptions();
  },
};
</script>

<style scoped>
@import "../../assets/css/user/join.css";
</style>