"use strict";

/*화면크기가 태블릿 이하일 때, 네비게이션에서 햄버거 누르면 메뉴 보이게 하는 기능 */
const menu = document.querySelector("header .box2_2");
const mypage = document.querySelector("header .box2_3");
var sec1 = document.querySelector('.sec1');
var sec2 = document.querySelector('.sec2');
var sec3 = document.querySelector('.sec3');
sec1.style.display="none";
sec2.style.display="none";
sec3.style.display="none";


/*input file타입의 이미지 파일만 올리게 하기 위해 사용*/
function chk_file_type(obj) {
	var file_kind = obj.value.lastIndexOf('.');
    var file_name = obj.value.substring(file_kind + 1, obj.length);
    var file_type = file_name.toLowerCase();



    var check_file_type = new Array();

    check_file_type = ['jpg', 'gif', 'png', 'jpeg', 'bmp'];



    if (check_file_type.indexOf(file_type) == -1) {
        alert('이미지 파일만 선택할 수 있습니다.');
        var parent_Obj = obj.parentNode
        var node = parent_Obj.replaceChild(obj.cloneNode(true), obj);
        return false;
    }
}


function toggleBtn1() {
    // 토글 할 버튼 선택 (.sec1) 
    console.log(sec1)
    // sec1 숨기기 (display: none)
    if (sec1.style.display !== 'none') {
        sec1.style.display = 'none';
    }
    // sec1 보이기 (display: flex)
    else {
        sec1.style.display = 'flex';
        sec2.style.display ='none';
        sec3.style.display ='none';
    }
}


function toggleBtn2() {
    console.log(sec2)
    if (sec2.style.display !== 'none') {
        sec2.style.display = 'none';
    }
    else {
        sec2.style.display = 'flex';
        sec1.style.display ='none';
        sec3.style.display ='none';
    }
}

function toggleBtn3() {
    //console.log(sec3)
    //if (sec3.style.display !== 'none') {
    //    sec3.style.display = 'none';
    //}
    //else {
        sec3.style.display = 'flex';
        sec1.style.display ='none';
        sec2.style.display ='none';
    //}
}




$(function() { // 보이기 | 숨기기
	$("#toplevel").click(function() { 
		$('html, body').animate({ scrollTop : 0 // 0 까지 animation 이동합니다. 
  		}, 400); // 속도 400 
  		return false;  
  	}); 
});





$(function() { // 보이기 | 숨기기
  	$("#listlevel").click(function() { 
	var location = document.querySelector(".sec4").offsetTop;
  	window.scrollTo({top:location, behavior:'smooth'});
  }); // 버튼 클릭시
}); 



// $(function() { // 보이기 | 숨기기

// }); 

// 페이징 / 검색을 위해 url 따오는 것들(url param 추출)   
let urlStr = window.location.href;

let url = new URL(urlStr);
console.log(urlStr);

let urlParams = url.searchParams;
let page = urlParams.get('page');

// 로그인 유저 정보
const memberNickname = $("#memberNickname").val(); 

// 리스트 첫 페이지에서 page 파라미터가 null이라서 페이지를 읽어오지 못함.
// if문으로 null 일 경우 1로 바꿔줌.
if(page == null) {
	page = 1;
}

$(function(){
	console.log("document ready test")
   
	loadRefreshData();
   
    // 다영 - form태그 안에 submit버튼이랑 input에서 엔터를 누르면 실행되는 이벤트
    $("#searchForm").on("submit", (e) => {
        // form action으로 페이지 이동이 되지 않게 submit default 이벤트를 삭제시킴
        e.preventDefault(); 
        // 게시글 검색으로 넘어감
        loadRefreshData(1);
    });
   
});

function formatTime(str) {
    var date = new Date(str);
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
}


