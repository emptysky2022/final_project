
// 화면 줄이면 탭메뉴 변형
$(document).ready(function() {
  var $tabButtonItem = $('#tab-button li'),
      $tabSelect = $('#tab-select'),
      $tabContents = $('.tab-contents'),
      activeClass = 'is-active';

$tabButtonItem.first().addClass(activeClass);
$tabContents.not(':first').hide();

// button
$tabButtonItem.find('a').on('click', function(e) {
  var target = $(this).attr('href');

  $tabButtonItem.removeClass(activeClass);
  $(this).parent().addClass(activeClass);
  $tabSelect.val(target);
  $tabContents.hide();
  $(target).show();
  e.preventDefault();
});

// select
$tabSelect.on('change', function() {
  var target = $(this).val(),
      targetSelectNum = $(this).prop('selectedIndex');

  $tabButtonItem.removeClass(activeClass);
  $tabButtonItem.eq(targetSelectNum).addClass(activeClass);
  $tabContents.hide();
  $(target).show();
});
});




// 결제 내역 수정 버튼 클릭 시 동작하는 이벤트 리스너
const editBtns = document.querySelectorAll('.edit-btn');
editBtns.forEach((btn) => {
  btn.addEventListener('click', () => {
    // 클릭한 결제 내역의 정보를 가져와서 수정하는 로직을 구현
    console.log('결제 내역 수정 버튼이 클릭되었습니다.');
  });
});
