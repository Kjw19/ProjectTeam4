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
	//댓글 수정
	$(document).on('submit', '#mre_form', function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#mre_content').val().trim()=='') {
			alert('내용을 입력하세요.');
			$('#mre_content').val('').focus();
			return false;
		}
		let form_data = $(this).serialize();
		
		$.ajax({
			url:'updateComment.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout') {
					alert('로그인해야 수정할 수 있습니다.');
				}
				else if(param.result == 'success') {
					$('#mre_form').parent().find('p').html($('#mre_content').val()
					.replace(/</g,'&lt;')
					.replace(/</g,'&gt;')
					.replace(/|n/g,'&<br>'));
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만');
					initModifyForm();
				}
				else if(param.result == 'wrongAccess') {
					alert('타인의 글은 수정할 수 없습니다.');
				}
				else {
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		});
});