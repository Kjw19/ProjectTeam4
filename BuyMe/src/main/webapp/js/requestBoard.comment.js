// 문의게시판 - 댓글 기능
$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rowCount; // 한 페이지에 몇 개의 레코드를 보여줄 것인지
	
	// textarea에 댓글 입력 시 글자수 체크 : 작성·수정 시 사용
	$(document).on('keyup','textarea',function(){
		// 입력한 글자수를 구한다.
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ // 300자를 넘어선 경우 잘라내서 없애버린다.
			$(this).val($(this).val().substring(0,300));
		}else{ // 300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			
			if($(this).attr('id')=='comm_content'){
				// 등록폼 글자수
				$('#comm_first .letter-count').text(remain);
			}else{
				// 수정폼 글자수
				$('#mcomm_first .letter-count').text(remain);
			}
		}
	});
	
	// 댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#comm_first .letter-count').text('300/300');
	}
	// 댓글 등록
	$('#comm_form').submit(function(event){
		// 기본 이벤트 제거
		event.preventDefault();
		
		if($('#comm_content').val().trim()==''){
			alert('댓글이 작성되지 않았습니다.');
			$('#comm_content').val('').focus();
			return false;
		}
		
		// form 이하의 태그에 입력한 데이터를 모두 읽어 온다.
		let form_data = $(this).serialize(); // 파라미터 네임과 밸류의 한 쌍으로 가져온다.
		
		// 서버에 연결하여 댓글 등록
		$.ajax({
			url:'commentWrite.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 댓글을 작성할 수 있습니다.');
				}else if(param.result=='success'){
					alert('댓글을 작성하셨습니다.');
					initForm(); // 폼 초기화
					selectList(1); // 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫 번째 페이지의 게시글을 다시 호출한다.				
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); // 이클립스 콘솔 확인
			}
		});
	});
	
	// 댓글 목록
	function selectList(pageNum){ // selectList(value)
		currentPage = pageNum;
		
		// 로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'commentList.do',
			type:'post',
			data:{pageNum:pageNum,req_num:$('#req_num').val()}, // key:value
			dataType:'json',
			success:function(param){
				// 로딩 이미지 감추기(?)
				$('#loading').hide();
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum==1){
					// 처음 호출 시, 목록을 표시하는 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.id + '</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.comm_content + '</p>';
					
					// 로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if(param.user_num==item.mem_num){
						// 로그인한 회원번호와 작성자의 회원번호가 일치
						// data- : 속성을 만드는 접두사 (커스텀 데이터 속성) → 내가 만드는 속성
						output += ' <input type="button" data-commentnum="' + item.comment_num + '" value="수정" class="modify-btn">'
						output += ' <input type="button" data-commentnum="' + item.comment_num + '" value="삭제" class="delete-btn">'
					}
					
					output += '<hr size="1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					
					// 문서 객체에 추가
					$('#output').append(output);
				}); // end of each : 목록이 다 만들어 졌다.
				
				// Page Button 처리 : 소수점 자리가 없게끔 만든다.(Math.ceil())
				if(currentPage>=Math.ceil(count/rowCount)){
					// 다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					// 다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	// 초기 데이터(목록) 호출
	selectList(1);
	
	// 페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭 시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	// 댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mcomm_form').remove();
	}
	// 댓글 수정 버튼 클릭 시 수정폼 노출 : 동적으로 코드 만들기
	$(document).on('click','.modify-btn',function(){
		// 댓글 번호
		let comment_num = $(this).attr('data-commentnum');
		// 댓글 내용 : 문서내의 모든 br태그 검색
		// g : 지정문자열 모두, i : 대소문자 무시
		// 수정버튼.부모태그로이동.부모태그의하위p태그.(내용중에br이있다, \n으로변경)
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		
		// 댓글 수정 폼 UI : 동적으로 생성
		let modifyUI = '<form id="mcomm_form">';
		   modifyUI += '<input type="hidden" name="comment_num" id="comment_num" value="'+comment_num+'">';
		   modifyUI += '<textarea rows="4" cols="70" name="comm_content" id="mcomm_content" class="comm-content">'+content+'</textarea>';
		   modifyUI += '<div id="mcomm_second" class="align-right">';
		   modifyUI += ' <input type="submit" value="수정">';
		   modifyUI += ' <input type="button" value="취소" class="comm-reset">';
		   modifyUI += '</div>';
		   modifyUI += '<div id="mcomm_first"><span class="letter-count">300/300</span></div>';
		   modifyUI += '<hr size="1" noshade width="96%">';
		   modifyUI += '</form>';
		   
		   // 이전에 수정한 댓글이 있을 경우 수정버튼을 클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화시킨다.
		   initModifyForm();
		   
		   // 지금 클릭해서 수정하고자 하는 데이터는 감추기
		   // 수정버튼을 감싸고 있는 div : 수정버튼(this)의 부모(parent)를 감추기(hide)
		   // sub-item을 숨긴다.
		   $(this).parent().hide();
		   
		   // 수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		   $(this).parents('.item').append(modifyUI);
		   
		   // 입력한 글자수 셋팅
		   let inputLength = $('#mcomm_content').val().length;
		   let remain = 300 - inputLength;
		   remain += '/300';
		   
		   // 문서 객체에 반영
		   $('#mcomm_first .letter-count').text(remain);
	});
	// 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	$(document).on('click','.comm-reset',function(){
		initModifyForm();
	});
	// 댓글 수정
	$(document).on('submit','#mcomm_form',function(event){ // 미래($(document).on())의 태그에 동적으로 연결
		// 기본 이벤트 제거 : 주소가 바뀌면 안 되기 때문에
		event.preventDefault();
		
		if($('#mcomm_content').val().trim()==''){
			alert('댓글을 입력하세요.');
			$('#mcomm_content').val('').focus();
			return false;
		}
		
		// 폼에 입력한 데이터 반환 = serialize() : 폼 이하의 태그들을 한번에 읽어온다.
		// serialize() : parameter name과 value의 쌍으로 불러온다.
		// this : textarea를 감싸고 있는 form
		let form_data = $(this).serialize();
		
		// 서버와 통신
		$.ajax({
			url:'commentUpdate.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 댓글을 수정할 수 있습니다.');
				}else if(param.result=='success'){ // 화면 제어
					$('#mcomm_form').parent().find('p').html($('#mcomm_content').val().replace(/</g,'&lt;')
																				  .replace(/>/g,'&gt;')
																				  .replace(/\n/g,'<br>'));
					$('#mcomm_form').parent().find('.modify-date').text('최근 수정일 : 5초미만'); // 최근 수정일 화면에 표시
					initModifyForm(); // 수정폼 삭제 및 초기화 → 바로 화면 갱신
				}else if(param.result=='wrongAccess'){
					alert('타인의 댓글을 수정할 수 없습니다.');
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}); 
	
	// 댓글 삭제
	$(document).on('click','.delete-btn',function(){
		// 댓글 번호
		let comment_num = $(this).attr('data-commentnum');
		
		// 서버와 통신
		$.ajax({
			url:'commentDelete.do',
			type:'post',
			data:{comment_num:comment_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 댓글을 삭제할 수 있습니다.');
				}else if(param.result=='success'){
					alert('댓글 삭제 완료!');
					selectList(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 댓글은 삭제할 수 없습니다.');
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); // 이클립스 콘솔 확인
			}
		});
	});
});