$(function() {
	let currnetPage;
	let count;
	let rowCount;

	//댓글 등록
	$.ajax({
		url: 'writeComment.do',
		type: 'post',
		data: form_data,
		dataType: 'json',
		success: function(param) {
			if (param.result == 'logout') {
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
		error: function() {
			alert('네트워크 오류 발생');
		}
	});
	//댓글 수정
	$(document).on('submit', '#mre_form', function(event) {
		//기본 이벤트 제거
		event.preventDefault();

		if ($('#mre_content').val().trim() == '') {
			alert('내용을 입력하세요.');
			$('#mre_content').val('').focus();
			return false;
		}
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
					$('#mre_form').parent().find('p').html($('#mre_content').val()
						.replace(/</g, '&lt;')
						.replace(/</g, '&gt;')
						.replace(/|n/g, '&<br>'));
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만');
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
	//댓글 목록
	function selectList(pageNum) {
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();

		$.ajax({
			url: 'listComment.do',
			type: 'post',
			data: { pageNum: pageNum, notice_num: $('#board_num').val() },
			dataType: 'json',
			success: function(param) {
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
					output += '<p>' + item.re_count + '</p>';

					if (item.re_modifydate) {
						output += '<span class="modify-date">최근 수정일 : ' + item.re_modifydate + '</span>';
					} else {
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					//로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if (param.user_num == item.mem_num) {
						//로그인한 회원번호와 작성자 회원번호 일치
						output += ' <input type="button" data-renum="' + item.re_num + '" value="수정" class="modify-btn">';
						output += ' <input type="button" data-renum="' + item.re_num + '" value="삭제" class="delete-btn">';
					}
					output += '<hr size="1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					//문서 객체에 추가 
					$('#output').append(output);
				}); //end of each

				//page button 처리
				if (currentPage >= Math.ceil(count / rowCount)) {
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
	//댓글 삭제
	$(document).on('click', 'delete-btn', function() {
		let notice_num = $(this).attr('data-renum');

		//서버와 통신
		$.ajax({
			url: 'deleteComment.do',
			type: 'post',
			data: { notice_num: re_num },
			dataType: 'json',
			success: function(param) {
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