const $cartUl = $(".shoppingcart");
const $campUl = $("#campBox");
$(function() {
  const $tabButtonItem = $('#tab-button li'),
      $tabSelect = $('#tab-select'),
      $tabContents = $('.tab-contents');
  let activeClass = 'is-active';

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
	
	loadAllHistory();
});




// 결제 내역 수정 버튼 클릭 시 동작하는 이벤트 리스너
const editBtns = document.querySelectorAll('.edit-btn');
editBtns.forEach((btn) => {
  btn.addEventListener('click', () => {
    // 클릭한 결제 내역의 정보를 가져와서 수정하는 로직을 구현
    console.log('결제 내역 수정 버튼이 클릭되었습니다.');
  });
});


async function loadAllHistory(){
	try{
		const result = await $.get("/history/lists");
		console.log(result);
		const itemFilter = result.filter((history, index) => {
			if(history.historyType == "ITEM" && history.name && history.state && history.price && history.regDate && history.amount){
				console.log("통과!")
				return true;
			}
			return false;
		})
		const itemHtml = itemFilter.map(({name, state, price, regDate, amount}) => {
				return `
				<li>
					<div class="date">${regDate}</div>
					<div class="product-name">${name}</div>
					<div class="price">${price * amount}</div>
					<div class="status">${state}</div>
					<div class="edit"><button class="edit-btn">수정</button></div>
				</li>
				`
		}).join("");
		console.log(itemHtml);
		$cartUl.html(itemHtml);
		const campHtml = result.map(({hno, historyType, historyNum, name, state, price, regDate, startdate, enddate, address, peopleNum}) => {
			if(history.historyType == "CAMP"){
				`
				<div class="campgroundsbox box4">
					<div class="campgrounds_title item">캠핑장</div>
					<div class="campgrounds_name item_2">${name}</div>
					<div class="campgrounds_date item_3">${startdate}~${enddate}</div>
					<div class="campgrounds_expense item_4">${price * peopleNum}</div>
				</div>
				`
			}
		}).join("");
		$campUl.html(campHtml);
	} catch(err) {
		console.log("에러 발생!", err);
	}
}