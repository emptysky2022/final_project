$(function(){
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
	})
	loadJsonData();
})

function loadJsonData(){
	let ino = new URLSearchParams(window.location.search).get("ino");
	let reviewGroup = $("#reviewGroup");
	$.getJSON("/review/detail/" + ino, function(result){
		console.log(result);
		let str = "";
		$.each(result, function(index, review){
			  console.log(review)
			  str += '<div class="rv_r box5 ">';
			  str += '  <div class="box_l box6">';
			  str += '    <div class="imgbox box7">';
			  str += '      <a class="imglink box8" href="">';
			  str += '        <img class="rv_img item" src="' + review.capture + '"></a></div>';
			  str += '    <div class="rv_star box7_2"><p class="item">★★★★★</p></div></div>';
			  str += '  <div class="box_r box6">';
			  str += '    <div class="comment box7">';
			  str += '      <h2 class="write item">' + review.reviewer + '</h2>';
			  str += '      <p class="content item_2">' + review.content + '</p></div>';
			  str += '    <div class="rv_like box7"><i id="review_heart" class="fa-sharp fa-solid fa-thumbs-up fa-1x item" onclick="clickReviewHeart(' + review.irno + ')"> ' + review.heart + '</i></div></div>';
		})
		reviewGroup.html(str);
		console.log("끝!")
	})
	
}

function clickItemHeart(ino){
	$.ajax({
		url: "/item/heart/" + ino,
		contentType: "text/plain",
		success: function(result){
			console.log("result : " + result);
			$("#item_heart").html(result);
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
		}
	})
}

