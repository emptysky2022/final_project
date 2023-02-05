"use strict";

const hamburger = document.querySelector('.nav__hamburger');
const menu = document.querySelector('.nav_menu');
const mypage = document.querySelector('.nav_mypage');

hamburger.addEventListener('click', () => {
    menu.classList.toggle('active');
    mypage.classList.toggle('active');
});


