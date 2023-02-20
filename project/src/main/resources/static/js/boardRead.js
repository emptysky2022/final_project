$(function(){
	console.log("document ready test")
	
	
});

const urlStr = window.location.href;
	
	const url = new URL(urlStr);
	
	console.log(urlStr);
	
	const urlParams = url.searchParams;

	const page = urlParams.get('page');
	
	const type = urlParams.get('type');
	
	const keyword = urlParams.get('keyword');

	console.log(page);
	console.log(type);
	console.log(keyword);
	
function loadReadData(bno){
	
	
	
	let boardListRE = $("#boardListRE_read");
	
	$.getJSON("/sample/list/" + bno, function(result){
		console.log(result);
		
		const [board, reply, user] = result;
		
		console.log("게시글 상세", board);
		console.log("댓글", reply);
		
		let str = "";
			
		str += '<h3>글 상세 페이지입니다.</h3>'
		str += '  <div class="boardListRE_bno">';
		str += '    <label>번호</label><input type="text" name="bno" value=' + board.bno + ' readonly></div>';
		str += '   <input type="hidden" id="boardBno" value="' + board.bno + '">';
		str += '  <div class="boardListRE_title">';
		str += '    <label>제목</label><input type="text" name="title" value=' + board.title + ' readonly></div>';
		str += '  <div class="boardListRE_content">'
		str += '    <label>내용</label><textarea rows="5" name="content" readonly>' + board.content + '</textarea></div>';
		str += '  <div><label>사진</label><input type="text" name="image"></div>';
		str += '  <div>댓글 수 : ' + board.replyCount + '</div>';
		str += '  <div><button type="submit" onclick="loadModifyData(' + board.bno + ')">수정하기</button></div><hr>';
		str += '  <div><button type="button" class="read_remove" onclick="loadRemoveData(' + board.bno + ')">삭제하기</button></div><hr>';
		
		str += '  <h4 id="readReplyer" name="replyer">' + user.nickname + '님 리뷰 남겨</h4><br>';
        str += '   <input type="hidden" id="userNickname" value="' + user.nickname + '">';
        str += '   <textarea id="replyContent" rows="3" cols="20" name="text" placeholder="리뷰를 작성하세요"></textarea>';
        str += '   <button type="button" name="replyRegisterBtn" onclick="loadRegisterReplyData(' + board.bno + ')">리뷰 등록하기</button>';

		str += '  <div id="repliesListRE"></div>';
		
		boardListRE.html(str);
		console.log("완");
	})
	
	
	
	setTimeout(() => { // 
		loadReadRepliesData(bno);
	}, 500);
}

function loadModifyData(bno){
	
    let boardListRE = $("#boardListRE_modify");
    $.getJSON("/sample/list/" + bno, function(readInfo){
		console.log(readInfo);
      
      	const [board, reply, user] = readInfo;
      	console.log("readInfo[0] : ", board);
      
      	let str = "";
      
      	str += '<h3>글 수정 페이지입니다.</h3>'
      	str += '  <div class="boardListRE_bno">';
  	 	str += '    <label>번호</label><input type="text" id="modiBno" name="bno" value=' + board.bno + ' readonly></div>';
      	str += '  <div class="boardListRE_title">';
      	str += '    <label>제목</label><input type="text" id="modiName" name="title" value=' + board.title + '></div>';
      	str += '  <div class="boardListRE_content">'
      	str += '    <label>내용</label><textarea id="updateContent" rows="5" name="content">' + board.content + '</textarea></div>';
      	str += '    <input type="hidden" id="modiMno" name="mno" value="' + user.mno + '" readonly>';
      	str += '    <input type="hidden" id="modinickname" name="nickname" value="' + user.id + '" readonly>';
      	str += '  <div><label>사진</label><input type="text" name="image"></div>';
      	str += '  <div><button type="button" id="boardModify">저장하기</button>&nbsp;&nbsp;<button type="reset">되돌리기</button></div>'
      
      	boardListRE.html(str);
      	console.log("리드 모디파이 완");
   })
}

function loadRefreshData(){
	
	urlParams.set("page", page);
	
    let boardListRE = $(".refresh_list");
    $.getJSON("/sample/list/refresh?page="+page, function(result){
    	let str = "";
    	console.log(result[0].dtoList);
      
    	$.each(result[0].dtoList, function(index, board){
       		console.log(board);
      
         	str += '<tr>';
         	str += '   <td scope=row>' + board.bno + '</td>';
         	str += '   <td><a onclick="loadReadData(' + board.bno + ')">' + board.title + '</a></td>';
         	str += '   <td>' + board.nickname + '</td>';
         	str += '   <td>' + board.regDate + '</td>';
         	str += '   <td>' + board.count + '</td>';
         	str += '</tr>';
      
      	})
      	
      
      boardListRE.html(str);
      console.log("리프레시 완");
      
	})
}

