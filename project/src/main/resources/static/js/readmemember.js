
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