// 게시물 목록
// 게시글 새로고침하기(실질적 새로고침 아님.) --> 다영 - 새로고침되는거 위에ㅔ서 고침
// async: 비동기 함수로 선언, await을 사용하기 위해 필수로 적어야함
// await : callback 함수(success, error)등을 사용하지 않고, 비동기 함수가 완료될때까지 기다렸다가 결과값을 변수에 넣어줌
async function loadRefreshData(x) {
   
   // 검색 키워드, 타입
   const $searchForm = $("#searchForm"); // jquery element 객체는 변수 앞에 $를 붙이는게 국룰임
   const keyword = $searchForm.find("[name='keyword']").val();
   const type = $searchForm.find("[name='type']").val();
   const category = $searchForm.find("[name='category']").val();
   
   // 다른 페이지 갔다 왔을 때를 대비해서 page, keyword, type은 querystring으로 남겨놔야함 (되게 해야함..)
   // urlParams.set("page", page);  default page가 1이라서 계속 1페이지를 가져옴.
   urlParams.set("keyword", keyword);
   urlParams.set("type", type);
   urlParams.set("category", category);
   
   page = x || 1; // x or 1(x = null, undefined, 0)
   
    // 오류 발생하면 catch 실행 (await 실패 시 오류처리 하기위해 꼭 써야함, 또는 다른 오류 났을 때도 많이 씀)
	try {
        const response = await $.get("/sample/list/refresh", {page, keyword, type, category});
    	const [result] = response;
	    const pageInfo = result.pageList;
	    const boardList = result.dtoList;
	    const {page: pageNum, prev: isPrev, next: isNext, end: endPage} = result;
	    const $board = $("#getList");
	    const $paging = $(".pagingEl");
      
        console.log(boardList);
      
        // Array.map은 배열 데이터를 변환해서 새로운 배열로 만들 때 사용함, 많이 쓰니 자주 써보도록
        // .map({bno, title..}) 과 같이 board 안에 데이터를 중괄호로 바로 꺼내는 방식을 비구조화 할당이라고 함, 엄청 많이 쓰이니 공부 필요
        // ` BackTick(억음부호)로 사용 시 변수를 바로 사용할 수 있어 많이 사용함
        // html += '<td>...' 방식은 사용 지양해야함
        const html = boardList.map(({bno, title, regDate, nickname, replyCount, count, heart}) =>
            `<tr>
               <td class="num item">${bno}</td>
               <td class="title item_2"><a onclick="loadReadData(${bno}), toggleBtn3(); window.scrollTo(0, 0);">${title}&nbsp<b><small>[${replyCount}] 
                   <img id="listHeartImg" src="../img/full_heart.png"><spen id="heartSpen"> ${heart}</spen></small></b></a></td>
               <td class="writer item_3">${nickname}</td>
               <td class="date item_4">${formatTime(regDate)}</td>
               <td class="count item_5">${count}</td>
             </tr>`
        ).join("");
        
        $board.html(html);
      
      	let pagination = `<ul class="pagination h-100 justfy-content-center align-items-conter boardpage box5">`;
      
        if(isPrev) {
	    	pagination += `<li class="page-item">
	    				       <a class="page-link bt_prev item_2" onclick="loadRefreshData()" tabindex="-1">이전</a>
	    				   </li>`;
	    }
	    
	    for(const value of pageInfo) {
	    	pagination += `<li class="page-item num ${value == page ? 'active' : ''}">
	    				       <a class="page-link" onclick="loadRefreshData(${value})">${value}</a>
	    				       <input type="hidden" name="pageValue" value="${value}">
	    				   </li>`;
	    }
	    
	    if(isNext) {
	    	pagination += `<li class="page-item num">
	    				<a class="page-link" onclick="loadRefreshData(${endPage + 1})">다음</a><li></ul>`;
	    }
	    pagination += "</ul>"
	    $paging.html(pagination);
	    
	    page = $(".pagingEl").find("[name='pageValue']").val();
	    
    } catch (e) {
        console.error("게시판 불러오는 중 오류 발생", e);
        alert(e);
    }
}

