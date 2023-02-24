$(document).ready(function() {

	//// 별점 관리 /////
	var grade = $(".starrr").html();
	var campRegister = $(".modal");
	let makeLocation = "";
	var makeType = [];
	var campType = "";

	// 검색 타입
	let searchType = "";

	// 캠핑 타입
	var type = "";
	let listGroup = $(".test");

	console.log("grade : " + grade);
	$(".starrr").html("");

	$('.starrr').starrr({
		rating: grade,
		change: function(e, value) {
			if (value) {
				console.log(value)
				grade = value
			}
		}
	});

	function modalClose() {
		$("#popup").fadeOut(); //페이드아웃 효과
	}

	function getStar(grade) {
		let star = '';
		for (let i = 1; i <= 5; i++) {
			if (i <= grade) star += '★';
			else star += '☆';
		}
		return star;
	}
	function setStar() {
		let grade = $("#sync_star").html();
		console.log("grade = " + grade);
		$("#item_star").html("<div id='sync_star'></div>");
		$("#sync_star").starrr({
			readOnly: true,
			rating: grade
		});
		$("#sync_star").append(Math.round(grade * 10) / 10);
	}

	/* 캠프 등록 관련 메소드 시작*/
	// 모달 오픈 
	$(campRegister).on("click", function() {
		console.log("나눌림");
		//팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
		$("#popup").css('display', 'flex').hide().fadeIn();
	});


	$("#close").click(function() {
		console.log("모달 닫기");
		modalClose(); //모달 닫기 함수 호출
	});

	$("#confirm").click(function() {
		campType = makeType.join(",");

		alert($(".campName").val());
		alert($(".address").val());
		alert($(".campintorduce").val());
		alert(makeLocation);


		funcCampRegister();

	});

	$(".makeLocation").on("click", "#type", function() {
		// 기존 꺼 연한 파란색으로 바꿔준다.
		$(".makeLocation #type").css("background-color", "#A2C4E1");

		// 눌른거 찐 파랑으로 바꿔버림.
		$(this).css("background-color", "#0062cc");

		// 값 넣어주기
		makeLocation = $(this).html();


	})


	$(".makeType").on("click", "#type", function() {
		var type = $(this).html();
		var findNum = makeType.indexOf(type);
		console.log(findNum);

		// 값이 없음
		if (findNum == -1) {
			// 눌른거 찐 파랑으로 바꿔버림.
			$(this).css("background-color", "#0062cc");
			makeType.push(type);
		} else {
			console.log(makeType);
			makeType = makeType.filter(function(item) {
				return item != type;
			})
			// 기존 꺼 연한 파란색으로 바꿔준다.
			$(this).css("background-color", "#A2C4E1");
		}

		console.log(makeType);


	});


	/* 캠프 등록 관련 메소드 종료 */

	// 지역 타입 한국말 써봄
	var locations = [];

	locations[0] = "서울시";
	locations[1] = "부산시";
	locations[2] = "대구시";
	locations[3] = "인천시";
	locations[4] = "광주시";
	locations[5] = "대전시";
	locations[6] = "울산시";
	locations[7] = "세종시";
	locations[8] = "경기도";
	locations[9] = "강원도";
	locations[10] = "충청북도";
	locations[11] = "충청남도";
	locations[12] = "전라북도";
	locations[13] = "전라남도";
	locations[14] = "경상북도";
	locations[15] = "경상남도";
	locations[16] = "제주도";

	// 캠핑 타입 확인
	$("#campType").on("click", "#type", function() {

		// 비어있을떄는 온전히 값을 넣어줌
		if (type == "") {

			// type 에 값넣기
			type = $(this).html();

			// 비어있지 않을시에는
		} else {

			// this html 이 없을 경우
			if (type.indexOf($(this).html()) == -1) {

				// , 붙여서 넣어줌
				type += "," + $(this).html();
				console.log(type);
			}
		}
		console.log(type);
	});


	// 캠핑 위치 확인
	$("#map").on("click", ".OUTLINE", function() {
		var clicklocation = $(this).prop("id");
		var dataHere = locations.indexOf(clicklocation);
		// 값이 없는지 확인
		if (dataHere == -1) {
			$(this).css('opacity', 1);
			console.log("값이없어서 추가할게");
			locations.push(clicklocation);
		} else { // 값이 있는지 확인

			// 값을 없앨때 동작하는 로직 1.색상 흐리게하고 기본값에서 제거한다.
			console.log("값이있어서 지울게");
			$(this).css('opacity', 0.3);
			locations.splice(dataHere, 1);
		}
		// 값조건 체크 완료
		console.log(locations.length);
	});

	$("#search").on("click", "#type", function() {
		// 기본값일경우
		searchType = "";
		searchType = $(this).html();

		type += "," + searchType;
		console.log(type);

		// 실행
		loadJSON();

		// 실행이후 데이터 조회수 로 찾기나 하트 로 찾기 없애자
		var buffer = [];
		buffer = type.split(",");

		let bdeletestar = buffer.indexOf("별점순");
		let bdeletecount = buffer.indexOf("조회순");

		if (bdeletestar != -1) {
			buffer.splice(bdeletestar, 1);
		}

		if (bdeletecount != -1) {
			buffer.splice(bdeletecount, 1);
		}

		type = buffer.join(',');

	});


	function loadJSON() {
		console.log("loadjson");
		$.getJSON('/camp/list/' + type + '/' + locations, function(arr) {

			let str = "";
			$.each(arr, function(index, camp) {

				str += "<div class='campgroundsbox box'  onclick='location.href=\"/camp/campgroundsdetail?cno=" + camp.cno + "\"''>";
				str += "<input type = 'hidden' name='cno' th:value ='" + camp.cno + "''>";
				str += "<div class='cg_imgbox box5'>";
				str += "<img class='cg_img item' src=" + camp.thumbnail + ">";
				str += "</div>";
				str += "<div class='cg_rightbox box4'>"
				str += "<div class='title item'>" + camp.name + "</div>";
				str += "<div class='cg_starbox item'>";
				str += "<div class='star item'>" + getStar(camp.star) + "  (" + (camp.count) + ")</div>";
				str += "</div>";
				str += "<div class='cg_explainbox box5'>";
				str += "<div class='explain item'>" + camp.address + "</div>";
				str += "</div>";
				str += "</div>";
				str += "</div>";
				listGroup.html(str);
			});
		});
	};
	function funcCampRegister() {
		$.ajax({
			url: "/camp/register",
			contentType: "application/json",
			method: "POST",
			data: JSON.stringify({
				name: $(".campName").val(),
				address: $(".address").val(),
				campintroduce: $(".campintorduce").val(),
				camptype: campType,
				location: makeLocation,
				thumbnail: "test.jpg"
			}), success: function(result) {
				alert(result);
			}, fail: function(result) {
				alert(result);
			}
		})
	}
});