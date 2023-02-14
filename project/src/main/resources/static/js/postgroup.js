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


/*input file타입의 이미지 파일만 올리게 하기 위해 사용*/
function chk_file_type(obj) {
    var file_kind = obj.value.lastIndexOf('.');
    var file_name = obj.value.substring(file_kind + 1, obj.length);
    var file_type = file_name.toLowerCase();



    var check_file_type = new Array();

    check_file_type = ['jpg', 'gif', 'png', 'jpeg', 'bmp'];



    if (check_file_type.indexOf(file_type) == -1) {
        alert('이미지 파일만 선택할 수 있습니다.');
        var parent_Obj = obj.parentNode
        var node = parent_Obj.replaceChild(obj.cloneNode(true), obj);
        return false;
    }
}


function toggleBtn1() {
    // 토글 할 버튼 선택 (.sec1) 
    var sec1 = document.querySelector('.sec1');
    console.log(sec1)
    // btn1 숨기기 (display: none)
    if (sec1.style.display !== 'none') {
        sec1.style.display = 'none';
    }
    // btn` 보이기 (display: block)
    else {
        sec1.style.display = 'block';
    }
}


function toggleBtn2() {
    var sec2 = document.querySelector('.sec2');
    console.log(sec2)
    if (sec2.style.display !== 'none') {
        sec2.style.display = 'none';
    }
    else {
        sec2.style.display = 'block';
    }
}

function toggleBtn3() {
    var sec3 = document.querySelector('.sec3');
    console.log(sec3)
    if (sec3.style.display !== 'none') {
        sec3.style.display = 'none';
    }
    else {
        sec3.style.display = 'block';
    }
}