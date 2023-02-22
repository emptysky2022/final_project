$(function(){
	console.log("document ready test")
	
	loadRefreshData();
	
	$("#modifyReply").hide();
	
	// 다영 - form태그 안에 submit버튼이랑 input에서 엔터를 누르면 실행되는 이벤트
	$("#searchForm").on("submit", (e) => {
		// form action으로 페이지 이동이 되지 않게 submit default 이벤트를 삭제시킴
		e.preventDefault(); 
		// 게시글 검색으로 넘어감
		loadRefreshData();
	});
});

-------------------------------------------------------------------------------------------------------------------------

// 게시글 새로고침하기(실질적 새로고침 아님.) --> 다영 - 새로고침되는거 위에ㅔ서 고침
// async: 비동기 함수로 선언, await을 사용하기 위해 필수로 적어야함
// await : callback 함수(success, error)등을 사용하지 않고, 비동기 함수가 완료될때까지 기다렸다가 결과값을 변수에 넣어줌
async function loadRefreshData() {
	
   // 검색 키워드, 타입
   const $searchForm = $("#searchForm"); // jquery element 객체는 변수 앞에 $를 붙이는게 국룰임
   const keyword = $searchForm.find("[name='keyword']").val();
   const type = $searchForm.find("[name='type']").val();
   
   // 다른 페이지 갔다 왔을 때를 대비해서 page, keyword, type은 querystring으로 남겨놔야함 (되게 해야함..)
   urlParams.set("page", page);
   urlParams.set("keyword", keyword);
   urlParams.set("type", type);
   
   // 오류 발생하면 catch 실행 (await 실패 시 오류처리 하기위해 꼭 써야함, 또는 다른 오류 났을 때도 많이 씀)
   try {
		const result = await $.get("/sample/list/refresh", {page, keyword, type});
		const boardList = result[0].dtoList;
		const $board = $(".refresh_list");
		
		// Array.map은 배열 데이터를 변환해서 새로운 배열로 만들 때 사용함, 많이 쓰니 자주 써보도록
		// .map({bno, title..}) 과 같이 board 안에 데이터를 중괄호로 바로 꺼내는 방식을 비구조화 할당이라고 함, 엄청 많이 쓰이니 공부 필요
		// ` BackTick(억음부호)로 사용 시 변수를 바로 사용할 수 있어 많이 사용함
		// html += '<td>...' 방식은 사용 지양해야함
		const html = boardList.map(({bno, title, regDate, nickname, replyCount, count}) =>
			`<tr>
				<td scope=row>${bno}</td>
				<td><a onclick="loadReadData(${bno})">${title}<b><small>[${replyCount}]</small></b></a></td>
				<td>${nickname}</td>
				<td>${moment(regDate).format('YYYY/MM/DD HH:mm:ss')}</td>
				<td>${count}</td>
			 </tr>`
		).join("");
		
		$board.html(html);
	} catch (e) {
		console.error("게시판 불러오는 중 오류 발생", e);
		alert(e);
	}
}