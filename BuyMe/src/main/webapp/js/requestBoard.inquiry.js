// 문의게시판 - 문의 기능
$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rowCount; // 한 페이지에 몇 개의 레코드를 보여줄 것인지
	
	// 초기 데이터(목록) 호출
	selectList(1);
	
	// textarea에 문의 입력 시 글자수 체크 : 작성·수정 시 사용
	$(document).on('keyup','textarea',function(){
		// 입력한 글자수를 구한다.
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ // 300자를 넘어선 경우 잘라내서 없애버린다.
			$(this).val($(this).val().substring(0,300));
		}else{ // 300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			
			if($(this).attr('id')=='inqu_content'){
				// 등록폼 글자수
				$('#inqu_first .letter-count').text(remain);
			}else{
				// 수정폼 글자수
				$('#minqu_first .letter-count').text(remain);
			}
		}
	});
	// 문의 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#inqu_first .letter-count').text('300/300');
	}
	// 문의 등록
	$('#inqu_form').submit(function(event){
		// 기본 이벤트 제거
		event.preventDefault();
		
		if($('#inqu_content').val().trim()==''){
			alert('문의를 작성하지 않으셨습니다.');
			$('#inqu_content').val('').focus();
			return false;
		}
		
		// form 이하의 태그에 입력한 데이터를 모두 읽어 온다. : 파라미터 네임과 밸류의 한 쌍으로 가져온다.
		let form_data = $(this).serialize();
		
		// 서버에 연결하여 등록
		$.ajax({
			url:'inquiryWrite.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 문의를 남기실 수 있습니다.');
				}else if(param.result=='success'){
					initForm(); // 폼 초기화
					selectList(1); // 문의 등록이 성공하면 새로 삽입한 글을 포함해서 첫 번째 페이지의 게시글을 다시 호출한다.
				}else{
					alert('문의 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); // 이클립스 콘솔 확인
			}
		});
	});
	
	// 문의 목록
	function selectList(pageNum){ // selectList(value)
		currentPage = pageNum;
		
		// 로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'inquiryList.do',
			type:'post',
			data:{pageNum:pageNum,req_num:$('#req_num').val()}, // key:value
			dataType:'json',
			success:function(param){
				// 로딩 이미지 감추기(?)
				//$('#loading').hide();
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
					output += '<p>' + item.inqu_content + '</p>';
					
					// 로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if(param.user_num==item.mem_num){
						// 로그인한 회원번호와 작성자의 회원번호가 일치
						// data- : 속성을 만드는 접두사 (커스텀 데이터 속성) → 내가 만드는 속성
						output += ' <input type="button" data-renum="' + item.inquiry_num + '" value="수정" class="modify-btn">'
						output += ' <input type="button" data-renum="' + item.inquiry_num + '" value="삭제" class="delete-btn">'
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
	// 페이지 처리 이벤트 연결(다음 문의 보기 버튼 클릭 시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	// 문의 수정 버튼 클릭 시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		// 문의 번호
		let inquiry_num = $(this).attr('data-inquirynum');
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		
		// 댓글 수정 폼 UI : 동적으로 생성
		let modifyUI = '<form id="minqu_form">';
		   modifyUI += '<input type="hidden" name="inquiry_num" id="inquiry_num" value="'+inquiry_num+'">';
		   modifyUI += '<textarea rows="3" cols="50" name="inqu_content" id="minqu_content" class="inqu-content">'+content+'</textarea>';
		   modifyUI += '<div id="minqu_first"><span class="letter-count">300/300</span></div>';
		   modifyUI += '<div id="minqu_second" class="align-right">';
		   modifyUI += ' <input type="submit" value="수정">';
		   modifyUI += ' <input type="button" value="취소" class="inqu-reset">';
		   modifyUI += '</div>';
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
		   let inputLength = $('#minqu_content').val().length;
		   let remain = 300 - inputLength;
		   remain += '/300';
		   
		   // 문서 객체에 반영
		   $('#minqu_first .letter-count').text(remain);
	});
	// 문의 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#minqu_form').remove();
	}
	// 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	$(document).on('click','.inqu-reset',function(){
		initModifyForm();
	});
	
	// 문의 수정
	$(document).on('submit','#minqu_form',function(event){
		event.preventDefault();
		
		if($('#minqu_content').val().trim()==''){
			alert('문의를 작성하세요.');
			$('#minqu_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		
		// 서버와 통신
		$.ajax({
			url:'inquiryUpdate.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 문의를 수정할 수 있습니다.');
				}else if(param.result=='success'){
					$('#minqu_form').parent().find('p').html($('#minqu_content').val().replace(/</g,'&lt;')
																					  .replace(/>/g,'&gt;')
																					  .replace(/\n/g,'<br>'));
					initModifyForm();
				}else if(param.result=='wrongAccess'){
					alert('타인의 문의를 수정할 수 없습니다.');
				}else{
					alert('문의 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	// 문의 삭제
	$(document).on('click','.delete-btn',function(){
		// 문의 번호
		let inquiry_num = $(this).attr('data-inquirynum');
		
		$.ajax({
			url:'inquiryDelete.do',
			type:'post',
			data:{inquiry_num:inquiry_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 문의를 삭제할 수 있습니다.');
				}else if(param.result=='success'){
					alert('문의 삭제 완료!');
					selectList(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 문의는 삭제할 수 없습니다.');
				}else{
					alert('문의 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});	
});