$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//댓글 목록
	function selectList(pageNum) {
		currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listComment.do',
			type:'post',
			data:{pageNum:pageNum,notice_num:$('#notice_num').val()},
			dataType:'json',
			success: function(param) {
				//로딩 이미지 감추기
				$('loading').hide();
				count = param.count;
				rowCount = param.rowCount;

				if (pageNum == 1) {
					//처음 호출시 목록을 표시하는 div의 내부 내용을 제거
					$('#output').empty();
				}
				$(param.list).each(function(index, item) {
					let output = '<div class = "item">';
					output += '<h4>' + item.id + '</h4>';
					output += '<div class = "sub-item">';
					output += '<p>' + item.comm_content + '</p>';
					output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					
					//로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if (param.user_num == item.mem_num) {
						//로그인한 회원번호와 작성자 회원번호 일치
						output += ' <input type="button" data-commnum="' + item.commnet_num + '" value="수정" class="modify-btn">';
						output += ' <input type="button" data-commnum="' + item.commnet_num + '" value="삭제" class="delete-btn">';
					}
					output += '<hr size="1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					//문서 객체에 추가 
					$('#output').append(output);
				}); //end of each

				//page button 처리
				if (currentPage >= Math.ceil(count/rowCount)) {
					//다음 페이지 없음
					$('.paging-button').hide();
				}
				else {
					//다음 페이지 존재	
					$('paging-button').show();
				}
			},
			error: function() {
				$('loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	//댓글 등록
	$('#comm_form').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#comm_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#comm_content').val('').focus();
			return false;
		}
	
	
	//form 이하의 태그에 입력한 모든 데이터를 읽어옴			
	let form_data = $(this).serialize();
		

	//댓글 등록
	$.ajax({
			url:'writeComment.do',
			type:'post',
			data:form_data,
			dataType:'json',
		success:function(param){
			if (param.result == 'logout') {
				alert('로그인해야 작성할 수 있습니다.');
			}
			else if (param.result == 'success') {
				initForm(); //폼 초기화
				//댓글 작성 성공 시 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출
				selectList(1);
			}
			else {
				alert('댓글 등록 오류 발생');
			}
		},
		 error:function(request,status,error){
        alert("\n"+"error:"+error);
       }
	});
		});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#comm_first .letter-count').text('300/300');
	}
	//textarea에 내용 입력 시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300) {
			$(this).val($(this).val().substring(0,300));
		}
		else {
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 'comm_content') {
				//등록폼 글자 수 
				$('#comm_first .letter-count').text(remain);
			}
			else {
				//수정폼 글자 수
				$('#mcomm_first .letter-count').text(remain);
			}
		}
	});
	
	//댓글 수정 버튼 클릭시 수정폼 노출 
	$(document).on('click', 'modify-btn', function(){
		//댓글 번호
		let comment_num = $(this).attr('data-commnum');
		
		//댓글 내용                
		let comment_content = $(this).parent().find('p')
		                     .html().replace(/<br>/gi,'\n');                            
			
		//댓글 수정폼 UI
		let modifyUI = '<form id="mcomm_form">'; 
		   modifyUI += '<input type="hidden" name="comment_num" id="comment_num" value="'+comment_num+'">';
		   modifyUI += '<textarea rows="3" cols="50" name="comm_content" id="mcomm_content" class="comm-content">'+comment_content+'</textarea>';
		   modifyUI += '<div id="mcomm_first"><span class="letter-count">300/300</span></div>';
		   modifyUI += '<div id="comm_second" class="align-right">';
		   modifyUI += ' <input type="submit" value="수정">';
		   modifyUI += ' <input type="button" value="취소" class="re-reset">';
		   modifyUI += '</div>';
		   modifyUI += '<hr size="1" noshade width="96%">';
		   modifyUI += '</form>';
		 
		   //이전에 이미 수정하는 댓글이 있을 경우 수정버튼을
		   //클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화됨
		   initModifyForm();
		   
		   //지금 클릭해서 수정하고자 하는 데이터는 감추기
		   //수정버튼을 감싸고 있는 div
		   $(this).parent().hide();
		   
		   //수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		   $(this).parents('.item').append(modifyUI); 
		   
		   //입력한 글자수 셋팅
		   let inputLength = $('#mcomm_content').val().length;
		   let remain = 300 - inputLength;
		   remain += '/300';
		   
		   //문서 객체에 반영
		   $('#mcomm_first .letter-count').text(remain);
	});
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화 
	$(document).on('click', 'comm-rest', function(){
		initModifyForm();
	});
	
	
	//댓글 수정폼 초기화 
	function initModifyForm(){
		$('.sub-item').show();
		$('#mcomm_form').remove();
	}
	
	
	//댓글 수정
	$(document).on('submit', '#mcomm_form', function(event) {
		//기본 이벤트 제거
		event.preventDefault();

		if ($('#mcomm_content').val().trim() == '') {
			alert('내용을 입력하세요.');
			$('#mcomm_content').val('').focus();
			return false;
		}

		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();

		$.ajax({
			url: 'updateComment.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인해야 수정할 수 있습니다.');
				}
				else if (param.result == 'success') {
					$('#mcomm_form').parent().find('p').html($('#mcomm_content').val()
						.replace(/</g, '&lt;')
						.replace(/</g, '&gt;')
						.replace(/\n/g, '&<br>'));
			
					initModifyForm();
				}
				else if (param.result == 'wrongAccess') {
					alert('타인의 글은 수정할 수 없습니다.');
				}
				else {
					alert('댓글 수정 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
	//댓글 삭제
	$(document).on('click', 'delete-btn', function() {
		let comment_num = $(this).attr('data-commnum');

		//서버와 통신
		$.ajax({
			url:'deleteComment.do',
			type:'post',
			data:{comment_num:comment_num},
			dataType:'json',
			success:function(param) {
				if (param.result == 'logout') {
					alert('로그인해야 삭제할 수 있습니다.');
				}
				else if (param.result == 'success') {
					alert('삭제 완료');
					selectList(1);
				}
				else if (param.result == 'wrongAccess') {
					alert('타인의 글은 삭제할 수 없습니다.');
				}
				else {
					alert('네트워크 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}

		});

	});
		//초기 데이터(목록) 호츌
		selectList(1);

});