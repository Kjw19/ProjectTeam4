// 문의게시판 - 이야기 한마당
$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rowCount; // 한 페이지에 몇 개의 레코드를 보여줄 것인지
	
	// textarea에 이야기 입력 시 글자수 체크 : 작성·수정 시 사용
	$(document).on('keyup','textarea',function(){
		// 입력한 글자수를 구한다.
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ // 300자를 넘어선 경우 잘라내서 없애버린다.
			$(this).val($(this).val().substring(0,300));
		}else{ // 300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			
			if($(this).attr('id')=='cheerComm_content'){
				// 등록폼 글자수
				$('#cheerComm_first .letter-count').text(remain);
			}else{
				// 수정폼 글자수
				$('#mcheerComm_first .letter-count').text(remain);
			}
		}
	});
	
	// 이야기 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#cheerComm_first .letter-count').text('300/300');
	}
	// 이야기 등록
	$('#cheerComm_form').submit(function(event){
		// 기본 이벤트 제거
		event.preventDefault();
		
		if($('#cheerComm_content').val().trim()==''){
			alert('아직 이야기를 들려주시지 않았어요!');
			$('#cheerComm_content').val('').focus();
			return false;
		}
		
		// form 이하의 태그에 입력한 데이터를 모두 읽어 온다.
		let form_data = $(this).serialize(); // 파라미터 네임과 밸류의 한 쌍으로 가져온다.
		
		// 서버에 연결하여 이야기 작성
		$.ajax({
			url:'cheerCommentWrite.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 이야기를 나눌 수 있습니다.');
				}else if(param.result=='success'){
					alert('이야기를 작성하셨습니다.');
					initForm(); // 폼 초기화
					//selectList(1); // 이야기 작성이 성공하면 새로 삽입한 글을 포함해서 첫 번째 페이지의 게시글을 다시 호출한다.				
				}else{
					alert('이야기 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); // 이클립스 콘솔 확인
			}
		});
	});
});