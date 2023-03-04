let image = '';
let ino = new URLSearchParams(window.location.search).get("ino");
$(function(){
	var grade=$(".starrr").html();
	console.log("grade : " + grade);
	$(".starrr").html("");
	$('.starrr').starrr({
		rating: grade,
		change: function(e, value){
			if(value){
				grade=value
			}
		}
	});
	$("#capture").on("change", function(){
		image = [];
		console.log(image);
		console.log("capture change event");
		// 이미지 업로드 클릭시 파일 받아서 uploadAjax controller로 이동
		let formData = new FormData();
		const inputFile = $(this);
		const files = inputFile[0].files;
		let appended = false;
		
		$.each(files, function(index, file){
			if(!checkExtension(file.name, file.size)){
				return false;
			}
			console.log(file);
			formData.append("uploadFiles", file);
			appended = true;
		})
		if(!appended) {return};
		formData.append("webPath", "itemReview");
		for(let value of formData.values()){
			console.log(value)
		}
		console.log(formData);
		console.log("Ajax 바깥 스코프");
		console.log(image);
		$.ajax({
			url: '/uploadAjax',
			processData: false,
			contentType: false,
			data: formData,
			type: "POST",
			dataType: "json",
			success: function(result){
				console.log(result);
				//결과값중 uuid, filePath등등 있는데 imageURL만 뽑아서 리스트에 넣기
				result.map(({uuid, fileName}) => image += uuid+'_'+fileName+',');
				image.substring(-1, 0);
				console.log(image);
			},
			error: function(xhr, text, errorThrown){
				console.log(text);
			}
		});
	});
	$("#confirm").click(function(){
        modalClose(); //모달 닫기 함수 호출
        //컨펌 이벤트 처리
		//등록할 때 data를 ItemReviewDTO, List<String>으로 받아야 함
        let data = {
			content : $("#content").val(),
			ino : ino,
			star : $("#select_star").data('starrr').options.rating,
			capture: image
		};
		console.log(data);
		$.ajax({
			url: "/review/register",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(ino){
				$("#select_star").starrr('setRating', 0);
				$("#content").val("");
				$("#capture").val("");
				loadJsonData(ino);
			}
		})       
        
    });
	
    $("#item-modal-open").click(function(){
		$("#modify").hide();
		$("#confirm").show();       
        $("#popup").css('display','flex').hide().fadeIn();
        //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
    });
    $(".close").click(function(){
        modalClose(); //모달 닫기 함수 호출
    });
    loadJsonData(ino);
})
function modalClose(){
    $("#popup").fadeOut(); //페이드아웃 효과    
    console.log($("#popup").html())
    
}

