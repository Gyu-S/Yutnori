# S10P12D205 배고프면 생각이 안나조 프로젝트

## 🔍프로젝트 소개

### 📌서비스 요약

- 기존 윷놀이 말에 밀정 시스템을 추가하여 색다른 윷놀이를 즐길 수 있다.
- 맵의 특정 위치마다 미션을 설정하여 새로운 흥미 요소를 즐길 수 있다.

### 📌기획 의도

- 두 명 이상의 플레이어가 화상과 음성을 이용하여 상대방과 직접 소통하며 전략 시뮬레이션 게임인 윷놀이를 즐길 수 있다.
- 전통 놀이인 윷놀이에 전략적 요소를 가미하여 더욱 즐거운 게임이 가능하다.
- 경쟁과 협력이 공존하는 게임으로 서로 간의 유대감 증진이 가능하다.

### 📌기술 스택
<div>
<img src="https://img.shields.io/badge/html5-E34F26?style=flat&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=flat&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/vite-646CFF?style=flat&logo=vite&logoColor=white">
<img src="https://img.shields.io/badge/vue.js-4FC08D?style=flat&logo=vue.js&logoColor=white">  
</div>
<div>
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white">
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=spring-security&logoColor=white">
<img src="https://img.shields.io/badge/JPA-007396?style=flat&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/JWT-000000?style=flat&logo=json-web-tokens&logoColor=white">
</div>
<div>
<img src="https://img.shields.io/badge/Nginx-009639?style=flat&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white">  
</div>
<div>
<img src="https://img.shields.io/badge/gitlab-FC6D26?style=flat&logo=gitlab&logoColor=white">
<img src="https://img.shields.io/badge/Gerrit-FCC624?style=flat&logo=gerrit&logoColor=white">
</div>
<div>
<img src="https://img.shields.io/badge/docker-2496ED?style=flat&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/jenkins-D24939?style=flat&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat&logo=amazon-aws&logoColor=white">
</div>
<div>
<img src="https://img.shields.io/badge/figma-F24E1E?style=flat&logo=figma&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white">
<img src="https://img.shields.io/badge/mattermost-0058CC?style=flat&logo=mattermost&logoColor=white">
<img src="https://img.shields.io/badge/jira-0052CC?style=flat&logo=jira&logoColor=white">
</div>
<div>
<img src="https://img.shields.io/badge/fontawesome-339AF0?style=flat&logo=fontawesome&logoColor=white">
<img src="https://img.shields.io/badge/Three.js-0aa0f4?style=flat&logo=three.js&logoColor=white">
<img src="https://img.shields.io/badge/OpenVidu-fecb00?style=flat&logo=openvidu&logoColor=white">
<img src="https://img.shields.io/badge/MediaPipe-0097a7?style=flat&logo=mediapipe&logoColor=white">  
</div>

### 📌프로젝트 기간

- 2023.12.28 ~ 2024.02.16

## 🙍팀원 소개

- 김수빈 : 팀장, 프론트엔드
- 양성규 : 프론트엔드
- 이주미 : 프론트엔드
- 이희웅 : 프론트엔드
- 이준희 : 백엔드, 실시간 구현
- 박지훈 : 백엔드, 인프라

## 📑Convention

### 📌Git Commit Convention

#### 1. 커밋 유형

- 커밋 제목 첫 글자는 대문자로 작성하기
  
  | 커밋 유형 | 의미  |
  | --- | --- |
  | Feat | 새로운 기능 추가 |
  | Fix | 버그 수정 |
  | Docs | 문서 수정 |
  | Style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
  | Refactor | 코드 리팩토링 |
  | Test | 테스트 코드, 리팩토링 테스트 코드 추가 |
  | Chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
  | Design | CSS 등 사용자 UI 디자인 변경 |
  | Comment | 필요한 주석 추가 및 변경 |
  | Rename | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
  | Remove | 파일을 삭제하는 작업만 수행한 경우 |
  | !BREAKING CHANGE | 커다란 API 변경의 경우 |
  | !HOTFIX | 급하게 치명적인 버그를 고쳐야 하는 경우 |
  

#### 2. 제목

- `커밋 유형 태그 : 제목`의 형태이며 제목 첫 글자는 대문자로 작성
- 제목은 최대 50자 이내로 작성하고 마침표 및 특수기호는 사용 금지
- 커밋 유형 이후 제목과 본문은 한글로 작성하여 내용이 잘 전달될 수 있도록 할 것

#### 3. 본문

- 본문에는 변경한 내용과 이유 설명 (How 보다는 What & Why 위주 설명)
- 본문 내용은 양에 구애받지 않고 최대한 상세하게 작성

#### 4. 꼬리말

- 꼬리말은 옵션이므로 `이슈 트래커 ID`를 명시하고 싶을 때 작성
- 꼬리말은 `“유형: #이슈 번호”` 형식으로 사용
- 여러 개의 이슈 번호를 적을 때는 쉼표로 구분
- 이슈 트래커 유형은 다음 중 하나 사용
  - `Fixes`: 이슈 수정중 (아직 해결되지 않은 경우)
  - `Resolveds`: 이슈 해결했을 때 사용
  - `Ref`: 참고할 이슈가 있을 때 사용
  - `Related to`: 해당 커밋에 관련된 이슈 변호 (아직 해결되지 않은 경우)

### 📌Git Branch Convention

#### 규칙

- `master`에서 각자의 기능 브랜치를 분기
  - 프론트(`develop-FE`), 백(`develop-BE`)
- 브랜치 이름 규칙
  - `작업종류/기능` 으로 브랜치 만들기
  - (지라 스토리 이슈 = 기능 명세서 소 분류 = 브랜치 1개)
  - (지라 작업 이슈 = 커밋 1개)
  - `design/FE-logout` `feat/BE-signup` `fix/FE-not-render-nav`
- 해당 기능의 브랜치에 작업이 완료 되면 해당 브랜치를 원격 저장소에 `push`하고 Gerrit 을 이용하여 commit 단위로 리뷰 받기
  - commit 단위로 리뷰를 받았으므로, 추후에 기능 완성 후 로컬에서 프론트와 백 브랜치에 `merge` 이후 충돌 해결한 다음 원격브랜치에 `push`
- `merge request`가 승인되면 `merge된 master` 브랜치에대하여 다시 EC2에서 빌드, 배포 실행 ⇒ MM 혹은 대면으로 결과 알림

#### 주의

- `master` 브랜치로 부터 개발하는 용도 외의 복사본 용을 git clone하여 `master` 브랜치로 부터 계속 pull 받으면서 복사본으로 사용\*\*
- **원격 저장소 `master`에 `merge` 이후 꼭 잊지말고 `master` 에서 `pull` 하기**

## 📌Code Convention

### 백, 프론트 공통 코드 컨벤션

- 메소드 파라미터는 최대 4개 이하
  
- 함수명, 변수명은 카멜케이스
  
- 주석은 설명하려는 구문 상단에 맞춰 쓰기
  
  ```java
  function example() {
  ...
    // something에 관한 주석
    something ...
  }
  ```
  
- 공백으로 구분 잘해주기
  

### 프론트엔드 코드 컨벤션

**[ 코드 ]**

- 문장마다 Enter하여 가독성 높이기
  
- 화살표 함수 사용
  
- 작은 따옴표 사용
  
- 세미콜론 사용
  
- 멀티 워드 컴포넌트 이름 사용하기
  
  ```java
  <!-- 사전 컴파일된 템플릿에서 -->
  <TodoItem />
  ```
  

<!-- in-DOM 템플릿에서 -->

<todo-item></todo-item>

````
- prop 정의는 상세하게, 최소한 타입을 명시하기
```java
// 잘못된 예
const props = defineProps(['status'])
````

```java
// 좋은 예
// 1. 첫번째 예시
const props = defineProps({
    status: String
})

// 2. 두번째 예시 (상세)
const props = defineProps({
    status: {
        type: String,
        required: true,

        validator: (value) => {
                return ['syncing', 'synced', 'version-conflict', 'error'].includes(
                    value
                )
            }
        }
    })
