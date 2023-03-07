
$(document).ready(function() {

	//// 별점 관리 /////
	var grade = $(".starrr").html();
	var campRegister = $(".modal");
	let makeLocation = "";
	let modifyLocation = "";
	let modifyType = [];
	var makeType = [];
	var campType = [];
	let makeCamp = "";
	let Camp = "";
	let imgpath = "";

	// 검색 타입
	let searchType = "";

	// 캠핑 타입
	var type = "";
	let listGroup = $(".test");
	let size = $(".test").attr("id");
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

	// 캠프 생성 모달
	function modalClose() {
		$("#popup").fadeOut(); //페이드아웃 효과
	}

	function modifyModalClose() {
		$("#modify-popup").fadeOut(); //페이드아웃 효과
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

	// 캠핑장 수정
	listGroup.on('click', '.modify', function(event) {
		var cno = $(this).attr('id');

		//팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
		$("#modify-popup").css('display', 'flex').hide().fadeIn();

		// 캠핑장 수정 하기전 데이터 받아오기.
		getCamp(cno);
	});

	//캠핑장 삭제
	listGroup.on('click', '.remove', function(event) {
		var cno = $(this).attr('id');

		if (confirm("정말로 삭제 하시겠습니까?")) {
			campRemove(cno);
		} else {
			alert("삭제를 취소하였습니다.");
		}
	});

	/* 캠프 등록 관련 메소드 시작*/
	// 모달 오픈 
	$(campRegister).on("click", function() {
		console.log("나눌림");
		//팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
		$("#popup").css('display', 'flex').hide().fadeIn();
	});


	// 캠프 생성 모달 닫기
	$("#close").click(function() {
		console.log("모달 닫기");
		modalClose(); //모달 닫기 함수 호출
	});

	//캠프 생성 
	$("#confirm").click(function() {
		makeCamp = makeType.join(",");
		funcCampRegister();
	});

	// 캠프 수정 모달 닫기
	$(".modify-close").click(function() {
		clearModify();
		modifyModalClose(); //모달 닫기 함수 호출
	});

	// 캠프 수정
	$(".modify-confirm").click(function() {
		cmapModify();
		modifyModalClose(); //모달 닫기 함수 호출
	});

	$(".makeLocation").on("click", "#type", function() {
		// 기존 꺼 연한 파란색으로 바꿔준다.
		$(".makeLocation #type").css("background-color", "#A2C4E1");

		// 눌른거 찐 파랑으로 바꿔버림.
		$(this).css("background-color", "#0062cc");

		// 값 넣어주기
		makeLocation = $(this).html();
	})

	$(".modifyLocation").on("click", "#type", function() {
		// 기존 꺼 연한 파란색으로 바꿔준다.
		$(".modifyLocation #type").css("background-color", "#A2C4E1");

		// 눌른거 찐 파랑으로 바꿔버림.
		$(this).css("background-color", "#0062cc");

		// 값 넣어주기
		modifyLocation = $(this).html();
	})


	$(".modifyType").on("click", "#type", function() {
		var type = $(this).html();
		var findNum = modifyType.indexOf(type);
		console.log(findNum);

		// 값이 없음
		if (findNum == -1) {
			// 눌른거 찐 파랑으로 바꿔버림.
			$(this).css("background-color", "#0062cc");
			modifyType.push(type);
		} else {
			console.log(makeType);
			modifyType = modifyType.filter(function(item) {
				return item != type;
			})
			// 기존 꺼 연한 파란색으로 바꿔준다.
			$(this).css("background-color", "#A2C4E1");
		}

		console.log(makeType);
	});

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
	// 캠핑 타입 확인
	$("#campType").on("click", "#type", function() {

		type = $(this).html();
		var findNum = campType.indexOf(type);
		console.log(findNum);

		// 값이 없음
		if (findNum == -1) {
			// 눌른거 찐 파랑으로 바꿔버림.
			$(this).css("background-color", "#0062cc");
			campType.push(type);
		} else {
			console.log(campType);
			campType = campType.filter(function(item) {
				return item != type;
			})
			// 기존 꺼 연한 파란색으로 바꿔준다.
			$(this).css("background-color", "#A2C4E1");
		}
		type = campType.join(",");
		console.log(type);

		// 누를 떄마다 호출

		loadJSON();
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

			// 값을 없앨때 동작하는 로직 색상 흐리게하고 기본값에서 제거한다.
			console.log("값이있어서 지울게");
			$(this).css('opacity', 0.3);
			locations.splice(dataHere, 1);
		}
		// 값조건 체크 완료
		loadJSON();
	});

	// 검색 
	$("#search").on("click", "#type", function() {
		// 기본값일경우
		searchType = "";
		searchType = $(this).html();

		type += "," + searchType;
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

	function clearModify() {

		makeType = [];
		modifyLocation = "";

		// 버튼 타입 요소 가져오기
		const type = $('.modifyType button');
		const locationType = $('.modifyLocation button');
		// 있던 타입 버튼들의 배열 
		type.each(function(index, value) {
			$(value).css("background-color", "#A2C4E1");

		});

		// 있떤 타입들의 배열
		locationType.each(function(index, value) {
			$(value).css("background-color", "#A2C4E1");
		});
	}

	async function getCamp(cno) {

		const result = await $.get("/camp/campData/" + cno);

		console.log(result);

		var address = result.address;
		var campintroduce = result.campintroduce;
		var camptype = result.camptype;
		var location = result.location;
		var name = result.name;
		var thumbnail = result.thumbnail;
		var star = result.star;
		var count = result.count;
		var heart = result.heart;
		var unit = result.unit;

		$(".modify-campName").val(name);
		$(".modify-campintorduce").val(campintroduce);
		$(".modify-address").val(address);
		$(".modify-thumbnail").val(thumbnail);
		$(".modify-campName").val(name);
		$(".modify-cno").val(cno);
		$(".modify-star").val(star);
		$(".modify-count").val(count);
		$(".modify-heart").val(heart);
		$(".modify-unit").val(unit);

		$(".modify-popup .makeType").val();

		// 버튼 타입 요소 가져오기
		const type = $('.modifyType button');


		// 있던 타입 버튼들의 배열 
		type.each(function(index, value) {
			if (camptype.includes($(value).html())) {
				$(value).css("background-color", "#0062cc");
				makeType.push(type);
			} else {
				$(value).css("background-color", "#A2C4E1");
			}
		});


		// 버튼 타입 요소 가져오기
		const locationType = $('.modifyLocation button');

		// 있떤 타입들의 배열
		locationType.each(function(index, value) {

			if (location.includes($(value).html())) {
				$(value).css("background-color", "#0062cc");
				modifyLocation = $(value).html();
			} else {
				$(value).css("background-color", "#A2C4E1");
			}
		});
	}

	//하트 추가
	listGroup.on('click', '.saveHeart', function() {
		var cno = $(this).attr('id');
		var hlno = $(this).attr('data-id');
		var heart = $(this);
		saveHeart(cno, heart, hlno);
	});

	//하트 삭제
	listGroup.on('click', '.removeHeart', function() {
		var cno = $(this).attr('id');
		var hlno = $(this).attr('data-id');
		var heart = $(this);

		removeHeart(cno, heart, hlno);
	});

	// 이미지 클래스 data-id 변경 메서드
	function changeHeartImg(flag, heart, hlno) {
		console.log(flag);
		console.log(hlno);
		heart.attr("src", flag ? "../img/full_heart.png" : "../img/empty_heart.png");

		if (flag) {
			heart.removeClass("saveHeart");
			heart.addClass("removeHeart");
			heart.attr("data-id", hlno.hlno);
		} else {
			heart.removeClass("removeHeart");
			heart.addClass("saveHeart");
			heart.attr("data-id", hlno);
		}

	};


	function cmapModify() {

		var type2 = modifyType.join(",");
		console.log(modifyType);
		console.log(type);

		$.ajax({
			url: "/camp/modify",
			contentType: "application/json",
			method: "PUT",
			data: JSON.stringify({

				cno: $(".modify-cno").val(),
				name: $(".modify-campName").val(),
				campintroduce: $(".modify-campintorduce").val(),
				address: $(".modify-address").val(),
				thumbnail: $(".modify-thumbnail").val(),
				camptype: type2,
				location: modifyLocation,
				star: $(".modify-star").val(),
				count: $(".modify-count").val(),
				heart: $(".modify-heart").val(),
				unit: $(".modify-unit").val(),
			})
		})
	}

	function campRemove(cno) {
		$.ajax({
			url: "/camp/remove/" + cno,
			method: "DELETE",
			success: function() {
				alert("캠핑장 을 삭제하였습니다.");
				loadJSON();
			}
		})
	}


	function loadJSON(x) {

		console.log("프로그램 시작");

		if (type == "") {
			type = "별점순"
		}
		console.log(page);

		page = x || 1; // x or 1(x = null, undefined, 0)

		var url = '/camp/list/' + type + '/' + locations + '/' + page + '/' + size;

		$.getJSON(url, function(arr) {
			listGroup.html("");
			let str = "";
			const [camp, member, avg] = arr;
			console.log(camp);

			$.each(camp.dtoList, function(index, camp) {


				str += "<div class='campgroundsbox box'>";
				str += "<input type = 'hidden' name='cno' th:value ='" + camp.cno + "''>";
				str += "<div class='cg_imgbox box5' onclick='location.href=\"/camp/campgroundsdetail?cno=" + camp.cno + "\"''>";

				console.log(camp.thumbnail);
			
				try {
					if (camp.thumbnail != null && camp.thumbnail.split(':')[0] === "https") {
						console.log(camp);
						console.log("어");
						str += "<img class='cg_img item' src=" + camp.thumbnail + ">";
					} else {
						console.log("나야");
						str += "<img class='cg_img item' src=/display?fileName=" + camp.thumbnail.split(',')[camp.thumbnail.split(',').length-1] + "&folderType=camp>";
					}
				} catch {
					console.log("나도탐?");
					str += "<img class='cg_img item' src=/display?fileName=" + "http://www.hanawaterjet.com/app/dubu_board/docs/imgs/n/lg_n15287811543531_%EC%9D%B4%EB%AF%B8%EC%A7%80%EB%A5%BC%EC%A4%80%EB%B9%84%EC%A4%91%EC%9E%85%EB%8B%88%EB%8B%A4.jpg" + "&folderType=camp>";
				}


				str += "</div>";
				str += "<div class='cg_rightbox box4'>"
				str += "<div class='title item'>" + camp.name + "</div>";
				str += "<div class='cg_starbox item'>";

				str += "<div class='star item'>" + getStar(avg[index]) + "  (" + (avg[index]) + ")</div>";

				str += "</div>";
				str += "<div class='cg_explainbox box5'>";
				str += "<div class='explain item'>" + camp.address + "</div>";
				str += "</div>";

				var heartData = "";
				try {
					heartData = JSON.parse(heartCheck(camp.cno));
				} catch {

				}

				// 하트 한 항목이라면 ?
				if (Number(heartData.hlno) > 0) {
					// 눌렷을때 행동을 class Name 으로줌
					console.log(heartData.hlno);
					str += "<img class='removeHeart' id=" + camp.cno + " data-id= " + heartData.hlno + " width=30px height=30px src='../img/full_heart.png'>";
				} else {
					str += "<img class='saveHeart' id=" + camp.cno + " data-id= 'empty' width=30px height=30px src='../img/empty_heart.png'>";
				}


				// 버튼 HTML 생성 시 data-cno 속성 추가
				if (member.mno === camp.mno) {
					str += '<div class="modify-box">';
					str += '  <button class="modify" id="' + camp.cno + '">수정</button>';
					str += '  <button class="remove" id="' + camp.cno + '">삭제</button>';
					str += '</div>';
				}

				str += "</div>";
				str += "</div>";


			});
			setStar();

			const [result] = arr;
			const { page: pageNum, prev: isPrev, next: isNext, end: endPage } = result;

			console.log(pageNum);
			console.log(isPrev);
			console.log(isNext);
			console.log(endPage);

			let pagination = `<ul class="pagination h-100 justfy-content-center align-items-conter camppage box5">`;

			if (isPrev) {
				pagination += `<li class="page-item">
	    				       <a class="page-link bt_prev item_2" onclick="pageLoad()" tabindex="-1">이전</a>
	    				   </li>`;
			}

			for (const value of camp.pageList) {
				pagination += `<li class="page-item num ${value == page ? 'active' : ''}">
	    				       <button class="rqwrqw" id=${value}>${value}</button>
	    				       <input type="hidden" name="pageValue" value="${value}">
	    				   </li>`;
			}

			if (isNext) {
				pagination += `<li class="page-item num">
	    				<a class="page-link" onclick="pageLoad(${endPage + 1})">다음</a><li></ul>`;
			}


			pagination += "</ul>"
			page = $(".pagingEl").find("[name='pageValue']").val();
			$paging.html(pagination);

			listGroup.html(str);

		});
	};

	function saveHeart(cno, heart) {
		console.log(heart);
		$.ajax({
			url: "/heart/save",
			contentType: "application/json",
			method: "post",
			data: JSON.stringify({
				productNum: cno,
				productType: "CAMP",
				// 동기처리
				async: false
			}), success: function(hlno) {
				console.log("탔음");
				changeHeartImg(true, heart, hlno);
			}
		})
	}

	function removeHeart(cno, heart, hlno) {
		console.log(hlno);
		console.log(cno);
		$.ajax({
			url: "/heart/remove",
			contentType: "application/json",
			method: "DELETE",
			data: JSON.stringify({
				hlno: Number(hlno),
				productNum: cno,
				async: false,
				productType: "CAMP",
			}), success: changeHeartImg(false, heart, hlno),
			fail: console.log("실패")
		})
	}

	// 하트 확인 메서드
	function heartCheck(no) {
		return $.ajax({
			url: "/heart/getOne",
			contentType: "application/json",
			// 동기처리
			async: false,
			method: "POST",
			data: JSON.stringify({
				productNum: no,
				productType: "CAMP"
			}), dataType: "json"
		}).responseText
	}

	$("#thumbnail").on("change", function() {
		var formData = new FormData();
		var files = $("#thumbnail")[0].files;
		console.log(files);

		for (var i = 0; i < files.length; i++) {
			console.log(files[i].name);
			formData.append("uploadFiles", files[i]);
		}
		formData.append("webPath", "camp");
		var i = 0;
		$.ajax({
			url: "/uploadAjax",
			processData: false, // 데이터 처리하지 않음
			contentType: false, // 컨텐츠 타입을 false로 설정하여 FormData가 알아서 설정하도록 함
			method: "POST",
			data: formData,
			dataType: "json",
			success: function(result) {
				result.forEach(data => {
					if (i == 0) {
						imgpath = data.uuid + "_" + data.fileName;
					} else {
						imgpath += "," + data.uuid + "_" + data.fileName;
					}
					i++;
				})

			},
			error: function(xhr, text, errorThrown) {
				console.log(text);
			}
		})
	});


	function funcCampRegister() {
		console.log(imgpath);
		$.ajax({
			url: "/camp/register",
			contentType: "application/json",
			method: "POST",
			data: JSON.stringify({
				name: $(".campName").val(),
				address: $(".address").val(),
				campintroduce: $(".campintorduce").val(),
				camptype: makeCamp,
				location: makeLocation,
				thumbnail: imgpath
			}), success: function(result) {
				alert("캠핑장이 등록 되었습니다");
				modalClose();
			}, fail: function(result) {
				alert("캠핑장이 등록 되지 않았습니다");
				modalClose();
			}
		})
	}

	// 페이징 처리를 위한 부분 따로 모아둠 //////////////////////////////
	const $paging = $(".camppagingEl");

	// 페이징 / 검색을 위해 url 따오는 것들(url param 추출)   
	let urlStr = window.location.href;
	console.log(urlStr);

	let url = new URL(urlStr);
	console.log(urlStr);

	let urlParams = url.searchParams;
	let page = urlParams.get('page');


	// 리스트 첫 페이지에서 page 파라미터가 null이라서 페이지를 읽어오지 못함.
	// if문으로 null 일 경우 1로 바꿔줌.
	if (page == null) {
		page = 1;
	}

	$paging.on("click", ".rqwrqw", function() {
		loadJSON($(this).attr("id"));
	});

	loadJSON();
});