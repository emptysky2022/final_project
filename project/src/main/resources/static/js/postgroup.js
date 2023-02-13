/*input file타입의 이미지 파일만 올리게 하기 위해 사용*/
function chk_file_type(obj) {
    var file_kind = obj.value.lastIndexOf('.');
    var file_name = obj.value.substring(file_kind+1,obj.length);
    var file_type = file_name.toLowerCase();
   
   
   
    var check_file_type=new Array();​
   
    check_file_type=['jpg','gif','png','jpeg','bmp'];
   
   
   
    if(check_file_type.indexOf(file_type)==-1){
     alert('이미지 파일만 선택할 수 있습니다.');
     var parent_Obj=obj.parentNode
     var node=parent_Obj.replaceChild(obj.cloneNode(true),obj);
     return false;
    }
   }
   
   