"use strict";



//typeit 화면에 글씨써주는 효과
 new TypeIt('.ni_text', {
    speed: 100,
    loop: true,
    cursorChar: '',
  })
  .pause(1500)
  .delete(11, {delay: 1000})
  .type('이번엔 어디를 갈까?')
  .pause(1500)
  .delete(11, {delay: 1000})
  .type('어떤 캠핑용품이 필요하지?')
  .pause(1500)
  .delete(14, {delay: 1000})
  .pause(1500)
  .type('이번에도 역시!')
  .pause(500)
  .go() ;
  
  
 $(function(){
	 search(1);
 })

 
function search(value){
	let list = $("#list");
	$.getJSON("/item/list/data?category=&keyword=&type=&page="+value+"&size=4", function(result){
		let str = "";
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
		console.log("hello")
		
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
	search(page);
}

function getStar(grade){
	let star = '';
	for(let i = 1; i <= 5; i++){
		if(i <= Math.round(grade)) star += '★';
		else star += '☆';
	}
	return star;
}

//스크롤 내릴 때 속도를 다르게 줄 수 있다.

/*var rellax = new Rellax('.rellax');*/
  



