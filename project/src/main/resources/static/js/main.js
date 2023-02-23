"use strict";



//typeit 화면에 글씨써주는 효과
new TypeIt('.ni_text', {
  strings: ['Hello', 'World', 'TypeIt'], // 타이핑할 문자열 배열
  speed: 100, // 타이핑 속도
  loop: true, // 루프 옵션
  cursorChar:''
}).go(); 




//typeit 화면에 글씨써주는 효과
// document.addEventListener('DOMContentLoaded', ()=> {
//   new TypeIt('.ni_text')
//   .pause(1500)
//   .delete(11, {delay: 1000})
//   .type('이번엔 어디를 가볼까?')
//   .pause(1500)
//   .delete(12, {delay: 1000})
//   .type('어떤 캠핑용품이 필요하지?')
//   .pause(1500)
//   .delete(14, {delay: 1000})
//   .pause(1500)
//   .type('Campers와 함께')
  
//   .go() 
  
 
// })



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