function search(category, keyword, type){
	let list = $("#list");
	$.getJSON("/item/list/data?category="+category+"&keyword="+keyword+"&type="+type, function(result){
		let str = "";
		console.log(result.dtoList)
		$.each(result.dtoList, function(index, item){
			console.log(item)
			str += '<div class="productbox box4" onclick="location.href=\'/item/detail?ino=' + item.ino + '\'">';
			str += '  <div class="p_imgbox box5">';
			str += '    <img class="p_img item" src=' + item.thumbnail + '></div>';
            str += '  <div class="p_contentbox box5">';
            str += '    <div class="p_writingbox box6">';
            str += '      <div class="p_title item">' + item.name + '</div>';
            str += '      <div class="p_explan item_2">가격 : ' + item.price + '원</div></div>';
            str += '    <div class="p_reviewheartbox box6">';
            str += '      <div class="p_reviewbox box7">';
            str += '        <div class="review item">조회수 ' + item.count + '</div></div>';
            str += '      <div class="p_heartbox box7">';
            str += '        <div class="heart item">찜 ' + item.heart + '</div></div></div></div></div>';
		})
		list.html(str);
	})
}