function loadRemoveData(bno){
   
   console.log("온클릭 성공");
   
   
   $.ajax({
      url: '/sample/list/remove/' + bno,
      method: "delete",
      success: function(result){
         console.log("result : " + result);
         if(result === bno) {
            alert("게시글이 삭제되었습니다.");
         }
      }
   })
   
	setTimeout(() => { // 
		loadReadData(bno);
	}, 500);
	
	setTimeout(() => { // 
		loadReadRepliesData(bno);
	}, 1000);
	
	setTimeout(() => { // 
		loadRefreshData();
	}, 1500);	
   
}

$(document).on("click", "#boardModify", function(){
      console.log("화악");
      var bno = $("#modiBno").val();
      var board = {
            bno: bno,
            title: $('#modiName').val(),
            content: $('#updateContent').val(),
            mno: $('#modiMno').val(),
      }
      
      console.log(board);
      
      $.ajax({
         url: '/sample/update/' + bno,
         method: 'put',
         data: JSON.stringify(board),
         contentType: 'application/json; charset=utf-8',
         success: function(result){
            console.log("result : " + result);
            if(result === 'success') {
               alert("게시글이 수정되었습니다.");
               loadReadData(bno);
               loadRefreshData();
            }
         }
   
      });
})

// 댓글 상세 정보
async function loadReadRepliesData(bno) {
	const repliesListRE = $("#repliesListRE");
	//const replies = await $.get(`/replies/${bno}/all`);
	//const html = replies.map((reply) => `<h5 data-rno="${reply.rno}">${reply.content}</h5>`).join("");
	//repliesListRE.append(html);
	
	function formatTime(str) {
		
		var date = new Date(str);
		
		return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
	}
	

	$.getJSON("/replies/" + bno + "/all", function(result) {
		
		repliesListRE.empty();
		
		let str = "";
		for(var reply of result) {
			console.log(reply);
			
			str += '<h5 id="replyer" data-rno="' + reply.rno + '">' + reply.replyer + '<small>' + reply.heart + '</small></h5>';
            str += '<h6 id="content" data-content="' + reply.content + '">' + reply.content + '</h6>';
            str += '<h6 id="regDate" data-regDate="' + reply.regDate + '">' + formatTime(reply.regDate) + '</h6>';
         	str += '<button id="removeBtn" onclick="removeReply(' + reply.bno + ')">댓글 삭제</button>';
         	str += '<button id="show" onclick="modifyReply(' + reply.bno + ')">댓글 수정</button>';
         	str += '<button id="replyHeart">heart</button>';
        	str += '<input type="hidden" id="replyRno" value="' + reply.rno + '">';
			
			str += '<hr>';
		}
		repliesListRE.append(str);
	});
}

// 댓글 등록
function loadRegisterReplyData(bno) {
   
   console.log("등록 버튼 test");
   
   $.ajax({
      url: '/replies/' + bno,
      type: 'post',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8;',
      data: JSON.stringify ({
         bno: $('#boardBno').val(),
         replyer: $('#userNickname').val(),
         content: $('#replyContent').val()
      }),
      success: function(result) {
         console.log("성공");
      },
      error: function(xhr, status, error) {
         alert("실패");
      }
      
   }) 
   
   console.log("되는겨?");
   loadReadData(bno);
   
}

// 댓글 삭제
function removeReply(bno) {
   
   console.log("삭제 버튼");
   
   var rno = $("#replyRno").val();
   
   console.log(rno);
   
   $.ajax({
      url: '/replies/' + bno + "/" + rno,
      type: 'delete',
      dataType: 'json',
      data: JSON.stringify({
         bno: $('#boardBno').val(),
         replyer: $('#userNickname').val(),
         content: $('#replyContent').val()
      }),
      contentType: 'application/json; charset=utf-8',
      success:function (result){
         console.log("result : " + result);
         
      }
   })
   
   loadReadData(bno);
   
}


// 댓글 수정
function modifyReply(bno) {
   
   console.log("수정 버튼");
   
   var rno = $("#replyRno").val();
   
   console.log(rno);
   
}