// 게시글 상세보기 함수
async function loadReadData(bno) {
	const $boardList = $("#readList");
    try {
        const [board, reply, user] = await $.getJSON(`/sample/list/${bno}`);
        const heart = await heartCheck(bno);
        const hlno = heart ? JSON.parse(heart).hlno : undefined;
		
		console.log("board : ", board);
		
        $boardList.html(`
        <div class="titlebox box4">
            <div class="titleStrong read">
                <strong class="titletext item">게시글 상세</strong>
            </div>
        </div>
        <div class="boardviewbox box4">
            <div class="boardview box5">
                <div class="title item" name="title">${board.title}</div>
                    <div class="infobox item">
                        <dl class="numbox box6">
                            <dt class="numtext item">번호</dt>
                            <dd class="num item" name="bno">${board.bno}</dd>
                            <input type="hidden" name="hiddenBno" id="boardBno" value="${board.bno}">
                        </dl>
                        <dl class="datebox box6">
                            <dt class="datetext item">작성일</dt>
                            <dd class="date item_2" name="regDate">${formatTime(board.regDate)}</dd>
                        </dl>
                        <dl class="viewsbox box6">
                            <dt class="viewstitle item">조회수</dt>
                            <dd class="views item_2" name="count">${board.count}</dd>
                        </dl>
                        <dl class="writebox box6">
                            <dt class="writetext item">글쓴이</dt>
                            <dd class="write item_2" name="writer" id="boardWirter${board.nickname}">${board.nickname}</dd>
                        </dl>
                    </div>
                    <div class="content item" name="content"><pre>${board.content}</pre></div>
                </div>
            </div>
            <div class="boardviewbox box4">
                <div class="button box5">
                    <a onclick="loadModifyData(${board.bno}), toggleBtn2()" id="modifyBtn" class="modify item_2">수정하기</a>
                    <div class="img heart-box">
                        <div class="heart ${hlno ? 'h-on' : 'h-off'}" data-hlno="${hlno}" data-bno="${board.bno}"></div>
                        <h6 class="heart-count">${board.heart}</h6>
                    </div>
                    <button type="button" id="boardRemove" class="on" onclick="loadRemoveData(${board.bno})">삭제하기</button>
                </div>
            </div>
            <div class="commentbox">
                <div class="commenttextbox">
                <div class="commenttextregister">
                    <strong><p>댓글 등록</p></strong>
                </div>
            </div>
            <div class="ct_reg_btnbox">
                <textarea name="ct_reg" class="ct_reg" cols="20" rows="5" placeholder=" 댓글을 입력하세요" id="replyContent"></textarea>
                <input type="hidden" id="userNickname" value="${user.nickname}">
                <input type="button" class="ct_btn" value="등록하기" onclick="loadRegisterReplyData(${board.bno})">
            </div>
        </div>
       `);


        // 수정하려는 유저와 게시판 글쓴이가 같을 경우에 실행할 것
        if (memberNickname != board.nickname) {
            $("#modifyBtn").hide();
            $("#boardRemove").hide();
        }
		
        setTimeout(() => {
            loadReadRepliesData(bno);
        }, 500);
    } catch (e) {
        console.error("힝", e);
    }
}



// 게시글 수정하기 함수
function loadModifyData(bno, nickname){
	
    let modifyBoard = $("#modifyBoard");
    $.getJSON("/sample/list/" + bno, function(readInfo){
        console.log(readInfo);
        
        const [board, reply, user] = readInfo;
        
        let str = `
        			<div class="titlebox">
		        		<dl>
		           			<dt class="titletext item">제목</dt>
		        			<dd><input type="text" id="modifyTitle" value="${board.title}"></dd>
		        		</dl>
		        		<dl>
		           			<dt>글쓴이</dt>
		        			<dd><input type="text" value="${board.nickname}" readonly></dd>
		        		</dl>
       				 </div>
        			<div class="content">
        				<textarea id="modifyContent">${board.content}</textarea>
        			</div>
		        	<input type="hidden" id="modifyBno" name="bno" value="${board.bno}">
		        	<input type="hidden" id="modifyMno" name="mno" value="${user.mno}">
        		  `;
        
        	
        
        modifyBoard.html(str);
        console.log("modify form read");
    })
}

// 게시판 수정하기 버튼 눌렸을 때 작동하는 함수
$(document).on("click", "#boardModify", function(){

	toggleBtn3();
	
	var bno = $("#modifyBno").val();
    var board = {
        bno: bno,
        title: $('#modifyTitle').val(),
        content: $('#modifyContent').val(),
        mno: $('#modifyMno').val(),
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
           $("#boardModifyForm").hide();
        }
        
    });
      
})


// 게시글 삭제하기 함수
function loadRemoveData(bno) {
   $.ajax({
      url: '/sample/list/remove/' + bno,
      method: "delete",
      success: function(result){
         console.log("result : " + result);
         if(result === bno) {
            alert("게시글이 삭제되었습니다.");
            $("#boardList").hide(); 
         }
      }
   })
   
   setTimeout(() => { // 
      loadReadRepliesData(bno);
   }, 1000);
   
   setTimeout(() => { // 
      loadRefreshData();
   }, 1500);
   
}
   
