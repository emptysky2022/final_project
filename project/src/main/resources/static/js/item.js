$(document).ready(function(){
	const items = $(".itemList");
	console.log("hello world!!");
	
	
	function showList(){
		$.ajax({
			url: "/item/list",
			method: "GET",
			dataType: "json",
			success: function(itemLists){
				console.log(itemLists);
				let str = "";
				$.each(itemLists, function(index, item){
					console.log(item);
					str += "<div class='prooductbox" + index + "' box4>";
					str += "  <div class='p_imgbox box5'>";
					str += "    <img class='p_img item' src='" + item.thumbnail + "'>";
					str += "  </div>";
					str += "  <div class='p_contentbox box5'>";
					str += "    <div class='p_writingbox box6'>";
					str += "      <div class='p_title item'>" + item.name + "</div>";
					str += "      <div class='p_explan item_2'>" + item.price + "</div>";
					str += "    </div>";
					str += "    <div class='p_reviewheartbox box6'>";
					str += "      <div class='p_reviewbox box7'>";
					str += "        <div class='review item'>조회수 " + item.count + "</div>";
					str += "      </div>";
					str += "      <div class='p_heartbox box7'>";
					str += "        <div class='heart item'>찜 " + item.heart + "</div>";
					str += "      </div>";
					str += "    </div>";
					str += "  </div>";
					str += "</div>";
				})
				items.html(str);
			}
		})
	}
	showList();
})