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


// 예약 내역 확인
let $reservationForm = $(".sec4 ,box3");
function loadResrvationDetails() {

	$.getJSON("/camp/reservation/member", function(result) {

		var camp;
		var reservaion;
		var str = "";
		// 사용자가 캠프 예약한것만큼 들어옴
		for (var i = 0; i < result.length; i++) {
			// 그사이에서 두개로 나뉨 0 이 예약 1이 캠핑장 정보
			reservaion = result[i][0];
			camp = result[i][1];

			str += `<div class="campgroundsbox box4">
						<div class="campgrounds_title item"> <img src=${camp.thumbnail}> </div>
						<div class="campgrounds_name item_2">${camp.name}</div>
						<div class="campgrounds_name item_3">${camp.address}</div>
						<div class="campgrounds_date item_4">${reservaion.startdate} ~ ${reservaion.enddate}</div>
						<div class="campgrounds_expense item_4">
							<button class="reserveCancel" id = ${reservaion.ccno}> 예약취소 </button>
						</div>
					</div>`
		}

		$reservationForm.html(str);

	})
}

// 예약취소 버튼 눌림
$reservationForm.on("click", ".reserveCancel", function() {
	var ccno = $(this).attr("id");
	$.get('/camp/calendar/remove/' + ccno, function(result) {

		alert("예약 취소에 성공 하였습니다.");
		
		// 페이지 리로드
		loadResrvationDetails();
	}), fail(function(xhr, status, error) {
		alert("예약 취소에 실패 하였습니다.");
	})
})


// 프로그램 시작시 예약 내역 확인
loadResrvationDetails();

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
					<div class="date">${formatTime(regDate)}</div>
					<div class="product-name">${name}</div>
					<div class="price">${price * amount}</div>
					<div class="status">${state}</div>
					<div class="edit"><button class="edit-btn">수정</button></div>
				</li>
				`
		}).join("");
		console.log(itemHtml);
		$(".shoppingcart").html(itemHtml);
	} catch(err) {
		console.log("에러 발생!", err);
	}
	myHeart("ITEM");
}

function formatTime(str) {
    var date = new Date(str);
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
}


function myHeart(productType){
	$.ajax({
		url: "/heart/get/" + productType,
		method: "GET",
		success: function(result){
			console.log(result)
		}
	})
}