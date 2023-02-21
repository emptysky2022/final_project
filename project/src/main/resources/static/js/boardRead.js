$(function(){
	console.log("document ready test")
	
	loadRefreshData();
	
	$("#modifyReply").hide();
});
	
// 페이징 / 검색을 위해 url 따오는 것들(url param 추출)	
const urlStr = window.location.href;

const url = new URL(urlStr);
console.log(urlStr);

const urlParams = url.searchParams;
let page = urlParams.get('page');

// 리스트 첫 페이지에서 page 파라미터가 null이라서 페이지를 읽어오지 못함.
// if문으로 null 일 경우 1로 바꿔줌.
if(page == null) {
	page = 1;
}

const type = urlParams.get('type');
const keyword = urlParams.get('keyword');

console.log(page);
console.log(type);
console.log(keyword);

// 게시글 상세보기 함수
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
		str += '    <label>번호</label><input type="text" name="bno" value="' + board.bno + '" readonly></div>';
		str += '   <input type="hidden" id="boardBno" value="' + board.bno + '">';
		str += '  <div class="boardListRE_title">';
		str += '    <label>제목</label><input type="text" name="title" value="' + board.title + '" readonly></div>';
		str += '  <div class="boardListRE_content">'
		str += '    <label>내용</label><textarea rows="5" name="content" readonly>' + board.content + '</textarea></div>';
		str += '  <div><label>사진</label><input type="text" name="image"></div>';
		str += '  <div>댓글 수 <b>[' + board.replyCount + ']</b></div>';
		str += '  <div class="nickhide"><button type="submit" onclick="loadModifyData(' + board.bno + ')">수정하기</button>';
		str += '  <button type="button" class="read_remove" onclick="loadRemoveData(' + board.bno + ')">삭제하기</button></div><hr>';
		
		str += '  <h4 id="readReplyer" name="replyer">' + user.nickname + '님 리뷰 남겨</h4><br>';
        str += '   <input type="hidden" id="userNickname" value="' + user.nickname + '">';
        str += '   <textarea id="replyContent" rows="3" cols="20" name="text" placeholder="리뷰를 작성하세요"></textarea>';
        str += '   <button type="button" name="replyRegisterBtn" onclick="loadRegisterReplyData(' + board.bno + ')">리뷰 등록하기</button>';

		str += '  <div id="repliesListRE"></div>';
		
		boardListRE.html(str);
		console.log("완");
		
		$(".boardListRE_read").hide();
		loadRefreshData();
	})
	
	setTimeout(() => { // 
		loadReadRepliesData(bno);
	}, 500);
}

// 게시글 수정하기 함수
function loadModifyData(bno){
	
   let boardListRE = $("#boardListRE_modify");
   $.getJSON("/sample/list/" + bno, function(readInfo){
      console.log(readInfo);
      
      const [board, reply, user] = readInfo;
      
      let str = "";
      
      str += '<h3>글 수정 페이지입니다.</h3>'
      str += '  <div class="boardListRE_bno">';
  	  str += '    <label>번호</label><input type="text" id="modiBno" name="bno" value="' + board.bno + '" readonly></div>';
      str += '  <div class="boardListRE_title">';
      str += '    <label>제목</label><input type="text" id="modiName" name="title" value="' + board.title + '"></div>';
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

// 게시글 새로고침하기(실질적 새로고침 아님.)
function loadRefreshData(){
	
   urlParams.set("page", page);
	
   let boardListRE = $(".refresh_list");
   $.getJSON("/sample/list/refresh?page=" + page, function(result){
      let str = "";
      console.log(result[0].dtoList);
      
      $.each(result[0].dtoList, function(index, board){
         console.log(board);
      
      	 // 시간 포멧
      	 let reg = new Date(board.regDate);
       	 let dateFormat = reg.getFullYear() + '/' + (reg.getMonth() + 1) + '/'
       					+ reg.getDate() + ' ' + reg.getHours() + ':' + reg.getMinutes()
       					+ ':' + reg.getSeconds();
      		
         str += '<tr>';
     	 str += '   <td scope=row>' + board.bno + '</td>';
     	 str += '   <td><a onclick="loadReadData(' + board.bno + ')">' + board.title + ' <b><small>[' + board.replyCount + ']</samll></b>' + '</a></td>';
     	 str += '   <td>' + board.nickname + '</td>';
     	 str += '   <td>' + dateFormat + '</td>';
     	 str += '   <td>' + board.count + '</td>';
     	 str += '</tr>';
      
      })
      
      boardListRE.html(str);
      console.log("리프레시 완");
   })
}

// 게시글 삭제하기 함수
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
		loadReadRepliesData(bno);
	}, 1000);
	
	setTimeout(() => { // 
		loadRefreshData();
	}, 1500);
	
	$("#boardListRE_read").hide();   
   
}

// 게시판 수정하기 버튼 눌렸을 때 작동하는 함수
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

$(document).on("click", ".regiShow", function() {
	$(".regiBoard").show();
})

// 댓글 상세 정보
async function loadReadRepliesData(bno) {
	const repliesListRE = $("#repliesListRE");
	
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
         	str += '<button id="show" onclick="modifyReply(' + reply.bno + ', ' + reply.rno + ')">댓글 수정</button>';
         	str += '<button id="replyHeart" value="' + reply.heart + '">heart : ' + reply.heart + '</button>';
			
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
   
   loadReadData(bno);
   
}

// 댓글 삭제
function removeReply(bno, rno) {
   
   console.log("삭제 버튼");
   
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
function modifyReply(bno, rno) {
   
   $("#modifyReply").show().data("rno", rno).data("bno", bno);
   $("#modifyContent").val('');
   
   console.log(bno, rno);
}

// 댓글 수정 취소 버튼
$("#modifyReplyBtn-reset").click(function() {
	$("#modifyReply").hide();
});

// 수정 버튼 클릭
$("#modifyReplyBtn").click(function() {
	const $modifyReply = $(this).closest("#modifyReply");
	const bno = $modifyReply.data('bno');
	const rno = $modifyReply.data('rno');
	
	$.ajax({
		url: '/replies/' + rno,
		type: 'put',
		dataType: 'json',
		contentType: 'application/json; charset=utf-8;',
		data: JSON.stringify({
			rno,
			replyer: $("#userNickname").val(),
			content: $('#modifyContent').val()
		}),
		success: function(result) {
			loadReadData(bno);
		}
	})
})