// 댓글 상세 정보
async function loadReadRepliesData(bno, rno) {
   const repliesListRE = $("#repliesListRE");
   
   $.getJSON("/replies/" + bno + "/all", function(result) {

      repliesListRE.empty();
      let str = "";
      
      const replyResult = result;
      console.log("replyResult : ", replyResult);

      for(var reply of result) {
         console.log(reply);
         
         str += `
                    <div class="readReply">
                        <div class="ct_wr_debox">  
                            <div class="ct_wrbox">  
                                <div class="commentwriterbox">  
                                    <div class="commentwriter" id="replyer${reply.rno}">
                                    	<strong><p name="memreplyer">${reply.replyer}</p></strong>
                                    </div>  
                                    <input type="hidden" id="replyContentRno" value="${reply.rno}">  
                                    <input type="hidden" id="replyContentBno" value="${reply.bno}">  
                                    <input type="hidden" id="memreplyer" value="${reply.replyer}">  
                                </div>  
                            </div>  
                            <div class="commentdetailbox">  
                                <div class="commentdetail replyContentBox" id="content${reply.rno}">${reply.content}</div>  
                                <input type="hidden" name="showReplyContent${reply.rno}" value="${reply.content}">  
                            </div>
                        </div>  
                        <div id="replyBtn">
                        	<button class="hideModifyBtn${reply.rno}" id="removeReplyBtn" onclick="removeReply(${reply.bno}, ${reply.rno})">댓글 삭제</button>  
                        	<button class="hideModifyBtn${reply.rno}" id="modifyReplyBtn" onclick="modifyReply(${reply.bno}, ${reply.rno})" value="${reply.rno}">댓글 수정</button>  
                        </div>
                        <hr>	
                    </div>	
         		   `;			
      }

      repliesListRE.html(str);
      $(".modifyReplyContent").hide();
      
      $.each(replyResult, function(idx, reply) {
	      hideModifyButton(reply.rno, reply.replyer);	
	  });
      
   });
   
}

// 댓글 수정 및 삭제 hide
function hideModifyButton(rno, replyer) {
	console.log(rno, replyer);
	
	// 로그인 유저와 댓글 작성자 비교
	if(memberNickname != replyer) {
		$(".hideModifyBtn" + rno).hide();
	}
}


// 댓글 등록
function loadRegisterReplyData(bno) {
   
   console.log("등록이 안되는 이유가 뭐야");
   
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
   
   $.ajax({
      url: '/replies/' + bno + "/" + rno,
      type: 'delete',
      dataType: 'json',
      data: JSON.stringify({
         bno: $('#boardBno').val(),
         rno: rno,
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

// '댓글수정' 버튼 함수
function modifyReply(bno, rno) {
	
	$("#content" + rno).html(
		`<div id="modifyContentDiv">
			<input type="text" id="modifyContentBox" placeholder="수정할 내용을 입력하세요.">
		 	<div id="contentBtn">
		 		<button type="button" id="modifyContentBtn" onclick="modifyContentBox(${rno})">수정하기</button>
		 		<button type="button" id="cancelContentBtn" onclick="cancelModify(${rno})">취소</button>
		 	</div>
		 </div>
		`);
}

// 댓글 수정 '취소' 버튼
function cancelModify(rno) {
	const showReplyContent = $("#showReplyContent" + rno).val();
	
	$("#content" + rno).html(showReplyContent);
}


// '수정하기' 버튼
function modifyContentBox(rno) {
   let bno = $("#replyContentBno").val();

   $.ajax({
      url: '/replies/' + rno,
      type: 'put',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8;',
      data: JSON.stringify({
         rno,
         replyer: $("#memReplyer").val(),
         content: $('#modifyContentBox').val()
      }),
      success: function(result) {
         loadReadData(bno);
      }
   })
   $("#modifyContentDiv").hide();
}

// HEART
$("#readList").on("click", ".heart", async ({target}) => {
    const $this = $(target);
    const {bno, hlno} = $this.data();
    const isOn = $this.hasClass("h-on"); // 내가 눌렀니?
    const data = { productNum: bno,  productType: "BOARD"};
    if (isOn) data.hlno = hlno;
    try {
        const {hlno: saveHlno} = await $.ajax({
            url: isOn ? "/heart/remove" : "/heart/save",
            method: isOn ? "delete" : "post",
            data: JSON.stringify(data),
            contentType: "application/json"
        });
        $this.removeClass(isOn ? "h-on" : "h-off").addClass(isOn ? "h-off" : "h-on");
        $this.data("hlno", saveHlno);
        const $count = $(".heart-count");
        $count.text(parseInt($count.text()) + (isOn ? -1 : 1));
    } catch (e) {
        console.error("좋아요 오류", e);
    }
});

// 하트 확인 메서드
function heartCheck(no) {
    return $.ajax({
        url: "/heart/getOne",
        contentType: "application/json",
        async: false,
        method: "POST",
        data: JSON.stringify({
            productNum: no,
            productType: "BOARD"
        }), dataType: "json"
    }).responseText
}