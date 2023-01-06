$(function(){
	let currnetPage;
	let count;
	let rowCount;
	
	//댓글 등록
	$.ajax({
		url:'writeComment.do',
		type:'post',
		data:form_data,
		dataType:'json',
		success:function(param){
			if(param.result == 'logout'){
				alert('로그인해야 작성할 수 있습니다.');
			}
			else if (param.result == 'success') {
				initform(); //폼 초기화
				//댓글 작성 성공 시 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출
				selectList(1);
			}
			else {
				alert('댓글 등록 오류 발생');
			}
		},
		error:function(){
			alert('네트워크 오류 발생');
		}
	});
});