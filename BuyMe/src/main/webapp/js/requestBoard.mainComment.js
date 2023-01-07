$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rewCount; // 한 페이지에 몇 개의 레코드를 보여줄 것이냐
	
	// 초기 데이터(목록) 호출
	//selectMainList(1);
	
	// textarea에 내용 입력 시 글자수 체크 : 등록·수정 시 사용
	$(document).on('keyup','textarea',function(){ // $(document).on() : 미래의 태그에 연결
		// 입력한 글자수를 구한다.
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ // 300자를 넘어선 경우 잘라내서 없애버린다.
			$(this).val($(this).val().substring(0,300));
		}else{ // 300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			// 변수 (attr(id))처럼 들어가면 안되고, 문자열(attr('id'))로 들어가야 한다.
			if($(this).attr('id')=='mainComm_content'){
				// 등록폼 글자수
				$('#mainComm_first .letter-count').text(remain);
			}else{
				// 수정폼 글자수
				$('#mmainComm_first .letter-count').text(remain);
			}
		}
	});
	
	// 댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('mainComm_first .letter-count').text('300/300');
	}
	
	// 댓글 등록
	$('#mainComm_form').submit(function(event){
		// 기본 이벤트 제거
		event.preventDefault();
		
		if($('#mainComm_content').val().trim()==''){
			alert('응원 댓글 한 마디만 부탁드려요!');
			$('#mainComm_content').val('').focus();
			return false;
		}
		
		// form 이하의 태그에 입력한 데이터를 모두 읽어 온다.
		let form_data = $(this).serialize(); // 파라미터 네임과 밸류의 한 쌍으로 가져온다.
		
		// 서버에 연결하여 댓글 등록
		$.ajax({
			url:'mainCommentwrite.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					initForm(); // 폼초기화
					//selectMainList(1); // 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫 번째 페이지의 게시글을 다시 호출한다.
				}
			},
			error:function(){
				alert('네트워크 오류 발생하지만 일단 작성이 되긴 한다. 로그인 체크 후 다시 보기.'); // 이클립스 콘솔 확인
			}
		});
	});
	
	
	
	
	
	
	
	
	
});