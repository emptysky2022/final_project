let itemDTO;
let imageStr = '';
let page = new URLSearchParams(document.location.search).get('page');
let condition = {
	category:'',
	keyword:'',
	type:''
};
$(function(){
	console.log("document ready")
	$("#item-modal-open").click(function(){
		console.log("아이템 추가 버튼")
		$(".popup #modify").hide();
		$(".popup #confirm").show();
        $(".popup-wrap").css('display','flex').hide().fadeIn();
        //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
    });
    
    $(".close").click(function(){
		console.log('클릭은 되나')
        modalClose(); //모달 닫기 함수 호출
    });
    
    $(".popup #confirm").click(function(){
        modalClose(); //모달 닫기 함수 호출
        getData();
		$.ajax({
			url: "/item/register",
			method: "POST",
			data: JSON.stringify(itemDTO),
			contentType: "application/json",
			success: function(ino){
				console.log(ino + "통과!")
				$("#link").val("");
				$("#capture").val("");
				search('','','', 1);
			}
		})       
    });
    
    $("#thumbnail").on("change", function(){
		imageStr = '';
		console.log("capture change event");
		// 이미지 업로드 클릭시 파일 받아서 uploadAjax controller로 이동
		let formData = new FormData();
		const inputFile = $(this);
		const files = inputFile[0].files;
		let appended = false;
		console.log(imageStr);
		$.each(files, function(index, file){
			if(!checkExtension(file.name, file.size)){
				return false;
			}
			console.log(file);
			formData.append("uploadFiles", file);
			appended = true;
		})
		if(!appended) {return};
		formData.append("webPath", "item");
		for(let value of formData.values()){
			console.log(value)
		}
		console.log(formData);
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
				result.map(({uuid, fileName}) => imageStr += uuid+'_'+fileName+',');
				imageStr = imageStr.substring(imageStr.length-1, 0);
				let getImageURL = imageStr.split(',');
				$("#imageChange").attr('src', '/display?fileName='+ getImageURL[getImageURL.length-1] +'&folderType=item');
				console.log(imageStr);
			},
			error: function(xhr, text, errorThrown){
				console.log(text);
			}
		});
	});
    
	search('','','', 1);
})



function modalClose(){
    $(".popup-wrap").fadeOut(); //페이드아웃 효과
}

function checkPrice($el){
	let price = 0;
	const items = $("#cart-popup").find("table tr").map(async function(){
		if($(this).find("input[name=chk]").is(":checked") == true){
			const itemPrice = parseInt($(this).find(".amountall").html());
			price = price + itemPrice;
		}
	});
	$el.html(`${price}원`);
}

