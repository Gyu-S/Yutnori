export function initializeSnowAnimation(canvasId, backgroundImageSrc) {
  // 맵을 불러온다.
  const c = document.getElementById(canvasId);
  const d = c.getContext("2d");
  const w = (c.width = window.innerWidth);
  const h = (c.height = window.innerHeight);

  let mouseX = 0;
  let mouseY = 0;

  c.addEventListener("mousemove", function (e) {
    mouseX = e.clientX - c.getBoundingClientRect().left;
    mouseY = e.clientY - c.getBoundingClientRect().top;
  });

  // 배경이미지 가져오기.
  // const backgroundImage = new Image();
  // backgroundImage.src = backgroundImageSrc;
  // backgroundImage.onload = function () {
  //   Snowy();
  //   d.drawImage(backgroundImage, 0, 0, w, h);
  // };

  // 눈 생성
  function Snowy() {
    var arr = []; // 눈 배열
    var num = 300; // 눈송이 개수
    var sp = 0.4; // 내리는 속도
    var sc = 0.5; // 크기
    var t = 10;
    var mv = 20;
    var min = 0.3;

    // 300개의 눈의 위치와 크기를 arr 배열에 저장.
    for (var i = 0; i < num; ++i) {
      var snow = new Flake();
      snow.y = Math.random() * (h + 50);
      snow.x = Math.random() * w;
      snow.t = Math.random() * (Math.PI * 2);
      snow.sz = (100 / (10 + Math.random() * 100)) * sc;
      snow.sp = Math.pow(snow.sz * 0.8, 2) * 0.15 * sp;
      snow.sp = snow.sp < min ? min : snow.sp;
      arr.push(snow);
    }

    go();

    function go() {
      window.requestAnimationFrame(go);
      d.clearRect(0, 0, w, h);

      for (var i = 0; i < arr.length; ++i) {
        var f = arr[i];
        f.t += 0.05;
        f.t = f.t >= Math.PI * 2 ? 0 : f.t;

        // 이동 방향을 마우스 위치로 조절
        var angleToMouse = Math.atan2(mouseY - f.y, mouseX - f.x);
        f.x += Math.cos(angleToMouse) * (f.sz * 0.00005);
        f.y += f.sp; // 눈송이는 아래 방향으로 계속 이동

        if (f.y > h + 50) f.y = -10 - Math.random() * mv;
        if (f.x > w + mv) f.x = -mv;
        if (f.x < -mv) f.x = w + mv;
        f.draw();
      }
    }

    // 각각의 눈이 가지고 있는 정보
    function Flake() {
      this.draw = function () {
        this.g = d.createRadialGradient(
          this.x,
          this.y,
          0,
          this.x,
          this.y,
          this.sz + 8
        );
        this.g.addColorStop(0, "hsla(255,255%,255%,0.7)");
        this.g.addColorStop(1, "hsla(255,255%,255%,0.7)");
        d.moveTo(this.x, this.y);
        d.fillStyle = this.g;
        d.beginPath();
        d.arc(this.x, this.y, this.sz, 0, Math.PI * 2, true);
        d.fill();
      };
    }
  }
  Snowy();
}