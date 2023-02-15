$(function(){
	function loadJsonData(){
		let bno = new URLSearchParams(window.location.search).get("bno");
		let boardListRE = $("#boardListRE");
		$.getJSON("/sample/read/" + bno, function(result){
			console.log(result);
			let str = "";
			
			if()
			
			boardListRE.html(str);
			console.log("완")
		})
	}
})