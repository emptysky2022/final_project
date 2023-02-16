"use strict";


/*화면크기가 태블릿 이하일 때, 네비게이션에서 햄버거 누르면 메뉴 보이게 하는 기능 */
const hamburger = document.querySelector("header .header_hamburger");
const menu = document.querySelector("header .box2_2");
const mypage = document.querySelector("header .box2_3");

console.log(hamburger);

hamburger.addEventListener('click', () => {
    menu.classList.toggle('active');
    mypage.classList.toggle('active');
});
