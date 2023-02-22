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


var swiper = new Swiper(".mySwiper", {
  speed: 600,
  parallax: true,
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});



//typeit 화면에 글씨써주는 효과
document.addEventListener('DOMContentLoaded', ()=> {
  new TypeIt('.ni_text')
  .pause(1500)
  .delete(11, {delay: 1000})
  .type('이번엔 어디를 가볼까?')
  .pause(1500)
  .delete(12, {delay: 1000})
  .type('어떤 캠핑용품이 필요하지?')
  .pause(1500)
  .delete(14, {delay: 1000})
  .pause(1500)
  .type('Campers와 함께')
  
  .go() 
  
 
})



// scrollOut 스크롤 내릴때 효과
ScrollOut({
  
  onShown: (element) => {
    new TypeIt(".scroll", {
      startDelay: 500,
      cursor: false,
    })
    .pause(2000)
    .go();
  },
});


ScrollOut({
  
  onShown: (element) => {
    new TypeIt(element.querySelector('.scroll'), {
      startDelay: 500,
      cursor: false,
    })
    .pause(2000)
    .go();
  },
});



//스크롤 내릴 때 속도를 다르게 줄 수 있다.

var rellax = new Rellax('.rellax');