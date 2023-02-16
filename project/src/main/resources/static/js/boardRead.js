$(function(){
	console.log("document ready test")
	
})
	
function loadReadData(bno){
	let boardListRE = $("#boardListRE_read");
	
	$.getJSON("/sample/list/" + bno, function(result){
		console.log(result);
		let str = "";
		
		str += '<h3>글 상세 페이지입니다.</h3>'
		str += '  <div class="boardListRE_bno">';
		str += '    <label>번호</label><input type="text" name="bno" value=' + result.bno + ' readonly></div>';
		str += '  <div class="boardListRE_title">';
		str += '    <label>제목</label><input type="text" name="title" value=' + result.title + ' readonly></div>';
		str += '  <div class="boardListRE_content">'
		str += '    <label>내용</label><textarea rows="5" name="content" readonly>' + result.content + '</textarea></div>';
		str += '  <div><label>사진</label><input type="text" name="image"></div>';
		str += '  <div>댓글</div>'
		str += '  <div><button type="submit" onclick="loadModifyData()">수정하기</button></div>'
		str += '  <div name="reply">';
		str += '    <div name="reply">';
		str += '      <div name="replyRegister">';
		str += '         <b>' + result.nickname + '님 리뷰를 남겨</b><br>'
		str += '         <textarea rows="3" cols="20" name="text" placeholder="리뷰를 작성하세요"></textarea>';
		str += '         <button type="button" name="replyRegisterBtn">등록하기</button>';
		str += '      </div><br>';
		str += '    <div name="repltList">';
		str += '    </div><br>';
		str += '  </div>';
		
		boardListRE.html(str);
		console.log("완");
	})
}

/*function loadModifyData(){
	let getbno = new URLSearchParams(window.location.search).get("bno");
	let boardListRE = $("#boardListRE");
	$.getJSON("/sample/list/" + bno, function(result){
		console.log(result);
		let str = "";
		
		str += '<h3>글 수정 페이지입니다.</h3>'
		str += '  <div class="boardListRE_bno">';
		str += '    <label>번호</label><input type="text" name="bno" value=' + result.bno + '></div>';
		str += '  <div class="boardListRE_title">';
		str += '    <label>제목</label><input type="text" name="title" value=' + result.title + '></div>';
		str += '  <div class="boardListRE_content">'
		str += '    <label>내용</label><textarea rows="5" name="content">' + result.content + '</textarea></div>';
		str += '  <div><label>사진</label><input type="text" name="image"></div>';
		str += '  <div><button type="submit">저장하기</button>&nbsp;&nbsp;<button type="reset">모두 지우기</button></div>'
		
		boardListRE.html(str);
		console.log("완");
	})
}*/