function checkExtension(fileName, fileSize){
	console.log("extension function in");
	const regex = new RegExp("(.*?)\.(exe|sh|zip|alz|tiff)$");
	const maxSize = 10485760;
	if(fileSize >= maxSize){
		alert("파일 사이즈 초과");
		return false;
	}
	if(regex.test(fileName)){
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
}

//리뷰 목록 불러오기
function loadJsonData(ino){
	changeHeart(ino);	
	let reviewGroup = $("#reviewGroup");
	$.getJSON("/review/detail/" + ino, function(result){
		const [reviews, member] = result;
		console.log(member.nickname);
		let str = "";
		let starAvg = 0;
		$.each(reviews, function(index, review){
			  str += '<div class="rv_l box5 ">';
			  str += '  <div class="box_l box6">';
			  str += '    <div class="imgbox box7">';
			  str += '      <a class="imglink box8" href="">';
			  for(let image of review.capture.split(',')){
			  	str += '        <img class="rv_img item" src="/display?fileName=' + image + '&folderType=itemReview"></a></div>';
			  }
			  str += '    <div class="rv_star box7_2" id="star">' + getStar(review.star) + '</div></div>';
			  str += '  <div class="box_r box6">';
			  str += '    <div class="comment box7">';
			  str += '        <h2 class="write item">' + review.reviewer + '</h2>';
			  if(review.reviewer == member.nickname){
				  str += '    <div class="writer box8">';
				  str += '      <a class="modify item" onclick="modify(' + review.irno + ')">수정</a>';
				  str += '      <a class="remove item" onclick="remove(' + review.irno + ')">삭제</a></div>';
			  }
			  str += '      <p class="content item_2">' + review.content + '</p></div>';
			  str += '    <div class="rv_like box7"><i id="review_heart" class="fa-sharp fa-solid fa-thumbs-up fa-1x item" onclick="clickReviewHeart(' + review.irno + ')"> ' + review.heart + '</i></div></div></div><hr>';
			  starAvg += review.star;
		})
		console.log("starAvg : " + starAvg);
		starAvg = starAvg/reviews.length;
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
	const $heartdata = $("#item_heart").find(".heartImg").data("id");
	if($heartdata){
		console.log($heartdata)
	$.ajax({
		url: "/heart/remove",
		contentType: "application/json",
		method: "DELETE",
		data: JSON.stringify({
			hlno: Number($heartdata),
			productType: "ITEM",
			productNum: ino
		}),
		success: function(result){
			console.log("안녕 친구들")
			changeHeart(ino);
		},
		error: function(err){
			console.log("로그인 후 이용해주세요");
		}
	})
		
	}else{
		console.log($heartdata)
	$.ajax({
		url: "/heart/save",
		contentType: "application/json",
		method: "POST",
		data: JSON.stringify({
			productType: "ITEM",
			productNum: ino
		}),
		success: function(result){
			console.log("안뇽 친구들")
			changeHeart(ino);
		},
		error: function(err){
			console.log("로그인 후 이용해주세요");
		}
	})
	}
}

function clickReviewHeart(irno){
	const $heartdata = $("#review_heart").find(".heartImg").data("id");
	if($heartdata){
		console.log($heartdata)
	$.ajax({
		url: "/heart/remove",
		contentType: "application/json",
		method: "DELETE",
		data: JSON.stringify({
			hlno: Number($heartdata),
			productType: "ITEMREVIEW",
			productNum: irno
		}),
		success: function(result){
			console.log("안녕 친구들")
			changeReviewHeart(irno);
		},
		error: function(err){
			console.log("로그인 후 이용해주세요");
		}
	})
		
	}else{
		console.log($heartdata)
	$.ajax({
		url: "/heart/save",
		contentType: "application/json",
		method: "POST",
		data: JSON.stringify({
			productType: "ITEMREVIEW",
			productNum: irno
		}),
		success: function(result){
			console.log("안뇽 친구들")
			changeReviewHeart(irno);
		},
		error: function(err){
			console.log("로그인 후 이용해주세요");
		}
	})
	}
}

function changeHeart(ino){
	console.log(ino)
	const $heart = $("#item_heart");
	$.ajax({
		url: "/heart/getOne",
		contentType: "application/json",
		data: JSON.stringify({
			productType: "ITEM",
			productNum: ino
		}),
		method: "POST",
		success: function(result){
			console.log(result);
			if(!result){
				$heart.html('<img class="heartImg" src="../img/empty_heart.png">');
			} else{
				$heart.html('<img class="heartImg" src="../img/full_heart.png" data-id=' + result.hlno + '>');
			}
		},
		error: function(err){
			$heart.html('<img class="heartImg" src="../img/empty_heart.png">');
		}
	})
}
function changeReviewHeart(irno){
	console.log(irno)
	const $reviewHeart = $("#review_heart");
	$.ajax({
		url: "/heart/getOne",
		contentType: "application/json",
		data: JSON.stringify({
			productType: "ITEMREVIEW",
			productNum: irno
		}),
		method: "POST",
		success: function(result){
			console.log(result);
			if(!result){
				$reviewHeart.html('<img style="width:12px" class="heartImg" src="../img/empty_heart.png">');
			} else{
				$reviewHeart.html('<img style="width:12px" class="heartImg" src="../img/full_heart.png" data-id=' + result.hlno + '>');
			}
		},
		error: function(err){
			$reviewHeart.html('<img style="width:12px" class="heartImg" src="../img/empty_heart.png">');
		}
	})
}

function clickItemCart(ino){
	console.log("cart click");
	$.ajax({
		url: "/cart/item/" + ino,
		method: "POST",
		success: function(result){
			alert("장바구니에 추가 완료!");
		},
		error: function(err){
			alert("로그인 후 이용하실 수 있습니다.");
		}
	})
}

async function modify(irno){
	$("#modify").show();
	$("#confirm").hide(); 

	try{
		
	const result = await $.get("/review/"+irno);
	console.log(result);
	image = result.capture;
	$("#content").val(result.content);
	$("#select_star").starrr('setRating', result.star);		
	} catch(e){
		console.error("리뷰 수정 데이터 오류", e);
		alert(e);
	}
	
	$("#popup").css('display','flex').hide().fadeIn();
	$("#modify").click(function(){
        modalClose(); //모달 닫기 함수 호출
        //수정 이벤트 처리
		//등록할 때 data를 ItemReviewDTO, List<String>으로 받아야 함
        let data = {
			irno: irno,
			content : $("#content").val(),
			star : $("#select_star").data('starrr').options.rating,
			capture: image
		};
		console.log(data);
		$.ajax({
			url: "/review/modify",
			method: "POST",
			method: "PUT",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(ino){
				$("#content").val("");
				$("#capture").val("");
				$("#select_star").starrr('setRating', 0);
				loadJsonData(ino);
			}
		})       
        
    });
}

function remove(irno){
	$.ajax({
		url: "/review/" + irno,
		method: "delete",
		success: function(result){
			alert("댓글을 삭제하였습니다.");
			loadJsonData(ino);
		}
	})
}