function getData(ino){
	console.log($("select[name=category]").val())
	if(ino){
		itemDTO = {
			ino: ino,
			name: $("#name").val(),
			price: $("#price").val(),
			brand: $("#brand").val(),
			maker: $("#maker").val(),
			link: $("#link").val(),
			thumbnail: imageStr,
			category1: $("select[name=category]").val()
		}
	} else{
		itemDTO = {
			name: $("#name").val(),
			price: $("#price").val(),
			brand: $("#brand").val(),
			maker: $("#maker").val(),
			link: $("#link").val(),
			thumbnail: imageStr,
			category1: $("select[name=category]").val()
		}
	}
	$("#name").val("");
	$("#price").val("");
	$("#brand").val("");
	$("#maker").val("");
	$("#link").val("");
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

function search(category, keyword, type, value){
	condition = {
		category: category?category:condition.category,
		keyword: keyword.value?keyword.value:condition.keyword,
		type: type?type:condition.type
	}
	console.log(condition)
	if(category){		
		$(".tent :button").css('background-color', 'rgb(162, 196, 225)');
		$(".tent :button[value='" + category + "']").css('background-color', 'rgb(0, 98, 204)');
	}
	let list = $("#list");
	$.getJSON("/item/list/data?category="+condition.category+"&keyword="+condition.keyword+"&type="+condition.type+"&page="+value+"&size=16", function(result){
		let str = "";
		console.log(result)
		const [pageDTO, member] = result
		$.each(pageDTO.dtoList, function(index, item){
			str += '<div class="productbox box4" >';
			str += '<div onclick="location.href=\'/item/detail?ino=' + item.ino + '\'">';
			str += '  <div class="p_imgbox box5">';
			if(item.thumbnail != null && item.thumbnail.split(':')[0] === "https"){
				str += '    <img class="p_img item" src=' + item.thumbnail + '></div>';
			} else{
				str += '    <img class="p_img item" src=/display?fileName=' + item.thumbnail.split(',')[item.thumbnail.split(',').length-1] + '&folderType=item></div>';
			}
            str += '  <div class="p_contentbox box5">';
            str += '    <div class="p_writingbox box6">';
            str += '      <div class="p_title item">' + item.name + '</div>';
            str += '      <div class="p_explan item_2">가격 : ' + item.price + '원</div></div>';
            str += '    <div class="p_reviewheartbox box6">';
            str += '      <div class="p_reviewbox box7">';
            str += '        <div class="review item">조회수 ' + item.count + '</div></div>';
            str += '      <div class="p_heartbox box7">';
            str += '        <div class="heart item">찜 ' + item.heart + '</div></div></div>';
            str += '	<div class="star">' + getStar(item.star) + ' ' + Math.round(item.star * 10)/10 + '</div></div></div>';
           	if(member.mno === item.mno){
				  str += '    <div class="writer box8">';
				  str += '      <a class="modify item" onclick="modify(' + item.ino + ', ' + value + ')">수정</a>';
				  str += '      <a class="remove item" onclick="remove(' + item.ino + ', ' + value + ')">삭제</a></div>';				
			}	
			str += '</div>';
	
		})
		list.html(str);
		
		const $paging = $(".pagingEl");
		const {page: pageNum, prev: isPrev, next: isNext, end: endPage} = pageDTO;
		
		let pagination = `<ul class="pagination h-100 justfy-content-center align-items-conter boardpage box5">`;
      
        if(isPrev) {
	    	pagination += `<li class="page-item">
	    				       <a class="page-link bt_prev item_2" onclick="pageClick(${pageDTO.start-1})" tabindex="-1">이전</a>
	    				   </li>`;
	    }
	    
	    for(const value of pageDTO.pageList) {
	    	pagination += `<li class="page-item num ${value == pageDTO.page ? 'active' : ''}">
	    				       <a class="page-link" onclick="pageClick(${value})">${value}</a>
	    				       <input type="hidden" name="pageValue" value="${value}">
	    				   </li>`;
	    }
	    
	    if(isNext) {
	    	pagination += `<li class="page-item num">
	    				<a class="page-link" onclick="pageClick(${endPage + 1})">다음</a><li></ul>`;
	    }
	    pagination += "</ul>"
	    $paging.html(pagination);
	    
	    page = $(".pagingEl").find("[name='pageValue']").val();
	})
}

function pageClick(page){
	search(condition.category, condition.keyword, condition.type, page);
}

function getStar(grade){
	let star = '';
	for(let i = 1; i <= 5; i++){
		if(i <= Math.round(grade)) star += '★';
		else star += '☆';
	}
	return star;
}

async function modify(ino, value){
	$("#modify").show();
	$("#confirm").hide(); 
	try{
		
	const result = await $.get("/item/"+ino);
	console.log(result);
	
	if(result.thumbnail.split(':')[0] === "https"){
		$("#imageChange").attr("src", result.thumbnail);
		imageStr += result.thumbnail;
	} else{
		let itemURLs = result.thumbnail.split(',');
		$("#imageChange").attr("src", "/display?fileName="+itemURLs[itemURLs.length-1]+"&folderType=item");
	}
	$("#name").val(result.name);
	$("#price").val(result.price);
	$("#link").val(result.link);
	$("#brand").val(result.brand);
	$("#maker").val(result.maker);
	$("select[name=category]").val(result.category1);
	itemDTO = result;
	console.log(itemDTO);
	} catch(e){
		console.error("아이템 수정 데이터 오류", e);
		alert(e);
	}
	
	$(".popup-wrap").css('display','flex').hide().fadeIn();
	$("#modify").click(function(){
        modalClose(); //모달 닫기 함수 호출
        getData(ino);
        //수정 이벤트 처리
		//등록할 때 data를 ItemReviewDTO, List<String>으로 받아야 함
		$.ajax({
			url: "/item/modify",
			method: "PUT",
			contentType: "application/json",
			data: JSON.stringify(itemDTO),
			success: function(ino){
				search('','','', value);
			}
		})       
        
    });
}

function remove(ino, value){
	$.ajax({
		url: "/item/" + ino,
		method: "delete",
		success: function(result){
			alert("아이템을 삭제하였습니다.");
			search('','','', value);
		}
	})
}

