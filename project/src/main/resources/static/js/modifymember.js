function deleteMember()  {
    
    if(confirm("정말로 회원탈퇴 하시겠습니까?")==true){
		location.href="/memberDelete";
	}else
	return false;
}
  
  function checkNick(){
	console.log("유저 닉네임 중복 체크");
	var nickname= $('#nickname').val();
	$.ajax({
		type:"post",
		url:"./nickCheck",
		data :{nickname:nickname},
		
		success:function(nnt){
			
			if(nnt==0){
				$(".nick_ok").css("display","inline-block");
				$(".nick_already").css("display","none");
			}else{
				$(".nick_ok").css("display","none");
				$(".nick_already").css("display","inline-block");
			}
		},
		error:function(){
			alert("ajax 실행 실패");
		}
	});
};

function submitmessage(){
	alert("회원 정보 수정이 완료되었습니다. 다시 로그인 부탁드립니다!!")
}


