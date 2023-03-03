$(function(){
	$.get("/item/list/data?category=&keyword=&type=&size=4", function(response){
		const [result] = response;
		const itemList = result.dtoList;
		console.log(itemList);
		const $sec1 = $("#sec1-container");
		const html = itemList.map(({ino, thumbnail, heart, count, name}) => 
		`
			<div class="productbox box4">
			  <div onclick="location.href='/item/detail?ino=${ino}'">
			    <div class="p_imgbox box5"> 
			      <img class="p_img item" src="${thumbnail}">
			    </div>  
			    <div class="p_contentbox box5">   
			      <div class="p_writingbox box6">    
			        <div class="p_title item">${name}</div> 
			      </div>  
			      <div class="p_reviewheartbox box6">    
			        <div class="p_reviewbox box7">  
			          <div class="review item">조회수 ${count}</div>
			        </div>    
			        <div class="p_heartbox box7">      
			          <div class="heart item">찜 ${heart}</div>
			        </div>
			      </div>	
			    </div>
			  </div>
			</div>
			`
		).join("");
		$sec1.html(html);
	});
})

/*

<div class="cp box3 ">
	        <div class="cp_textbox">
		
	            <h1 class="cp_title item "> 캠핑 용품</h1>
	            <p class="cp_comment item2" >${item.name}</p>
	            <a class="cp_link" href="">캠핑 물품 구매</a><br>
	        </div>
	
	        <div class="cp_imgbox box5">
	            <a class="cp_link item" href="">
	                <img class="cp_img item" src="../img/family4.jpg" >
	            </a>
	        </div>
	
	    </div>	*/