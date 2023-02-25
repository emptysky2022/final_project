"use strict";



//typeit 화면에 글씨써주는 효과
 new TypeIt('.ni_text', {
    speed: 100,
    loop: true,
    cursorChar: '',
  })
  .pause(1500)
  .delete(11, {delay: 1000})
  .type('이번엔 어디를 갈까?')
  .pause(1500)
  .delete(11, {delay: 1000})
  .type('어떤 캠핑용품이 필요하지?')
  .pause(1500)
  .delete(14, {delay: 1000})
  .pause(1500)
  .type('이번에도 역시!')
  .pause(500)
  .go() ;
  

 






//스크롤 내릴 때 속도를 다르게 줄 수 있다.

var rellax = new Rellax('.rellax');