```

**[ 파일명 ]**

- 기초 컴포넌트명에 이어쓰기
  
  ex)
  
  Todo.vue
  
  TodoList.vue
  
  TodoDetailList.vue
  
- 파스칼 케이스
  

#### 백엔드 코드 컨벤션

- 모든 변수는 하나의 한개만 선언하기
  
  ```java
  int a, b; // X
  
  int a;
  int b; // O
  ```
  
- package 이름 : 소문자와 숫자만 사용 `ex) com.example.deepspace`
  
- Class 이름 : 첫 번째 문자를 대문자로 시작하며, 명사로 작성
  
  ```java
  class Test { }
  
  //단어가 2개 이상 혼합되어 있다면,
  //각 단어의 첫 번째 문자를 대문자로 표현
  class UserInfo { }
  ```
  
- Interface 이름 : 첫 번째 문자를 대문자 시작
  
  ```java
  interface Runnable { }
  interface ActionListener { }
  ```
  
- C style 배열선언 금지
  
  ```java
  String[] args // O
  String args[] // X
  ```
  
- 구현부가 없거나 한 줄의 구문을 포함해도 중괄호를 사용한다.
  
  ```java
  if(a > b) {
  a = b;
  }
  ```
  
- 상수는 모두 대문자로 작성하며, 단어 사이를 밑줄(\_)로 구분
  
- 클래스의 멤버와 initializer 의 순서는 따로 없지만, 최대한 논리적인 순서를 따름 새 메서드를 끝에 추가하는 것은 시간순이지 논리적인 순서가 아님
  
- 빈 블록은 줄바꿈하지 않고 {} 로 사용 multi-block(if/else, try/catch/finally) 은 줄바꿈
  
- 같은 동일한 이름의 메서드, 생성자는 연속적으로 작성
  
- @Override: 항상 사용
  

### 📌Jira Convention

#### 이슈 구조

- `최상단 에픽 이슈` : 개발, 테스트, 배포, 설계 (포인트 x)
- `중단 스토리 이슈` : 유저 스토리 중심의 기능 명세서 소분류 (포인트 x)
- `하단 작업 이슈` : 명세서 소분류의 하위 작업들 (포인트 부여)
  - 이때, 작업 이슈를 따로 만들고 이슈 연결을 해야함
  - 기능적인 측면을 가진 작업 이슈의 경우 최상단 에픽에 바로 연결

#### 이슈 구조 만들기

1. `에픽 이슈` 생성
2. `에픽 이슈`에서 `하위 이슈 추가 버튼` 클릭하여 `스토리 이슈` 생성
3. `작업 이슈`를 따로 생성
4. 2에서 만든 `스토리 이슈`에서 `이슈 연결 버튼` 클릭하여 작업 이슈와 연결

#### 일주일의 스프린트 사용 FLOW

1. `백로그`에 이슈 만들기 - 위 참고
2. `스프린트 만들기`(기간은 그 주 월요일 ~ 일요일)
3. `백로그`에서 만든 이슈(스토리 이슈와 작업 이슈)를 모두 `스프린트`로 옮기기
  1. 이때, `스토리 이슈` 바로 밑에 연결되는 `작업 이슈`를 위치하게 하여 정리하기
4. `스프린트 시작 버튼` 클릭
5. `작업 이슈`에 `진행 중`으로 표시 추가적으로 `진행 중` 표시하고 `포인트 부여`하고 일 시작
6. `작업 이슈`에 해당하는 일이 완료 되면 `완료 표시`하기
  1. 이때 `스토리 이슈`에 해당하는 `모든 작업 이슈`가 완료되면 `스토리 이슈`도 `완료 표시`하기
7. 스프린트 완료 일요일 날 되면 꼭 누르기

#### 추가 주의사항

- 매일 마무리 회의 태스크 추가
- 피치못할 사정이 아닌 이상 금요일에 스프린트 회고 진행

## 📚명세서

### 📌요구사항 명세서

#### 기능적 요구사항

| 순번  | 요구사항명 | 요구사항 상세 | 우선순위 |
| --- | --- | --- | --- |
| F01_1 | 회원가입 | 이메일(아이디) , 비밀번호, 닉네임, 생년월일, 성별을 입력 받고 회원 가입을 할 수 있다. 회원 가입 시 이메일 인증이 필요하다. | 매우 높음 |
| F01_2 | 회원정보 수정 | 기존 회원정보 중 닉네임, 비밀번호 변경할 수 있다. | 높음  |
| F01_3 | 회원정보 조회 | 회원정보를 확인할 수 있는 프로필 페이지 보여줄 수 있다. | 높음  |
| F01_4 | 회원 탈퇴 | 유저 개인 정보만 삭제 후 탈퇴 처리할 수 있다. | 높음  |
| F01_5 | 로그인 | 아이디, 비밀번호 입력 후 로그인 할 수 있다. 로그인 하지 않은 사용자는 모든 기능 사용이 제한된다. | 매우 높음 |
| F01_6 | 로그아웃 | 토큰을 삭제하여 로그아웃 할 수 있다. | 매우 높음 |
| F01_7 | 비밀번호 찾기 | 이메일 인증으로 비밀번호 찾을 수 있다. | 높음  |
| F02_1 | 회원 전적 조회 | 본인의 최근 전적 10개를 승패로 확인 할 수 있다. | 낮음  |
| F02_2 | 회원 전적 조회 | 전적을 클릭하여 해당 게임의 상세 정보를 확인 할 수 있다. | 낮음  |
| F03_1 | 친구 추가 | 다른 사용자 정보를 등록하여 친구를 추가 할 수 있다. | 보통  |
| F03_2 | 친구 삭제 | 등록 되어있는 친구를 삭제 할 수 있다. | 보통  |
| F03_3 | 친구 목록 조회 | 사용자는 등록된 친구 목록을 조회 할 수 있다. | 보통  |
| F04_01 | 방 생성 | 공개 방, 비공개 방을 생성할 수 있다. - 생성한 사람이 방장이 된다. - 비공개 방 생성 시 방장은 비밀번호를 설정할 수 있다. | 매우 높음 |
| F04_02 | 방 목록 조회 | 생성되어있는 방을 조회 할 수 있다. - 공개방은 누구나 입장할 수 있다. - 비공개 방은 비밀번호를 통해 참가할 수 있다. | 매우 높음 |
| F04_03 | 방 삭제 | 생성한 방을 삭제할 수 있다. | 매우 높음 |
| F04_04 | 방 초대 | 방장은 친구를 초대할 수 있다. | 보통  |
| F04_05 | 방 참여 | 사용자들은 방에 참가하여 게임시작을 대기할 수 있다. | 매우 높음 |
| F05_01 | 게임 관리 | 게임 참가자들은 준비 완료 버튼을 통해 준비 완료를 할 수 있다. 모든 참가자가 준비 완료 상태가 되어야만 방장의 화면에서 게임시작 버튼이 활성화 되고 방장이 게임을 시작할 수 있다. | 매우 높음 |
| F05_02 | 게임 관리 | 사용자는 게임 도중 게임을 나갈 수 있다. 나간 참여자는 재 참여가 가능하다. | 높음  |
| F06_01 | 캠 관리 | 사용자는 캠을 끄거나 대체 이미지를 설정할 수 있다. | 보통  |
| F06_02 | 캠 관리 | 게임이 시작되면 사용자의 캠이 켜져야 한다. | 매우 높음 |
| F06_03 | 캠 관리 | 게임 중 기본 설정은 팀끼리만 음성 및 화상이 되어야 하고 질문 등 상대방과 연결이 필요할 때에 모든 참여자가 참가한다 | 매우 높음 |
| F07_01 | 채팅 기능 | 사용자는 친구에게 개인 채팅 및 전체 채팅을 시작할 수 있다. | 매우 높음 |
| F07_02 | 채팅 기능 | 게임 대기방 및 게임 시작 중에 게임 참여 인원들과 채팅을 할 수 있다. 이때 채팅은 팀 채팅과 공개 채팅으로 나뉘어야 한다. | 매우 높음 |
| F07_02 | 채팅 기능 | 채팅 비속어 필터 기능을 제공해야 한다. | 매우 낮음 |
| F08_01 | 게임 사전 준비 | 게임이 시작되면 3인이 각각 말의 직업을 선택할 수 있어야 한다. 직업은 중복으로 선택하지 못한다. | 매우 높음 |
| F08_02 | 게임 사전 준비 | 말 직업을 선택하면, 상대 팀 말 중 밀정을 하나 선택할 수 있어야 한다. | 매우 높음 |
| F09_01 | 게임진행 | 게임 진행은 게임 플레이 플로우 차트를 준수한다. 1. 모든 플레이어가 준비를 마친 후 방장에 의해 게임이 시작된다. 2. 게임이 시작되면 사전 준비 페이지로 이동한다. 3. 사전 준비 페이지에서는 말로 사용될 캐릭터를 인 당 하나씩 선택한다. (롤 처럼) 4. 제한 시간 안에 캐릭터를 선택해야 하며 제한 시간이 초과될 경우 랜덤으로 선택된다. 5. 캐릭터 선택이 완료되면 선택한 캐릭터들이 공개가 된다. 6. 각 팀은 상대 캐릭터 중 하나를 골라 밀정으로 선정한다. 7. 제한 시간 안에 밀정을 선택해야 하며 제한 시간이 초과될 경우 랜덤으로 선택된다. 8. 밀정 선택까지 마치면 본 게임 페이지로 이동한다. 9. 본 게임 페이지에서는 블루팀 먼저 윷 던지기가 시작된다. 10. 각 팀은 다섯번 째 턴 부터는 활성화된 추리 버튼을 이용해서 자신의 팀에 있는 밀정 말을 찾을 수 있다. 1. 추리 버튼을 이용한 추리는 추리권을 가지고 있을 경우 사용할 수 있다. 2. 추리권은 기본적으로 5턴 마다 1개씩 지급된다. 3. 추리권을 가지고 있을 경우에만 추리 버튼이 활성화 된다. 4. 추리를 하게 되면 한 턴(윷을 던지거나 추리를 하거나 택 1)을 소모 해야한다. 5. 추리를 성공하면 밀정 말은 상대 말이 되며 시작 지점으로 돌아가되, 맵에 올려놓지 않은 상태가 되어야 한다. 6. 추리에 실패할 경우 의심받은 말은 실의에 빠져 3턴 간 이동 불가 상태가 된다. 11. 윷 던지기 제한 시간을 초과하면 자동으로 윷을 던진다. 12. 윷 던진 결과가 나왔다면 움직일 말을 선택한 후 움직인다. 13. 윷 던진 결과에 따라 추가 턴을 획득할 수 있다. 1. 움직인 말이 상대 말 위치에 도달하면 상대 말을 잡거나 한 턴의 기회를 더 얻는다. (해당 추가 턴은 추리를 위한 턴과는 별도의 턴으로 관리된다.) 2. 윷 혹은 모가 나온 경우 한 턴의 기회를 더 얻는다. (해당 추가 턴은 추리를 위한 턴과는 별도의 턴으로 관리된다.) 14. 말의 이동이 끝난 후 필드의 말을 선택하여 스킬을 사용할 수 있다. 스킬엔 쿨타임이 존재한다. 15. 움직인 말이 미션 위치에 도달하면 미션을 수행할 수 있다. 1. 미션은 n개 중 랜덤으로 하나 선택되어 수행되어야 한다. 2. 미션의 종류는 아래와 같다. 1. 야바위 (상대방의 꺼진 화면을 무작위로 돌려서 특정 상대를 찾는 미션) 2. 광물 캐기 (광석 캐기 모션을 통해서 특정 퍼센트 이상으로 광물 캐내기) 3. 눈싸움 (자신의 팀 한 명과 상대팀 한 명이 캠으로 눈 싸움을 실시) 4. 계란 받기 (화면 상 위에서 떨어지는 계란을 일정 개수 이상 바구니로 받기) 5. 파리 잡기 (화면 상에서 날아다니는 파리를 손 동작을 통해서 일정 마리 수 이상 잡기) 6. 가위 바위 보 (자신의 팀 한 명과 상태 팀 한 명이 가위 바위 보를 진행) 7. 참참참 (랜덤으로 방향을 부여하고 상대방 모션이 방향과 불일치하면 성공) 8. 고요 속의 외침 (게임하는 팀의 소리를 모두 뮤트하고, 제시어가 주어지면 입모양을 보고 정답을 유추하는 게임) 3. 미션을 성공하면 밀정에 대한 단서를 한 개 얻을 수 있다. 4. 상대팀은 밀정에 대한 단서 중 하나를 줄 수 있다. 5. 만약 상대팀이 거짓말권을 사용하면 밀정이 아닌 말의 정보를 흘릴 수 있다. 6. 거짓말권은 한번만 쓸 수 있다. 7. 미션에 실패하였고 추가 턴(윷, 모, 상대말 잡음)을 가지고 있지 않다면 턴이 종료된다. 8. 추가 턴을 가지고 있다면 윷 던지기를 실행한다. (해당 추가 턴은 추리를 위한 턴과는 별도의 턴으로 관리된다.) 16. 움직인 말이 같은 팀 말 위치에 도달하면 말을 합칠 수 있다. 17. 합쳐진 말은 다음 턴부터 함께 움직일 수 있다. 18. 움직인 말이 도착 지점에 골인 하면 게임에서 제외된다. 19. 만약 골인 지점에 도착한 말이 밀정 말이라면 게임에서 패배한다. 20. 밀정을 제외한 모든 말이 상대팀 보다 먼저 도착 지점에 도착하면 게임에서 승리한다. | 매우 높음 |
| F09_02 | 게임진행 | 게임 종료 된 후 게임 종료 페이지에서 게임에 대한 통계를 확인할 수 있다. | 높음  |
| F10 | 도움말 기능 | 게임 대기 화면과 게임 중 게임 룰에 대한 도움말을 확인 할 수 있어야 한다. 도움말에는 게임 룰, 서브 미션 방법이 포함되어 있어야 한다. | 높음  |
| F11_01 | 사운드 | 각 화면에 맞는 적절한 배경음악을 재생한다. | 보통  |
| F11_02 | 사운드 | 각 화면에 맞는 적절한 효과음이 있어야한다. | 보통  |
| F11_03 | 사운드 | 모든 사운드는 사용자가 적절히 조정할 수 있어야한다. | 보통  |
| F12 | 신고  | 사용자는 부정 플레이어에 대하여 게임 종료 후 신고할 수 있다. | 낮음  |
| F13_01 | 게시판 기능 | 사용자들이 참여할 수 있는 커뮤니티 기능이 있어야한다. 게시판은 공지사항, 일반 게시판, FAQ, 1:1 문의 게시판으로 이루어져야한다. | 매우 낮음 |
| F13_02 | 게시판 기능 | 각 게시판은 권한에 따른 글 작성 기능을 제공해야 한다. 각 게시판은 권한에 따른 글 삭제 / 수정 기능을 제공해야 한다. 각 게시판은 글 목록 및 페이지 네비게이션 / 검색 필터를 제공해야 한다. | 매우 낮음 |
| F14_01 | 상점  | 상점에서 파는 물품은 게임 플레이에 영향을 미쳐서는 안된다. | 매우 낮음 |
| F14_02 | 상점  | 사용자는 상점에서 물품을 구매할 수 있다. | 매우 낮음 |
| F14_03 | 상점  | 사용자는 구매한 물품을 인벤토리에 저장할 수 있어야 한다. | 매우 낮음 |
| F15_01 | 관리자 기능 | 관리자 계정으로 로그인 시 관리자 페이지가 활성화 되어야 한다. | 낮음  |
| F15_02 | 관리자 기능 | 관리자는 신고 목록을 확인하고 신고 피드백을 할 수 있어야 한다. | 낮음  |
| F15_03 | 관리자 기능 | 관리자는 신고 피드백을 통해 불건전 유저에게 제제를 가할 수 있어야 한다. | 낮음  |
| F15_04 | 관리자 기능 | 관리자는 1 대 1 문의를 확인하고 답글을 남길 수 있어야 한다, | 낮음  |
| F15_05 | 관리자 기능 | 관리자는 사이트에 대한 각종 통계를 확인할 수 있어야 한다. | 낮음  |
| F15_06 | 관리자 기능 | 관리자는 전체 유저 목록을 확인하고 관리할 수 있어야 한다. | 낮음  |

#### 비기능적 요구사항

| 순번  | 요구사항명 | 요구사항 상세 |
| --- | --- | --- |
| NF1 | 사용자 편의성 | 사이트에 대한 사전지식이 없어도 쓰기 편리해야한다. |
| NF2 | 실시간 통신 | 1. 6명 이하의 동시 화상 통신 및 채팅이 가능해야한다. |
|     |     | 2. 영상 통신 딜레이가 100ms 이하여야 한다. |
| NF3 | 응답성 | 방 리스트 조회에 걸리는 시간이 2초 이하여야 한다. |
|     |     | 방 생성 시 걸리는 시간이 3초 이하여야 한다. |
|     |     | 방 입장 시 걸리는 시간이 3초 이하여야 한다. |
| NF4 | 호환성 | 데스크탑 PC의 크롬 브라우저 권장 |
| NF5 | 보안  | 비밀번호 등 중요한 개인 정보를 서버에 전송할 시 https로 보안이 보장되어야한다. |

### 📌기능 명세서

| 대분류 | 소분류 | 주기능 | 상세 기능 | 우선 순위 |
| --- | --- | --- | --- | --- |
| 1. 회원 | 1.1 회원 가입 | 1.1.1 이메일 입력 | 이메일을 텍스트 박스로 입력 입력 받은 이메일이 규칙을 준수한 이메일인지 확인 이미 가입된 이메일인지 확인 | 매우 높음 |
|     |     | 1.1.2 닉네임 입력 | 닉네임을 텍스트 박스로 입력 입력 받은 닉네임이 규칙을 준수한 닉네임인지 정규식으로 확인 이미 등록된 닉네임인지 확인 | 매우 높음 |
|     |     | 1.1.3 비밀번호 입력 | 비밀번호를 텍스트 박스로 입력 입력 받은 비밀번호가 규칙을 준수한 비밀번호인지 확인 사용자가 입력한 비밀번호를 이중으로 확인 | 매우 높음 |
|     |     | 1.1.4 생년월일 선택 | 사용자의 생년월일을 연도, 월, 일 각자 하나씩 선택 | 매우 높음 |
|     |     | 1.1.5 성별 선택 | 사용자의 성별을 남, 여 중 하나 선택 | 매우 높음 |
|     |     | 1.1.6 회원 가입 실행 | 회원 정보 폼을 Json으로 변환 후 POST방식으로 백엔드로 전달 전달 받은 회원 정보를 데이터베이스에 저장 | 매우 높음 |
|     | 1.2 로그인 | 1.2.1 이메일 입력 | 이메일을 텍스트 박스로 입력 입력 받은 이메일 정규식 검사 | 매우 높음 |
|     |     | 1.2.2 비밀번호 입력 | 비밀번호를 텍스트 박스로 입력 | 매우 높음 |
|     |     | 1.2.3 데이터 전송 | 이메일 과 비밀번호 Json 형식으로 서버 전송 https 보안 필요 | 매우 높음 |
|     |     | 1.2.4 로그인 성공 | 서버에서 상태코드 200을 받은 경우 메인 페이지로 이동 | 매우 높음 |
|     |     | 1.2.5 로그인 실패 | 서버에서 상태코드 500 또는 400을 받은 경우 에러페이지 화면으로 이동 | 매우 높음 |
|     |     | 1.2.6 토큰 저장 | 로그인에 성공하였을 경우 서버에서 받은 토큰 값을 로컬 스토리지에 저장하여 관리한다. | 매우 높음 |
|     |     | 1.2.7 토큰 관리 | 로그인 이후 모든 api 요청헤더에 access token 값을 넣어 보낸다. 서버 응답이 만료된 토큰이라면 refresh token을 보내 재인증 과정을 거쳐 access token을 최신화 한다. | 매우 높음 |
|     | 1.3 로그아웃 | 1.3.1 토큰 삭제 | 로그아웃 시 서버에서 받은 토큰 값을 삭제한다. 로그아웃 버튼 클릭 시 로그아웃에 성공하였을 경우 시작 화면으로 이동한다. | 매우 높음 |
|     | 1.4 비밀번호 찾기 | 1.4.1 이메일 입력 및 확인 | 해당 이메일로 인증을 요구 인증 완료 시 비밀번호 변경 | 높음  |
|     | 1.5 회원 정보 조회 | 1.5.1 유저 인증 | 토큰 값으로 접근 권한 확인하고 비밀번호를 입력으로 인증 요구 | 높음  |
|     |     | 1.5.2 유저 정보 조회 | 이메일, 닉네임, 생년월일, 성별을 정보 확인 | 높음  |
|     |     | 1.5.3 유저 전적 정보 조회 | 유저의 게임 전적 조회 | 낮음  |
|     | 1.6 회원 정보 수정 | 1.6.1 닉네임 변경 | 수정한 닉네임이 이미 등록된 닉네임인지 확인한다. 수정한 닉네임이 정규식을 준수한 닉네임인지 확인한다. | 높음  |
|     |     | 1.6.2 비밀번호 변경 | 사용자가 원래 사용하던 비밀번호를 입력하여 등록된 사용자인지 확인한다. 사용자가 사용할 비밀번호를 입력 받은 후 규칙을 준수한 비밀번호인지 확인 사용자가 입력한 비밀번호를 이중으로 확인 모든 조건을 만족했다면 비밀번호 수정 완료 | 높음  |
|     | 1.7 회원 탈퇴 | 1.7 회원 탈퇴 | 해당 회원의 정보를 모두 삭제 | 높음  |
|     | 1.8 친구 | 1.8.1 친구 목록 | 사용자와 서로 친구인 친구의 목록 출력 | 보통  |
|     |     | 1.8.2 친구 요청 | 친구 요청 보내기 | 보통  |
|     |     | 1.8.3 친구 요청 거부 | 친구 요청 거부 | 보통  |
|     |     | 1.8.4 친구 요청 수락 | 친구 요청 수락 | 보통  |
|     |     | 1.8.5 친구 채팅 | 친구 목록에 있는 사용자와 1:1 채팅 채팅 문구 입력 후 전송 시 채팅 화면에 문구가 뜸 | 매우 높음 |
| 2. 방 관리 | 2.1 방 생성 | 2.1.1 방 제목 설정 | 방의 제목을 설정 | 매우 높음 |
|     |     | 2.1.2 맵 설정 | 맵의 테마를 설정 | 보통  |
|     |     | 2.1.3 게임 속도 설정 | 게임 속도를 설정 (느림, 보통, 빠름) | 높음  |
|     |     | 2.1.4 공개 / 비공개 여부 설정 | 방을 누구나 들어올 수 있는 공개로 할지 비밀번호를 입력해야 들어올 수 있는 비공개로 할지 설정 비공개 시 비밀번호를 입력해야함. | 높음  |
|     | 2.2 방 삭제 | 2.2.1 방 삭제 | 방장이 방에서 나가면 방을 삭제 | 매우 높음 |
|     | 2.3 방 리스트 조회 | 2.3.1 현재 생성된 방 조회 | 현재 생성된 Session 정보 리스트를 전달 플레이어 수 ex) 2/6 \\| 방 제목 \\| 공개 여부 식으로 표현 | 매우 높음 |
|     | 2.4 방 세부 정보 | 2.4.1 방 세부 정보 조회 | 방을 클릭하면 방 세부 정보 조회를 표시 방 제목, 인원, 맵 테마, 게임 속도, 윷놀이 판 사진을 표시 | 높음  |
|     | 2.5 방 참여 | 2.5.1 방 참여 | 방 목록에서 방을 클릭 후, 방 참여 버튼을 통해 방 참여 또는 초대를 받았을 경우 수락 버튼을 통해 방 참여 | 매우 높음 |
|     | 2.6 방 검색 | 2.6.1 방을 제목으로 검색 | 방을 제목으로 검색 | 높음  |
|     | 2.7 게임 대기실 | 2.7.1 팀 선택 | 방에 참여한 사람들은 팀을 선택 | 매우 높음 |
|     |     | 2.7.2 대기실 채팅 | 방에 참여한 사용자 전체와 채팅 채팅 문구 입력 후 전송 시 채팅 화면에 문구가 뜨게 한다. | 매우 높음 |
|     |     | 2.7.3 방 상세 정보 조회 | 참여한 방의 정보 조회를 표시 방 제목, 인원, 맵 테마, 게임 속도, 윷놀이 판 사진을 표시 | 높음  |
|     |     | 2.7.4 친구 추가 요청 | 방에 참여한 인원에게 친구 추가 요청 | 보통  |
|     |     | 2.7.5 추방 | 방에 참여한 인원을 추방 | 높음  |
|     |     | 2.7.6 게임 시작 | 방에 참여한 모든 사람들이 시작 버튼을 눌렀다면 방장은 게임 시작 가능 | 매우 높음 |
| 3. 게임 | 3.1 게임 사전 준비 | 3.1.1 캐릭터 선택 | 제한 시간 안에 각 팀원들은 차례대로 주어진 캐릭터를 선택 캐릭터는 중복으로 선택 불가 제한 시간이 초과될 경우 랜덤으로 캐릭터 선택 | 매우 높음 |
|     |     | 3.1.2 밀정 선택 | 제한 시간 안에 각 팀원 중 대표 1명이 상대방이 고른 캐릭터 중 하나를 밀정으로 선택 제한 시간이 초과될 경우 랜덤으로 밀정 선택 | 매우 높음 |
|     | 3.2 본 게임 | 3.2.1 윷 던지기 | 윷을 던져 말의 이동거리를 받음. 제한 시간 안에 윷을 던지지 않으면 자동으로 윷이 던져짐. 만약 모나 윷이 나왔다면 한 턴의 기회를 더 가짐. | 매우 높음 |
|     |     | 3.2.2 추리 | n번의 턴이 지나면 추리권이 생김 추리권으로 밀정 말을 찾을 수 있음. 추리권을 사용하면 한 턴을 소모함. (윷 던지기 불가) 추리에 성공하면 밀정 말은 상대 말이 되어 상대 팀으로 돌아감 추리에 실패하면 지목된 말은 3턴 간 이동 불가 | 매우 높음 |
|     |     | 3.2.3 도움말 기능 | F1을 누르면 게임 방법을 배울 수 있는 도움말 창 나타남. | 높음  |
|     |     | 3.2.4 캐릭터 정보 | 양쪽 사이드에 위치한 말에 마우스 커서를 올리면 바로 옆에 캐릭터 정보와 스킬 정보가 나타남. | 높음  |
|     |     | 3.2.5 말 옮기기 | 윷 결과에 따라 말을 옮길 수 있음. 움직인 말이 상대 말 위치에 도달하면 상대 말을 잡고, 한 턴의 기회를 더 얻음. 움직인 말이 자신의 팀 말 위치에 도달하면 말을 합칠 수 있음. 합쳐진 말은 다음 턴 부터 같이 움직일 수 있음. | 매우 높음 |
|     |     | 3.2.6 스킬 사용 | 말 이동이 끝나면 필드의 말을 선택하여 스킬을 사용할 수 있음. 각 말마다 스킬에 쿨타임이 있음. 쿨타임이 다 찬 캐릭터는 윷을 던지고 해당 캐릭터 스킬을 사용할 수 있음. | 매우 높음 |
|     |     | 3.2.7 승리 | 상대 말 중 밀정 말이 먼저 들어가거나, 밀정을 제외한 우리 팀 말이 전부다 들어오면 승리함. | 매우 높음 |
|     |     | 3.2.8 미션 지역 | 맵에 특정 위치에 미션 발판이 있음 말이 해당 위치로 이동하면 미션을 수행 가능 | 매우 높음 |
|     |     | 3.2.9 캠 관리 | 모든 플레이어는 캠을 키고 끌 수 있음. | 매우 높음 |
|     |     | 3.2.10 음성 관리 | 음성은 팀끼리 음성 대화가 가능. 음성을 끄고 킬 수 있음. | 매우 높음 |
|     |     | 3.2.11 게임 환경 설정 | 환경설정 버튼을 클릭하면 게임을 나가거나 종료할 수 있음. 게임의 사운드를 조절할 수 있음. | 보통  |
|     |     | 3.2.12 게임 상태 관리 | 게임 진행 정보를 서버와 클라이언트 모두 동일하게 관리 | 매우 높음 |
|     |     | 3.2.13 게임 상태 갱신 | 사용자가 특정 행동을 하여 게임 정보가 갱신되는 경우 갱신된 정보를 서버로 전송 | 매우 높음 |
|     |     | 3.2.14 게임 상태 불러오기 | 서버는 게임 상태가 변경된 경우 모든 참여자에게 변경된 상태를 보내 같은 상태를 유지 | 매우 높음 |
|     |     | 3.2.15 게임 조기 항복 | 게임 턴이 n번 지났다면, 항복버튼이 활성화 팀원 투표를 통해 조기 항복 가능 | 낮음  |
|     | 3.3 미션 | 3.3.1 미션 발판 | 본 게임 중 기물이 미션 발판에 도착할 경우 미션 모달 창 생성 | 매우 높음 |
|     |     | 3.3.2 미션 룰렛 | 어떤 미션이 진행될 지 룰렛을 통해 랜덤으로 결정 | 매우 높음 |
|     |     | 3.3.3 광물 캐기 | 1. 미션이 걸린 팀 3명의 캠이 표시됨. 2. 5초 카운트 다운 후 게임 시작 3. 시작하면 화면에 광물 이미지가 뜸 4. 광물을 캐는 행동이 캠에 비춰져야 광물을 캘 수 있음 5. 팀은 일정 갯수 이상의 광물을 캐야 미션 성공 6. 미션을 성공하면 밀정에 대한 단서를 어등ㄹ 수 있음. | 매우 높음 |
|     |     | 3.3.4 화면 야바위 | 1. 상대방 캠 화면이 표시됨 2. 찾아야 할 대상을 랜덤으로 지정해 줌 3. 3초 후 상대방 화면이 검은 화면으로 변함 4. 무작위로 섞이는 애니메이션이 표출되고 5. 미션 팀은 상의를 통해 결과를 선택 6. 결과 선택은 검은색으로 변한 캠 아래에 버튼을 통해서 선택 가능함. 7. 선택과 동시에 모든 캠이 오픈 8. 정답 여부를 알려줌. 9. 정답일 경우 밀정에 대한 단서를 얻을 수 있음. | 매우 높음 |
|     |     | 3.3.5 계란 받기 | 1. 미션이 걸린 팀원들의 캠이 표시됨. 2. 5초 카운트 다운 후 게임 시작 3. 시작하면 각 팀원들의 화면 상단에 계란이 떨어짐 4. 각 팀원들은 손으로 계란을 받아야 계란을 받을 수 있음 5. 팀은 일정 개수 이상의 계란을 받아야 미션 성공 6. 미션을 성공하면 밀정에 대한 단서를 얻을 수 있음. | 매우 높음 |
|     |     | 3.3.6 파리 잡기 | 1. 미션이 걸린 팀원들의 화면이 나란히 표시됨 2. 5초 카운트다운 이후에 미션이 시작된다. 3. 제한시간 안에 모든 참여자가 화면의 파리를 모두 잡으면 성공 4. 파리를 잡는 모션은 인공지능을 활용하여 판별. 5. 미션을 성공하면 밀정에 대한 단서를 얻을 수 있음. | 매우 높음 |
|     |     | 3.3.7 참참참 | 1. 미션 팀에서 시스템 무작위로 한 명을 선정하여 미션 진행 2. 선정된 사용자의 캠 화면이 표시되고 3. 캠 화면 안에 손바닥이 표시된다 4. 참참참 사운드와 함께 손바닥이 움직이고 5. 사용자가 손바닥을 피하여 움직일 경우 성공 | 매우 높음 |
|     |     | 3.3.8 가위바위보 | 1. 각 팀의 인원 중 시스템 무작위로 한 명씩 선택 2. 두 인원의 캠이 화면에 나란히 표출. 3. 카운트다운 5초 이후 경기가 시작 4. 가위바위보는 인공지능을 활용하여 판별 5. 미션 팀이 승리하였을 경우 단서를 얻을 수 있음. | 매우 높음 |
|     |     | 3.3.9 고요속의 외침 | 1. 미션이 걸린 팀원들의 화면이 표시됨. 2. 화면 배치 순서는 랜덤 3. 미션을 진행하는 팀원들의 모든 음성 채팅이 음소거 4. 미션을 진행하는 팀원들의 모든 채팅이 블락 5. 맞춰야 할 제시어가 주어진다. 해당 제시어는 가장 왼쪽 화면에 있는 팀원에게 주어짐. 6. 제시어를 받은 팀원은 제한 시간 8초안에 입 모양으로 제시어를 설명 해야 함. 설명을 들은 나머지 두 팀원은 각각 입 모양을 보고 제시어를 유추해야함. 7. 두 팀원은 유추한 제시어를 텍스트 박스에 입력. 8. 두 팀원 모두 제시어를 맞췄을 경우 미션 성공 9. 미션을 성공하면 밀정에 대한 단서를 얻을 수 있음. | 매우 높음 |
|     |     | 3.3.10 눈싸움 | 1. 각 팀의 인원 중 시스템 무작위로 한 명씩 선택. 2. 두 인원의 캠이 화면에 나란히 표출. 3. 카운트다운 5초 이후 경기가 시작. 4. 눈을 감았는지는 인공지능을 활용하여 판별. 5. 먼저 눈을 감을 경우 패배. 6. 미션팀이 승리하였을 경우 단서를 얻을 수 있음. | 매우 높음 |
|     | 3.4 캐릭터 | 3.4.1 대왕 | 대왕 캐릭터 스킬 : (패시브)밀정이 될 수 없습니다. | 매우 높음 |
|     |     | 3.4.2 기병 | 기병 캐릭터 스킬 : (패시브)나온 숫자 + 1칸 만큼 이동 할 수 있습니다. | 매우 높음 |
|     |     | 3.4.3 창병 | 창병 캐릭터 스킬 : (액티브)앞이나 뒤에 있는 상대 캐릭터를 1턴간 스턴시킨다. | 매우 높음 |
|     |     | 3.4.4 노비 | 노비 캐릭터 스킬 : (패시브)나온 숫자 -1 칸 만큼 이동 할 수 있습니다. | 매우 높음 |
|     |     | 3.4.5 농민 | 농민 캐릭터 스킬 : 없음 | 매우 높음 |
|     | 3.5 게임 채팅 | 3.5.1 전체 채팅 | 게임에 참여한 모든 유저들과 채팅 채팅 문구 입력 후 전송 시 채팅 화면에 문구가 뜸 | 매우 높음 |
|     |     | 3.5.2 팀원 채팅 | 같은 팀 팀원들과 채팅 채팅 문구 입력 후 전송 시 채팅 화면에 문구가 뜸 | 매우 높음 |
|     | 3.6 게임 종료 | 3.5.3 게임 통계 | 게임 진행 턴, 미니 게임 종류 및 성공, 실패, 기물 통과 순서, 선택 캐릭터, 밀정 캐릭터 | 보통  |
|     |     | 3.5.4 신고 | 부정행위 플레이어, 욕설 플레이어, 불쾌감을 유발하는 플레이어, 게임 진행을 의도적으로 방해하는 플레이어 에 대한 신고 기능 | 낮음  |
| 4. 게시판 | 4.1 커뮤니티 | 4.1.1 글 목록 | 커뮤니티 글 목록 10개 표시 페이지 전환 시 10개의 글 목록 표시 | 낮음  |
|     |     | 4.1.2 글 검색 | 글 제목 혹은 작성자로 특정 글 검색 | 낮음  |
|     |     | 4.1.3 글 정렬 | 조회수 / 추천 수 / 댓글 수에 따라 정렬 (오름차순, 내림차순) | 낮음  |
|     |     | 4.1.4 글 상세 | 유형(일반, 공략, 토론) / 제목 / 작성자 / 게시일 상단에 표시 내용 중앙에 표시 첨부 파일 / 조회수 / 추천 수/ 댓글 수 최하단에 표시 | 낮음  |
|     |     | 4.1.5 글 작성 | 글 유형 / 제목 / 내용/ 첨부파일 작성 글 작성 에디터 적용 | 낮음  |
|     |     | 4.1.6 글 수정 | 글 유형 / 제목 / 내용 / 첨부파일 수정 | 낮음  |
|     |     | 4.1.7 글 삭제 | 글 상세에서 작성자만 삭제 버튼 활성화 클릭 시 글 삭제 | 낮음  |
|     | 4.2 공지사항 | 4.2.1 글 목록 | 공지사항 글 목록 10개 표시 페이지 전환 시 10개의 글 목록 표시 | 낮음  |
|     |     | 4.2.2 글 검색 | 글 제목으로 특정 글 검색 | 낮음  |
|     |     | 4.2.3 글 상세 | 제목 / 게시일 상단에 표시 내용 중앙에 표시 | 낮음  |
|     |     | 4.2.4 글 작성 | 관리자만 제목 / 내용 작성 글 작성 에디터 적용 | 낮음  |
|     |     | 4.2.5 글 수정 | 관리자만 제목 / 내용 수정 | 낮음  |
|     |     | 4.2.6 글 삭제 | 글 상세에 관리자만 삭제 버튼 활성화 클릭 시 글 삭제 | 낮음  |
|     | 4.3 FAQ | 4.3.1 글 목록 | FAQ 글 목록 10개 표시 페이지 전환 시 10개의 글 목록 표시 | 낮음  |
|     |     | 4.3.2 글 검색 | 글 제목으로 특정 글 검색 | 낮음  |
|     |     | 4.3.3 글 상세 | 제목 / 게시일 상단에 표시 내용 중앙에 표시 | 낮음  |
|     |     | 4.3.4 글 작성 | 관리자만 제목 / 내용 작성 글 작성 에디터 적용 | 낮음  |
|     |     | 4.3.5 글 수정 | 관리자만 제목 / 내용 수정 | 낮음  |
|     |     | 4.3.6 글 삭제 | 글 상세에 관리자만 삭제 버튼 활성화 클릭 시 글 삭제 | 낮음  |
| 5. 관리자 기능 | 5.1 관리자 로그인 | 5.1.1 관리자 로그인 | 관리자 계정으로 로그인 시 관리자 페이지 버튼 생성 | 낮음  |
|     | 5.2 회원 관리 | 5.2.1 회원 목록 | 전체 회원 목록을 확인 |     |
|     |     | 5.2.2 회원 상세 | 회원의 상세 정보를 표시한다. |     |
|     |     | 5.2.3 회원 관리 | 회원 상세 정보에서 회원 추방 및 제제를 관리할 수 있다. |     |
|     |     | 5.2.4 신고 목록 | 전체 신고 목록 표시 검색 및 필터 기능 적용 |     |
|     |     | 5.2.5 신고 상세 | 신고 목록에서 선택하여 신고에 대한 상세 내용 확인 피드백을 통해 신고자에서 처리 내용 전달 신고된 유저에게 가할 제제를 선택하여 적용 |     |
|     |     | 5.2.6 1대1 문의 목록 | 전체 1대1 문의 목록 표시 검색 및 필터 기능 적용 |     |
|     |     | 5.2.7 1대1 문의 상세 | 1대 1 문의에서 선택하여 문의 상세 내용 확인 |     |
|     |     | 4.2.8 1대 1 문의 답장 | 1대 1 문의 상세에서 답글 작성 버튼을 통해 답글 작성 |     |
|     | 3. 사이트 통계 | 4.3.1 일 방문자 추이 | 사이트 일 방문자 추이를 그래프로 보여줌 |     |
|     |     | 4.3.2 가입자 추이 | 사이트 가입자의 추이를 그래프로 보여줌 |     |
|     |     | 4.3.3 현재 접속자 수 | 현재 사이트 접속자 수를 보여줌 |     |
|     |     | 4.3.4 현재 진행중인 게임 수 | 현재 진행중인 게임의 수를 보여줌 |     |

### 📌API 명세서

#### HTTPS

| 도메인 | Description | Method | EndPoint | Request Body | Response Body | Status |
| --- | --- | --- | --- | --- | --- | --- |
| User | 회원가입 | POST | /user/join | { email : “String”, nickname : “String”, password : “String”, birthDate : “String”, gender : “String” } |     | 200, “회원가입성공” 400, “회원가입실패” |
| User | 이메일 중복 체크 | GET | /user/email/{email} |     | 200, “사용 가능한 email 입니다.” 400, “이미 존재하는 사용자 email 입니다.” |     |
| User | 닉네임 중복 체크 | GET | /user/nickname/{nickname} |     | 200, “사용 가능한 닉네임 입니다.” 400, “이미 존재하는 사용자 닉네임 입니다.” |     |
| User | 이메일 인증 메일 전송 | POST | /user/emails/verification-requests | @param ?email=”” |     | 200 |
| User | 이메일 인증 | POST | /user/emails/verification | @param ?email=””&code=”” |     | 200 406 |
| User | 회원탈퇴 | DELETE | /user/deleat-account |     |     | 200 |
| User | 로그인 | POST | /auth/login | { email : String, password : String } | { refreshToken : String, accessToken : String } |     |
| User | 로그아웃 | GET | /user/logout |     |     | 200 |
| User | 비밀 번호 찾기 이메일 인증 요청 | POST | /user/get-temporary-password-email-verification-request | @param ?email=”” |     | 200 406 |
| User | 비밀 번호 찾기 이메일 인증 | POST | /user/get-temporary-password-email-verification | @param ?email=””&code=”” |     | 200 406 |
| User | 회원 정보 조회 | GET | /user/info |     | { “nickname” : string “email” : string “birth_date” : string “gender” : string “profile_url” : string } |     |
| User | 전적 조회 | GET | /user/stat |     | { gameSpeed : int, gameTheme : String, winner : String, blueSpyName : String, redSpyName : String, redSpyHint : String, blueSpyHint : String, redTeamReasoningResult : boolean, blueTeamReasoningResult : boolean, blueTeamMemberDtoList : { nickname : String } redTeamMemberDtoList : { nickname : String } blueTeamUnitDtoList : { name : String. place : String, time : String, contactor : String stuff : String, scal : String skill : String } redTeamUnitDtoList : { name : String. place : String, time : String, contactor : String stuff : String, scal : String skill : String } } |     |
| User | 비밀 번호 변경 | POST | /user/change-password | { previousPassword : “String”, nextPassword : “String” } |     | 200, 400 |
| User | 닉네임 변경 | POST | /user/change-nickname | String |     | 200 |
| User | 유저 검색 | GET | /user/search/{nickname} |     |     | { email : String, nickname : String, } |
| Friend | 내 친구 목록 | GET | /friend/myfriend |     | { { email : String, nickname : String isOnline : String }, } |     |
| Friend | 친구 요청 | POST | /friend/send | { toUserEmail : String, toUserNickname : String } | 200 “친구 요청 완료” 200 “이미 처리된 요청” |     |
| Friend | 친구 수락 | POST | /friend/accept | { fromUserEmail : String, fromUserNickname : String } | 200 “요청 수락 완료” 200 “이미 처리된 요청” |     |
| Friend | 내 요청 목록 | GET | /friend/send |     | { { toUserEmail : String, toUserNickname : String }, } |     |
| Friend | 받은 요청 목록 | GET | /friend/receive |     | { { fromUserEmail : String, fromUserNickname : String }, } |     |
| Friend | 요청 거절 | POST | /friend/reject | { fromUserEmail : String, fromUserNickname : String } | 200 ”요청 거절 완료” |     |
| Room | 방 생성 | POST | /room/create | header : accessToken { "title": string, "isPublic": boolean, "password": string, "theme": string, "speed": number } | room code 반환 | 201 - 방 생성 성공 401 - 토큰이 유효하지 않음 404 - 사용자를 찾을 수 없음 500 : 서버 에러 |
| Room | 방 목록 조회 | GET | /room |     | [ { "roomId": number, ”currentUserCount": number, "title": string, "public": boolean }, { "roomId": number, ”currentUserCount": number, "title": string, "public": boolean } ] |     |
| Room | 방 세부 정보 조회 | GET | /room/detail/{roomId} |     | { “title” : string, “isPublic” : boolean, “gameSpeed” : number, “currentUserCount” : number, “theme” : string } |     |
| Room | 방 입장 가능 여부 조회 | POST | /room/{roomId} | { ”password” : string } | room code를 반환함 | 200 - 입장 가능 403 - 인원 수 초과 404 - 유효하지 않은 room id |
| Board | 글 목록 | GET | /board/list |     | [ { "id": 1, "subject": "String.", "content": "String." ”created_date” : “String” }, { "id": 2, "subject": "String.", "content": "String." ”created_date” : “String” }, { "id": 3, "subject": "String.", "content": "String." ”created_date” : “String” }, { "id": 4, "subject": "String.", "content": "String." ”created_date” : “String” }, { "id": 5, "subject": "String.", "content": "String." ”created_date” : “String” } ] |     |

#### wss pub

| 도메인 | Description | EndPoint | Request Body | Response Body |
| --- | --- | --- | --- | --- |
| Room | 방 입장 | /pub/room/{roomCode}/enter | { destination : /pub/room/{roomCode}/enter body : email } | { "type":"ROOM_ENTER_INFO", "code":string "data":{ "ownerId":number, "currentSeatDtoList":[ {"team":1,"seatNumber":0,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":1,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":2,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":3,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":4,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":5,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}], "roomDetailDto":{ "title":string, "gameSpeed":number, "currentUserCount":number, "theme":string, "public":boolean }, "message": string “roomState” : 방이 삭제되지 않았더라면 0, 방이 삭제 되었다면 1 } } |
| Room | 방 나가기 (방장이면 방 삭제) | /pub/room/{roomCode}/exit | { destination : /pub/room/{roomCode}/enter body : email } | { "type":"ROOM_EXIT_INFO", "code":string, "data":{ "ownerId":number, "currentSeatDtoList":[ {"team":1,"seatNumber":0,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":1,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":2,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":3,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":4,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":5,"userId":0,"nickname":null,"profileImgUrl":null,"state":0,"ready":false}], "roomDetailDto":{ "title":string, "gameSpeed":number, "currentUserCount":number, "theme":string, "public":boolean }, "message":string “roomState” : 방이 삭제되지 않았더라면 0, 방이 삭제 되었다면 1 } } |
| Room | 방 채팅 | /pub/room/{roomCode}/chat | body : { nickname : string message : string } | { "type":"ROOM_CHAT", "code":string, "data":{ "nickname":string, "message":"string" } } |
| Room | 방 준비완료 | /pub/room/{roomCode}/ready | { destination : /pub/room/{roomCode}/ready body : email } | { "type":"ROOM_READY", "code":string, "data"[ {"team":1,"seatNumber":0,"userId":1,"nickname":”참여자1","profileImgUrl":null,"state":0,"ready":true}, {"team":1,"seatNumber":1,"userId":45,"nickname":”참여자2”,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":2,"userId":7,"nickname":”참여자3”,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":3,"userId":19,"nickname":””참여자4”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":4,"userId":25,"nickname":”참여자5”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":5,"userId":34,"nickname":”참여자6”,"profileImgUrl":null,"state":0,"ready":false} ] } |
| Room | 방 팀 변경 | /pub/room/{roomCode}/change | { destination : /pub/room/{roomCode}/change body : email } | { "type":"ROOM_CHANGE_TEAM", "code":string, "data"[ {"team":1,"seatNumber":0,"userId":1,"nickname":”참여자1","profileImgUrl":null,"state":0,"ready":true}, {"team":1,"seatNumber":1,"userId":45,"nickname":”참여자2”,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":2,"userId":7,"nickname":”참여자3”,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":3,"userId":19,"nickname":””참여자4”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":4,"userId":25,"nickname":”참여자5”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":5,"userId":34,"nickname":”참여자6”,"profileImgUrl":null,"state":0,"ready":false} ] } |
| Room | 방 시작 (캐릭터 창으로 이동) | /pub/room/{roomCode}/start | { destination : /pub/room/{roomCode}/start body : email } | { "type":"ROOM_START_PICK", "code":string, "data":”캐릭터 선택 시작” } |
| Pick | 캐릭터 픽 전 사전 정보 전달 | /pub/pick/{roomCode}/get-pre-info | { destination : /pub/pick/{roomCode}/get-pre-info } | 홍팀 일 경우 { "type":"PICK_GET_PRE_INFO", "code":"232618/red", "data":{ "userInfo"[ {"team":1,"seatNumber":0,"userId":2,"nickname":"주니","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":1,"userId":26,"nickname":"테스트1","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":2,"userId":27,"nickname":"테스트2","selectUnitId":0,”pick":false} ], "unitInfo":[ {"unitId":1,"name":"대왕","age":40,"skill":"밀정면역","pick":false}, {"unitId":2,"name":"창병","age":30,"skill":"기절","pick":false}, {"unitId":3,"name":"기병","age":32,"skill":"치명적속도","pick":false}, {"unitId":4,"name":"농민","age":24,"skill":"농민도스킬이쓰고싶어요","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"노비의무게","pick":true} ] } } 청팀 일 경우 { "type":"PICK_GET_PRE_INFO", "code":"232618/blue", "data":{ "userInfo"[ {"team":2,"seatNumber":3,"userId":46,"nickname":"참여자4","selectUnitId":0,”pick":false}, {"team":2,"seatNumber":4,"userId":52,"nickname":"참여자5","selectUnitId":0,”pick":false}, {"team":2,"seatNumber":5,"userId":27,"nickname":"참여자6","selectUnitId":0,”pick":false} ], "unitInfo":[ {"unitId":1,"name":"대왕","age":40,"skill":"밀정면역","pick":false}, {"unitId":2,"name":"창병","age":30,"skill":"기절","pick":false}, {"unitId":3,"name":"기병","age":32,"skill":"치명적속도","pick":false}, {"unitId":4,"name":"농민","age":24,"skill":"농민도스킬이쓰고싶어요","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"노비의무게","pick":true} ] } } |
| Pick | 캐릭터 픽 시작 | /pub/pick/{roomCode}/start | { destination : /pub/pick/{roomCode}/start } | 홍팀 일 경우 { "type":"PICK_ORDER", "code":"232618/red", "data":{ email : “픽을 해야하는 유저의 이메일”, time : 타이머 시간 } } 청팀 일 경우 { "type":"PICK_ORDER", "code":"232618/blue", "data":{ email : “픽을 해야하는 유저의 이메일”, time : 타이머 시간 } } |
| Pick | 캐릭터 픽 선택 | /pub/pick/{roomCode}/select | { destination: /pub/pick/{roomCode}/select body:{ team: “홍팀” or “청팀”, email : 선택한 유저의 이메일, unitId : 선택한 유닛 아이디 } } | 홍팀 일 경우 { "type":"PICK_SELECT", "code":"232618/red", "data":{ "userInfo"[ {"team":1,"seatNumber":0,"userId":2,"nickname":"주니","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":1,"userId":26,"nickname":"테스트1","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":2,"userId":27,"nickname":"테스트2","selectUnitId":0,”pick":false} ] } } 청팀 일 경우 { "type":"PICK_SELECT", "code":"232618/blue", "data":{ "userInfo"[ {"team":2,"seatNumber":3,"userId":46,"nickname":"참여자4","selectUnitId":0,”pick":false}, {"team":2,"seatNumber":4,"userId":52,"nickname":"참여자5","selectUnitId":0,”pick":false}, {"team":2,"seatNumber":5,"userId":27,"nickname":"참여자6","selectUnitId":0,”pick":false} ] } } |
| Pick | 캐릭터 픽 선택 완료 | /pub/pick/{roomCode}/done | { destination: /pub/pick/{roomCode}/done body:{ team: “홍팀” or “청팀”, email : 선택한 유저의 이메일, unitId : 선택한 유닛 아이디 } } | 양팀 픽이 끝나지 않았다면 { "type":"PICK_NEXT", "code":"232618/red", "data":{ email : “픽을 해야하는 유저의 이메일”, time : 타이머 시간 } } 한 팀만 픽이 끝났다면 { "type":"PICK_WAIT", "code":"232618/red", "data": “모든 픽이 완료될 때까지 기다려주세요.” } 양팀 다 픽이 끝났다면 { "type":"PICK_SELECT_SPY", "code":"232618/red", “data”: [ {"unitId":1,"name":"대왕","age":40,"skill":"","pick":true}, {"unitId":2,"name":"창병","age":30,"skill":"","pick":true}, {"unitId":3,"name":"기병","age":32,"skill":"","pick":true}, {"unitId":4,"name":"농민","age":24,"skill":"","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"","pick":true} ] } 픽 한 정보 전달 (무조건) { "type":"PICK_SELECT_DONE", "code":"232618/red", "data":{ "userInfo"[ {"team":1,"seatNumber":0,"userId":2,"nickname":"주니","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":1,"userId":26,"nickname":"테스트1","selectUnitId":0,”pick":false}, {"team":1,"seatNumber":2,"userId":27,"nickname":"테스트2","selectUnitId":0,”pick":false} ], "unitInfo":[ {"unitId":1,"name":"대왕","age":40,"skill":"밀정면역","pick":false}, {"unitId":2,"name":"창병","age":30,"skill":"기절","pick":false}, {"unitId":3,"name":"기병","age":32,"skill":"치명적속도","pick":false}, {"unitId":4,"name":"농민","age":24,"skill":"농민도스킬이쓰고싶어요","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"노비의무게","pick":true} ] } } |
| Pick | 밀정 픽 완료 | /pub/pick/{roomCode}/spy | { destination: /pub/pick/{roomCode}/spy body:{ team: “홍팀” or “청팀”, unitId: number } } | 한 팀만 보냈을 경우 { "type":"PICK_SPY_WAIT", "code":"232618/red", "data": “모든 픽이 완료될 때까지 기다려주세요.” } 두 팀 모두 보냈을 경우 홍팀 일 경우 (홍팀 하나만 명세, 블루팀도 동일) { "type":"GAME_START", "code":"232618/red", “data”: [ “EnemySpyInfo” : unitId, “allUserInfo”:[ {"team":1,"seatNumber":0,"userId":1,"nickname":”참여자1","profileImgUrl":null,"state":0,"ready":true}, {"team":1,"seatNumber":1,"userId":45,"nickname":”참여자2”,"profileImgUrl":null,"state":0,"ready":false}, {"team":1,"seatNumber":2,"userId":7,"nickname":”참여자3”,"profileImgUrl":null,"state":0,"ready":false}, {"team":2,"seatNumber":3,"userId":19,"nickname":””참여자4”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":4,"userId":25,"nickname":”참여자5”,"profileImgUrl":null,"state":0,"ready":true}, {"team":2,"seatNumber":5,"userId":34,"nickname":”참여자6”,"profileImgUrl":null,"state":0,"ready":false} ], “redTeamUnitList”:[ {"unitId":1,"name":"대왕","age":40,"skill":"","pick":true}, {"unitId":2,"name":"창병","age":30,"skill":"","pick":true}, {"unitId":3,"name":"기병","age":32,"skill":"","pick":true}, {"unitId":4,"name":"농민","age":24,"skill":"","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"","pick":true} ] “blueTeamUnitList”:[ {"unitId":1,"name":"대왕","age":40,"skill":"","pick":true}, {"unitId":2,"name":"창병","age":30,"skill":"","pick":true}, {"unitId":3,"name":"기병","age":32,"skill":"","pick":true}, {"unitId":4,"name":"농민","age":24,"skill":"","pick":true}, {"unitId":5,"name":"노비","age":33,"skill":"","pick":true} ] ] } |
| Pick | 캐릭터 선택 채팅창 | /pub/pick/{roomCode}/chat | { destination: /pub/pick/{roomCode}/done body:{ team: “홍팀” or “청팀”, nickname: string, message: string } } | { "type":"PICK_.CHAT", "code":"232618/red", “data”: [ team: “홍팀” or “청팀”, nickname: string, message: string ] } |
| Event | 이벤트 보내기 | /pub/event | { fromUserEmail : String, toUserEmail : String, eventCategory : String, eventAction : String, message : String } |     |
| Game | 게임 시작 | /pub/game/{roomCode}/start |     | { actionCategory : 0, missionRegion : {0, 0, 0, 0}, gameSpeed : int, gameTheme : String, redTeamUserList : [ { "unitId" : 1, "name" : "대왕", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 2, "name" : "기병", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 3, "name" : "창병", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 4, "name" : "농민", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 5, "name" : "노비", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” } ], blueTeamUserList: : [ { "unitId" : 1, "name" : "대왕", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 2, "name" : "기병", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 3, "name" : "창병", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 4, "name" : "농민", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” }, { "unitId" : 5, "name" : "노비", "skill" : “String”, “place” : “String”, "time” : “String", “contactor” : “String”, “stuff” : “String”, “scal” : “String” } ], redTeamUserList: [ {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”} ], blueTeamUserList: [ {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”}, {”email” : “String”, “nickname” : “String”, “profileImg” : “String”} ], mySpyUnitId : int, mySqyHint : String } |
| Game | 윷던지기 | /pub/game/{roomCode}/throw-yut | { yutRes : number, throwRes : {true,true,true,true}, } | { actionCategory : 1 yutRes : number, throwRes : {true,true,true,true}, } |
| Game | 말선택 | /pub/game/{roomCode}/select-unit | { unitIndex : number, } | { actionCategory : 2, unitIndex : number } |
| Game | 추리  | /pub/game/{roomCode}/reasoning | { team : number selectedUnit : number } | { actionCategory : 3, selectedUnit : number, isSpy : boolean } |
| Game | 추리권 사용여부 | /pub/game/{roomCode}/reason-ticket-use | { reasoningChoose : boolean } | { actionCategory : 4, reasoningChoose : boolean } |
| Game | 말도착 | /pub/game/{roomCode}/unit-gole | { team : int unitId: int[] } | { actionCategory : 5 team : int unitIds : int[] isSpy : boolean spyId : int } |
| Game | 미션성공 후 힌트 얻기 | /pub/game/{roomCode}/hint | { team : int, unitId : int, } | { actionCategory : 10 team : int, unitId : int, hint : String category : int, } |
| Game | 게임종료 | /pub/game/{roomCode}/finish | { team : int } | { actionCategory : 9 team : int } |
| Game | 팀 채팅 | /pub/game/{roomCode}/chat | { team : “홍팀” or “청팀”, nickname : string, message : string } | { actionCategory : 6, team : “홍팀” or “청팀”, nickname : string, message : string } |
| Game | 미니 게임 시작 | /pub/game/{roomCode}/mini-game-start | { email : 미션 걸린 사람의 email } | { actionCatergory : 7, email : 미션 걸린 사람의 email } |
| Game | 미니 게임 종료 | /pub/game/{roomCode}/mini-game-finish | { email : 미션 걸린 사람의 email result : 미션 결과 } | { actionCatergory : 8, email : 미션 걸린 사람의 email result : 미션 결과 } |
| Login | 로그인 알림 | /pub/login | { fromUserEmail : String, eventCategory : String, message : String } |     |
| Logout | 로그아웃알림 | /pub/logout | { fromUserEmail : String, eventCategory : String, message : String } |     |

#### WSS sub

| 도메인 | Description | EndPoint |
| --- | --- | --- |
| Room | 방 입장 (방 구독) | /sub/room/{roomCode} |
| Room | 팀 구독 | /sub/room/{roomCode}/red /sub/room/{roomCode}/blue |
| Event | 이벤트 구독 | /sub/event /user/sub/event |
| Game | 게임 구독 | /sub/game/{roomCode} |

## 🔧설계

### 📌아키텍처 설계

![system.drawio](/images/system.drawio.png)

### 📌DB 설계 (ERD)

![ERD](/images/ERD.png)

## 💻실제 화면

#### 📌진입 화면

##### 회원가입
![회원가입gif](/images/회원가입gif.gif)

##### 로그인
![로그인gif](/images/로그인gif.gif)

#### 📌홈 화면

##### 홈 화면 진입 후 로그아웃
![로그아웃gif](/images/로그아웃gif.gif)

##### 도움말
![도움말gif](/images/도움말gif.gif)

##### 게임 관련 설정창 조작
![설정창gif](/images/설정창gif.gif)

##### 친구 추가
![친구추가gif](/images/친구추가gif.gif)

##### 친구 채팅
![친구채팅2gif](/images/친구채팅2gif.gif)

#### 📌프로필 화면

##### 프로필 홈화면
![내정보gif](/images/내정보gif.gif)

##### 닉네임 변경
![닉네임변경gif](/images/닉네임변경gif.gif)

##### 비밀번호 변경
![비밀번호변경gif](/images/비밀번호변경gif.gif)

#### 📌게임 방 화면

##### 게임 방 리스트 및 대기 방 입장
![방입장gif](/images/방입장gif.gif)

#### 📌게임 진행

##### 게임 시작 시 캐릭터 픽 창 진입
![픽창입장gif](/images/픽창입장gif.gif)

##### 캐릭터 픽 이후 상대 팀 밀정 픽 창 진입
![픽과밀정픽gif](/images/픽과밀정픽gif.gif)

##### 게임 시작
![게임입장gif](/images/게임입장gif.gif)

##### 윷 던지기
![윷던지기유닛이동미션도착gif](/images/윷던지기유닛이동미션도착gif.gif)

##### 미니 게임 실행
![미션gif](/images/미션gif.gif)

##### 미니 게임 성공 후 밀정 힌트 얻기
![힌트얻기gif](/images/힌트얻기gif.gif)

##### 밀정 추리
![추리gif](/images/추리gif.gif)

##### 게임 승패
![gif-ezgif.com-resize](/images/승리gif.gif)


## 🌐 출처
"**Beautiful Korea(뷰티플 코리아)**" 사운드는 윤영현에 의해 제작되었으며, CC BY 라이선스에 따라 사용되었습니다.  
"**BLACK BOX - South Korea**" 사운드는 이지빈에 의해 제작되었으며, CC BY 라이선스에 따라 사용되었습니다.  
"**74. Blumenlied (Flower Song)_ 꽃노래_국악**" 사운드는 한국저작권위원회에 의해 제작되었으며, CC BY 라이선스에 따라 사용되었습니다.  
"**LSMBG0701_궁궐에서 맞이하는 아침**" 사운드는 한국저작권위원회에 의해 제작되었으며, CC BY 라이선스에 따라 사용되었습니다.  
"**Once upon a time**" 사운드는 eldlofi에 의해 의해 제작되었습니다.  
