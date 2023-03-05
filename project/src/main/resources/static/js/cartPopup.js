$(function(){
	$("#cart-popup").click(function(){
		cartModal()
		console.log("popup")
	    $("#cart-modal").css('display','flex').hide().fadeIn();
	    //팝업을 flex속성으로 바꿔준 후 hide()로 숨기고 다시 fadeIn()으로 효과
	});
	
	$("#cart-modal .close").click(function(){
		console.log('클릭은 되나')
    	$("#cart-modal").fadeOut(); //페이드아웃 효과
    });
	
	$("#cart-modal .confirm").click(async function(){
    	$("#cart-modal").fadeOut(); //페이드아웃 효과
        console.log("여긴 cart 수정부분")
        
        const cartItems = await $.get("/cart/list");
        const data = cartItems.map((item) => {
			return {
				amount : parseInt($(this).parent().parent().find(`tr#${item.sno}`).find(".cart-amount").val()),
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
})

function cartModal(){
	const $cart= $("#cart-modal table");
	const $count = $("#cartCount");
	const $checkPrice = $("#txt_getChkList");
	$.ajax({
		url: "/cart/items",
		method: "GET",
		dataType: "json",
		success: function(result){
			console.log(result);
			if(result.length == 0){
				$("#cart-modal .cart-popup .cart-popup-body").html(`
					<div class="cart-noneCart">
						<h1>장바구니가 텅 비었습니다.</h1>
					</div>
				`)
			}else{
				
				$cart.html( `
					<tr>
						<th class="cart-selectall">
	                        <input type="checkbox" name="cartitem" id="cbx_chkAll" value="selectall">
	                    </th>
	                    <th class="cart-productinfoheader">상품 정보</th>
	                    <th class="cart-amountheader">수량</th>
	                    <th class="cart-amountallheader">주문금액</th>
                    </tr>
				` );
				const cartHtml = result.map(([{thumbnail, name, brand, price}, cartDTO]) => {
					let images = '';
					if(thumbnail.split(',').length != 1){
						images = thumbnail.split(',').map((URL) => 
							`<img class="cart-productimg" src="/display?fileName=${URL}&folderType=item">`
						).join('');
					} else {
						if(thumbnail.split(':').length != 1){
							images = `<img class="cart-productimg" src="${thumbnail}">`;
						}else{
							images = `<img class="cart-productimg" src="/display?fileName=${thumbnail}&folderType=item">`;
						}
					}
					return `
						<tr id="${cartDTO.sno}">
                            <td class="cart-select">
                                <input type="checkbox" name="chk" value="select">
                            </td>

                            <td class="cart-productinfobox">
                                <div class="cart-productimgbox">
                           `
                            + images + 
                           `
                                </div>
                                <div class="cart-producttextbox">
                                    <div class="cart-productname">${name}</div>
                                    <div class="cart-brandname">${brand}</div>
                                    <div class="cart-price">${price}원</div>
                                </div>
                            </td>

                            <td class="cart-amountbox">
                                <input type="number" class="cart-amount" value="${cartDTO.amount}"></div>
                            </td>

                            <td class="cart-amountallbox">
                                <div class="cart-amountall">${cartDTO.amount * price}원</div>
                            </td>
                        </tr>
					`
				}).join("")
				
				$cart.append(cartHtml);
				$count.html(`총 ${result.length}개의 상품`)
			}
			checkPrice($checkPrice);
			$("#cart-modal #confirm").show();
	        $("#cart-modal").css('display','flex').hide().fadeIn();
		    $("#cbx_chkAll").click(function() {
				console.log("여기 들어감")
				if($("#cbx_chkAll").is(":checked")) $("input[name=chk]").prop("checked", true);
				else $("input[name=chk]").prop("checked", false);
				checkPrice($checkPrice);
			});
			$("input[name=chk]").click(function() {
				console.log("여긴 들어감")
				var total = $("input[name=chk]").length;
				var checked = $("input[name=chk]:checked").length;
		
				if(total != checked) $("#cbx_chkAll").prop("checked", false);
				else $("#cbx_chkAll").prop("checked", true); 
				checkPrice($checkPrice);
			});
			$("#cart-modal input[type=number]").bind("keyup mouseup", function(){
				const price = parseInt($(this).parent().prev().find(".cart-price").html());
				const amount = $(this).val();
				
				$(this).parent().next().find(".cart-amountall").html(`${price * amount}원`);
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
	const items = $("#cart-modal").find("table tr").map(function(){
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
	const items = $("#cart-modal").find("table tr").map(function(){
		if($(this).find("input[name=chk]").is(":checked") == true){
			snos.push(parseInt($(this).attr('id')));			
		}
	});
	console.log($("#cart-modal").find("table tr"))
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
	console.log("여긴 check")
	let price = 0;
	const items = $("#cart-modal").find("table tr").map(async function(){
		if($(this).find("input[name=chk]").is(":checked") == true){
			const itemPrice = parseInt($(this).find(".cart-amountall").html());
			price = price + itemPrice;
		}
	});
	console.log(price)
	$el.html(`${price}원`);
}