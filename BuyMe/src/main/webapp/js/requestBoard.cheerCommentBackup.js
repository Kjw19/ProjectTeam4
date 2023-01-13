// 문의게시판 - 타임라인
$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rowCount; // 한 페이지에 몇 개의 레코드를 보여줄 것인지
	let my_photo;
	
	// textarea에 타임라인 입력 시 글자수 체크 : 작성·수정 시 사용
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
	
	//타임라인 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#cheerComm_first .letter-count').text('300/300');
	}
	
	function initPhoto(){
		$('.my-photo').attr('src','../images/blank.png');
		$('#photo').val('');
	}	
	
	$(function(){
		// 이미지 미리보기
		let photo_path = $('.my-photo').attr('src'); // 처음 화면에 보여지는 이미지 읽기
		
		$('#photo').change(function(){
			my_photo = this.files[0];
			
			/*
			if(!my_photo){
				$('.my-photo').attr('src',photo_path);
				return; // 선택한 이미지가 없으니 다시 선택하게끔
			}
			*/
			
			
			// 파일 용량 체크
			if(my_photo.size>1024*1024){
				alert(Math.round(my_photo.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
				$('.my-photo').attr('src',photo_path); // 용량이 너무 크면 처음 이미지를 보여준다.
				$(this).val(''); // 선택한 파일 정보 지우기
			}
			
			
			let reader = new FileReader();
			reader.readAsDataURL(my_photo);
			
			reader.onload = function(){
				$('.my-photo').attr('src',reader.result);
			};
		}); // end of change
		
		// 타임라인 전송
		$('#cheer_btn').click(function(event){
			event.preventDefault(); // 기본 이벤트 제거
			
			if($('#cheerComm_title').val().trim()==''){
				$('#cheerComm_title').val('').focus();
				alert('타임라인 제목을 알려주세요!');
				return;
			}
			
			if($('#cheerComm_content').val().trim()==''){
				$('#cheerComm_content').val('').focus();
				alert('타임라인을 아직 작성하지 않으셨습니다!');
				return;
			}
			
			// 서버에 파일 전송
			let form_data = new FormData();
			
			
			form_data.append('cheer_num',$('#cheerComment_num').val());
			form_data.append('cheerComm_title',$('#cheerComm_title').val());
			form_data.append('cheerComm_content',$('#cheerComm_content').val());
			if(my_photo){
				form_data.append('cheerComm_filename',my_photo);
			}
			
			$.ajax({
				url:'cheerCommentWrite.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false, // 데이터 객체를 문자열로 바꿀지에 대한 설정 : true면 일반문자, false면 file
				processData:false, // 해당 타입을 true로 하면 일반 text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result=='logout'){
						alert('로그인 후 타임라인을 작성하실 수 있습니다!');
					}else if(param.result=='success'){
						alert('타임라인을 꾸며주셔서 감사합니다!');
						initForm();
						selectList(1);
						initPhoto();
					}else{
						alert('타임라인 작성 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		}); // end of click_submit (파일 전송)
		
		// 이미지 미리보기 취소
		$('#photo_reset').click(function(){
			$('.my-photo').attr('src',photo_path); // 초기 이미지 표시
			$('.my-photo').val('');
		}); // end of click_reset (이미지 미리보기 취소)
	});
	
	// 타임라인 목록
	function selectList(pageNum){ // selectList(value)
		currentPage = pageNum;
		
		// 로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'cheerCommentList.do',
			type:'post',
			data:{pageNum:pageNum,cheer_num:$('#cheerComment_num').val()}, // key:value
			dataType:'json',
			success:function(param){
				// 로딩 이미지 감추기(?)
				$('#loading').hide();
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum==1){
					$('#output').empty(); // 처음 호출 시, 목록을 표시하는 div의 내부 내용물을 제거
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<b>' + item.cheerComm_title + '</b><br>';
					output += '<span> 작성자 ' + item.id + '(' + item.cheerComm_reg_date + ')</span><br>';
					output += '<br><div class="sub-item">';
					//output += '<span><img src="../upload/'+item.cheerComm_filename+'" width="200" height="150"></span>';
					
					if(item.cheerComm_filename!=null){
						output += '<span><img src="../upload/'+item.cheerComm_filename+'" width="200" height="150"></span>';
					}else{
						output += '<span></span>';
					}
					
					output += '<p>' + item.cheerComm_content + '</p>';
					
					
					// 로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if(param.user_num==item.mem_num){
						//output += ' <input type="button" data-cheerCommentnum="' + item.cheerComment_num + '" value="수정" class="modify-btn">'
						output += ' <input type="button" data-cheerCommentnum="' + item.cheerComment_num + '" value="삭제" class="delete-btn">'
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
	//초기 데이터(목록) 호출
	selectList(1);
	// 페이지 처리 이벤트 연결(다음 타임라인 보기 버튼 클릭 시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	// 타임라인 삭제
	$(document).on('click','.delete-btn',function(){
		// 타임라인 번호
		let cheerComment_num = $(this).attr('data-cheerCommentnum');
		
		// 서버와 통신
		$.ajax({
			url:'cheerCommentDelete.do',
			type:'post',
			data:{cheerComment_num:cheerComment_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 타임라인을 지울 수 있습니다.');
				}else if(param.result=='success'){
					alert('타임라인 삭제 완료!');
					selectList(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 타임라인은 지울 수 없습니다.');
				}else{
					alert('타임라인 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); // 이클립스 콘솔 확인
			}
		});
	});
});