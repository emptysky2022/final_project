let itemDTO;
let image = new Array();
$(function(){
	console.log("document ready")
	$("#modal-open").click(function(){
		$("#modify").hide();
		$("#confirm").show();       
        $("#popup").css('display','flex').hide().fadeIn();
        //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
    });
    
    $("#close").click(function(){
        modalClose(); //모달 닫기 함수 호출
    });
    
    $("#confirm").click(function(){
        modalClose(); //모달 닫기 함수 호출
        getData();
        //컨펌 이벤트 처리
		//등록할 때 data를 ItemReviewDTO, List<String>으로 받아야 함
        let data = {
			item : itemDTO,
			image : image
		};
		console.log(data);
		$.ajax({
			url: "/item/register",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(ino){
				$("#content").val("");
				$("#capture").val("");
				search('','','');
			}
		})       
    });
    
    $("#thumbnail").on("change", function(){
		image = [];
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
				result.map(({imageURL}) => image.push(imageURL));
				$("#imageChange").attr('src', '/display?fileName='+image[image.length - 1]);
				console.log(image);
			},
			error: function(xhr, text, errorThrown){
				console.log(text);
			}
		});
	});
    
	search('','','');
})

function modalClose(){
    $("#popup").fadeOut(); //페이드아웃 효과
}

function getData(ino){
	itemDTO = {
		ino: ino,
		name: $("#name").val(),
		price: $("#price").val(),
		brand: $("#brand").val(),
		maker: $("#maker").val(),
		link: $("#link").val(),
		category1: $("select[name=category]").val()
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

function search(category, keyword, type){
	let list = $("#list");
	$.getJSON("/item/list/data?category="+category+"&keyword="+keyword+"&type="+type, function(result){
		let str = "";
		console.log(result)
		const [pageDTO, member] = result
		$.each(pageDTO.dtoList, function(index, item){
			str += '<div class="productbox box4" >';
			str += '<div onclick="location.href=\'/item/detail?ino=' + item.ino + '\'">';
			str += '  <div class="p_imgbox box5">';
			if(item.thumbnail.split(':')[0] === "https"){
				str += '    <img class="p_img item" src=' + item.thumbnail + '></div>';
			} else{
				str += '    <img class="p_img item" src=/display?fileName=' + item.thumbnail + '></div>';
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
				  str += '      <a class="modify item" onclick="modify(' + item.ino + ')">수정</a>';
				  str += '      <a class="remove item" onclick="remove(' + item.ino + ')">삭제</a></div>';				
			}	
			str += '</div>';
		})
		list.html(str);
	})
}

function getStar(grade){
	let star = '';
	for(let i = 1; i <= 5; i++){
		if(i <= Math.round(grade)) star += '★';
		else star += '☆';
	}
	return star;
}

async function modify(ino){
	$("#modify").show();
	$("#confirm").hide(); 
	try{
		
	const result = await $.get("/item/"+ino);
	console.log(result);
	
	if(result.thumbnail.split(':')[0] === "https"){
		$("#imageChange").attr("src", result.thumbnail);
		image.push(result.thumbnail);
	} else{
		$("#imageChange").attr("src", "/display?fileName="+result.thumbnail);
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
	
	$("#popup").css('display','flex').hide().fadeIn();
	$("#modify").click(function(){
        modalClose(); //모달 닫기 함수 호출
        getData(ino);
        //수정 이벤트 처리
		//등록할 때 data를 ItemReviewDTO, List<String>으로 받아야 함
        let data = {
			item : itemDTO,
			image : image
		};
		console.log(data);
		$.ajax({
			url: "/item/modify",
			method: "PUT",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(ino){
				search('','','');
			}
		})       
        
    });
}

function remove(ino){
	$.ajax({
		url: "/item/" + ino,
		method: "delete",
		success: function(result){
			alert("아이템을 삭제하였습니다.");
			search('','','');
		}
	})
}
