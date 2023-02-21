"use strict";


/*화면크기가 태블릿 이하일 때, 네비게이션에서 햄버거 누르면 메뉴 보이게 하는 기능 */
const hamburger = document.querySelector("header .header_hamburger");
const menu = document.querySelector("header .header_menu");
const mypage = document.querySelector("header .header_mypage");

console.log(hamburger);

hamburger.addEventListener('click', () => {
    menu.classList.toggle('active');
    mypage.classList.toggle('active');
});


/*swiper*/

  const progressCircle = document.querySelector(".autoplay-progress svg");
    const progressContent = document.querySelector(".autoplay-progress span");
    var swiper = new Swiper(".mySwiper", {
      spaceBetween: 30,
      centeredSlides: true,
      autoplay: {
        delay: 2500,
        disableOnInteraction: false
      },
      pagination: {
        el: ".swiper-pagination",
        clickable: true
      },
      navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev"
      },
      on: {
        autoplayTimeLeft(s, time, progress) {
          progressCircle.style.setProperty("--progress", 1 - progress);
          progressContent.textContent = `${Math.ceil(time / 1000)}s`;
        }
      }
    });