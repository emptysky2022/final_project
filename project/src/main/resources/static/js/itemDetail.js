$(function(){
	let ino = new URLSearchParams(window.location.search).get("ino");
	var grade=$(".starrr").html();
	console.log("grade : " + grade);
	$(".starrr").html("");
	$('.starrr').starrr({
		rating: grade,
		change: function(e, value){
			if(value){
				console.log(value)
				grade=value
			}
		}
	});
	$("#confirm").click(function(){
        modalClose(); //모달 닫기 함수 호출
        //컨펌 이벤트 처리
		$.ajax({
			url: "/review/register",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				content : $("#content").val(),
				picture : $("#picture").val(),
				ino : ino,
				star : $("#select_star").data('starrr').options.rating
			}),
			success: function(ino){
				loadJsonData(ino);
			}
		})       
        
    });
    $("#modal-open").click(function(){        
        $("#popup").css('display','flex').hide().fadeIn();
        //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
    });
    $("#close").click(function(){
        modalClose(); //모달 닫기 함수 호출
    });
    function modalClose(){
        $("#popup").fadeOut(); //페이드아웃 효과
    }
    loadJsonData(ino);
})

function loadJsonData(ino){
	let reviewGroup = $("#reviewGroup");
	$.getJSON("/review/detail/" + ino, function(result){
		let str = "";
		let starAvg = 0;
		$.each(result, function(index, review){
			console.log(review);
			  str += '<div class="rv_l box5 ">';
			  str += '  <div class="box_l box6">';
			  str += '    <div class="imgbox box7">';
			  str += '      <a class="imglink box8" href="">';
			  str += '        <img class="rv_img item" src="' + review.capture + '"></a></div>';
			  str += '    <div class="rv_star box7_2" id="star">' + getStar(review.star) + '</div></div>';
			  str += '  <div class="box_r box6">';
			  str += '    <div class="comment box7">';
			  str += '      <div class="writer box8">';
			  str += '        <h2 class="write item">' + review.reviewer + '</h2>';
			  str += '        <a class="modify item" sec:authorize="hasRole(\'MEMBER\')">수정</a>';
			  str += '        <h1 ' + @{sec:authorize="hasRole(MEMBER)"} + '>asdfsad</h1>';
			  str += '        <a class="remove item" sec:authorize="isAuthenticated()">삭제</a></div>';
			  str += '      <p class="content item_2">' + review.content + '</p></div>';
			  str += '    <div class="rv_like box7"><i id="review_heart" class="fa-sharp fa-solid fa-thumbs-up fa-1x item" onclick="clickReviewHeart(' + review.irno + ')"> ' + review.heart + '</i></div></div></div><hr>';
			  starAvg += review.star;
		})
		starAvg = starAvg/result.length;
		reviewGroup.html(str);
		$("#sync_star").html(starAvg);
		setStar();
		
		console.log("끝!")
	})
}
function getStar(grade){
	let star = '';
	for(let i = 1; i <= 5; i++){
		if(i <= grade) star += '★';
		else star += '☆';
	}
	return star;
}
function setStar(){
	let grade = $("#sync_star").html();
	console.log("grade = " + grade);
	$("#item_star").html("<div id='sync_star'></div>");
	$("#sync_star").starrr({
		readOnly: true,
		rating: grade
	});
	$("#sync_star").append(Math.round(grade * 10) / 10);
}

function clickItemHeart(ino){
	$.ajax({
		url: "/item/heart/" + ino,
		contentType: "text/plain",
		success: function(result){
			console.log("result : " + result);
			$("#item_heart").html(result);
		},
		error: function(err){
			console.log("로그인 후 이용하실수 있습니다.");
			location.reload();
		}
	})
}
function clickReviewHeart(irno){
	$.ajax({
		url: "/review/heart/" + irno,
		contentType: "text/plain",
		success: function(result){
			console.log("result : " + result);
			$("#review_heart").html(" " + result);
		},
		error: function(err){
			console.log("로그인 후 이용하실수 있습니다.");
			location.reload();
		}
	})
}

function modifyCheck(){
	
}

