
//별명중복 체크 userNickNameCheck() 
//아이디 중복체크 userIdChecked(),id=userIdCheck
function checkId(){
	console.log("유저 아이디 중복 체크");
	var id = $('#id').val();
	
	$.ajax({
		type: "post",
		url : "./idCheck",
		data : {id : id},
		
		success: function(cnt){
			if(cnt == 0){
				
					$(".id_ok").css("display","inline-block");
					$(".id_already").css("display","none");
					
			}else {//cnt가 1인경우는 이미 존재하는 아이디
					$(".id_ok").css("display","none");
					$(".id_already").css("display","inline-block");

					}
		},
		error: function(){
			alert("ajax 실행 실패");
		}
		
	});
};

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
		
    $("#passcheck").keyup(function(){
        var passcheck=$(this).val();
        var pw=$("#pw").val();
        // 비밀번호 같은지 확인
        if(passcheck==pw){//비밀번호 같다면
            $("#rePwdErr").hide();
            successState("#passcheck");
        }else{//비밀번호 다르다면
            $("#rePwdErr").show();
            errorState("#passcheck");
        }
    });
        
        
        
        
        
        
        
        
        
