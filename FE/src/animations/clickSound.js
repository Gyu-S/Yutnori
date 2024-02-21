export function initializeClickSoundEffect() {
  const clickSound = document.querySelector("#clickSound");

  // 혹시 모를 clickSound를 찾지 못했을 때를 대비.
  if (!clickSound) {
    console.error("Click sound element not found");
    return;
  }

  document.addEventListener("click", function(e) {
    // 버튼, 라우터링크, 입력칸에서 클릭 이벤트가 발생하면 사운드.
    if (e.target.matches("a") || e.target.matches("button") || e.target.matches("input")) {
      // 오디오 재생 시도
      clickSound.play().catch(error => console.error("Error playing sound:", error));
    }
  });
}
