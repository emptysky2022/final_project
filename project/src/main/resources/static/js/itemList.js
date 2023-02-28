let itemDTO;
let imageStr = '';
$(function(){
	console.log("document ready")
	$("#item-modal-open").click(function(){
		$("#item-modal-open #modify").hide();
		$("#item-modal-open #confirm").show();
        $("#item-popup").css('display','flex').hide().fadeIn();
        //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
    });
    
	
    
    $(".close").click(function(){
		console.log('클릭은 되나')
        modalClose(); //모달 닫기 함수 호출
    });
    
    
    
    $("#cart-popup .confirm").click(async function(){
        modalClose(); //모달 닫기 함수 호출
        console.log("여긴 cart 수정부분")
        
        const cartItems = await $.get("/cart/list");
        const data = cartItems.map((item) => {
			return {
				amount : parseInt($(this).parent().parent().find(`tr#${item.sno}`).find(".amount").val()),
				ino : item.ino,
				sno : item.sno,
				mno : item.mno
			}
		});
		
		console.log(data);
		
		$.ajax({
			url: "/cart/items",
			method: "PUT",
			data: JSON.stringify(data),
			contentType: "application/json",
			success: function(result){
				console.log(result);
			},
			error: function(err){
				alert("잠시후에 다시 실행해주시길 바랍니다.")
			}
		})
    });
    
    $("#item-popup #confirm").click(function(){
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
				search('','','');
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
    
	search('','','');
})



function modalClose(){
    $("#cart-popup").fadeOut(); //페이드아웃 효과
    $("#item-popup").fadeOut(); //페이드아웃 효과
}

function cartModal(){
	const $cart= $("#cart-popup table");
	const $count = $("#cartCount");
	const $checkPrice = $("#txt_getChkList");
	$.ajax({
		url: "/cart/items",
		method: "GET",
		dataType: "json",
		success: function(result){
			console.log(result);
			if(result.length == 0){
				$("#cart-popup .popup .popup-body").html(`
					<div class="noneCart">
						<h1>장바구니가 텅 비었습니다.</h1>
					</div>
				`)
			}else{
				
				$cart.html( `
					<tr>
						<th class="selectall">
	                        <input type="checkbox" name="cartitem" id="cbx_chkAll" value="selectall">
	                    </th>
	                    <th class="productinfoheader">상품 정보</th>
	                    <th class="amountheader">수량</th>
	                    <th class="amountallheader">주문금액</th>
                    </tr>
				` );
				const cartHtml = result.map(([{thumbnail, name, brand, price}, cartDTO]) => {
					let images = '';
					if(thumbnail.split(',').length != 1){
						images = thumbnail.split(',').map((URL) => 
							`<img class="productimg" src="/display?fileName=${URL}&folderType=item">`
						).join('');
					} else {
						if(thumbnail.split(':').length != 1){
							images = `<img class="productimg" src="${thumbnail}">`;
						}else{
							images = `<img class="productimg" src="/display?fileName=${thumbnail}&folderType=item">`;
						}
					}
					return `
						<tr id="${cartDTO.sno}">
                            <td class="select">
                                <input type="checkbox" name="chk" value="select">
                            </td>

                            <td class="productinfobox">
                                <div class="productimgbox">
                           `
                            + images + 
                           `
                                </div>
                                <div class="producttextbox">
                                    <div class="productname">${name}</div>
                                    <div class="brandname">${brand}</div>
                                    <div class="price">${price}원</div>
                                </div>
                            </td>

                            <td class="amountbox">
                                <input type="number" class="amount" value="${cartDTO.amount}"></div>
                            </td>

                            <td class="amountallbox">
                                <div class="amountall">${cartDTO.amount * price}원</div>
                            </td>
                        </tr>
					`
				}).join("")
				
				$cart.append(cartHtml);
				$count.html(`총 ${result.length}개의 상품`)
			}
			checkPrice($checkPrice);
			$("#cart-modal-open #confirm").show();
	        $("#cart-popup").css('display','flex').hide().fadeIn();
		    $("#cbx_chkAll").click(function() {
				console.log("여기 들어감")
				if($("#cbx_chkAll").is(":checked")) $("input[name=chk]").prop("checked", true);
				else $("input[name=chk]").prop("checked", false);
				checkPrice($checkPrice);
			});
			$("input[name=chk]").click(function() {
				var total = $("input[name=chk]").length;
				var checked = $("input[name=chk]:checked").length;
		
				if(total != checked) $("#cbx_chkAll").prop("checked", false);
				else $("#cbx_chkAll").prop("checked", true); 
				checkPrice($checkPrice);
			});
			$("#cart-popup input[type=number]").bind("keyup mouseup", function(){
				const price = parseInt($(this).parent().prev().find(".price").html());
				const amount = $(this).val();
				
				$(this).parent().next().find(".amountall").html(`${price * amount}원`);
				checkPrice($checkPrice);
			})
		},
		error: function(err){
			alert("로그인 후 이용하실 수 있습니다.");
		}
	})		
};

function deleteCart(){
	let snos = new Array();
	const items = $("#cart-popup").find("table tr").map(function(){
		if($(this).find("input[name=chk]").is(":checked") == true){
			snos.push(parseInt($(this).attr('id')));			
		}
	});
	console.log(snos);
	$.ajax({
		url: "/cart/items",
		method: "DELETE",
		contentType: "application/json",
		data: JSON.stringify(snos),
		success: function(result){
			cartModal();
		}
	})
}

function orderCart(){
	let snos = new Array();
	const items = $("#cart-popup").find("table tr").map(function(){
		if($(this).find("input[name=chk]").is(":checked") == true){
			snos.push(parseInt($(this).attr('id')));			
		}
	});
	console.log(snos);
	$.ajax({
		url: "/history/items",
		method: "POST",
		contentType: "application/json",
		data: JSON.stringify(snos),
		success: function(result){
			console.log("orderCart 등록 성공?")
			deleteCart();
		}
	})
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
	
	$("#item-popup").css('display','flex').hide().fadeIn();
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
