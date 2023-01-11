// 문의게시판 - 타임라인
$(function(){
	// 목록 작업 시 필요한 변수들
	let currentPage;
	let count;
	let rowCount; // 한 페이지에 몇 개의 레코드를 보여줄 것인지
	
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
		$('#cheer_btn').show();
		$('textarea').val('');
		$('#cheerComm_first .letter-count').text('300/300');
	}
	
	$(function(){
		// 이미지 미리보기
		let photo_path = $('.my-photo').attr('src'); // 처음 화면에 보여지는 이미지 읽기
		
		$('#photo').change(function(){
			my_photo = this.files[0];
			
			if(!my_photo){
				$('.my-photo').attr('src',photo_path);
				return; // 선택한 이미지가 없으니 다시 선택하게끔
			}
			
			
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
			form_data.append('cheerComm_filename',my_photo);
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
						// 이미지 초기화
						$('$cheerComm_filename').val('');
						alert('이미지 초기화 완료');
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
			$('#cheer_btn').show();
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
	
	/*
	// 타임라인 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mcheerComm_form').remove();
	}
	// 타임라인 수정 버튼 클릭 시 수정폼 노출 : 동적으로 코드 만들기
	$(document).on('click','.modify-btn',function(){
		// 타임라인 번호
		let cheerComment_num = $(this).attr('data-cheerCommentnum');
		// 타임라인 내용 : 문서내의 모든 br태그 검색
		// g : 지정문자열 모두, i : 대소문자 무시
		// 수정버튼.부모태그로이동.부모태그의하위p태그.(내용중에br이있다, \n으로변경)
		let cheerComm_title = $(this).parent().find('b');
		let cheerComm_content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		
		// 타임라인 수정 폼 UI : 동적으로 생성
		let modifyUI = '<form id="mcheerComm_form">';
		   modifyUI += '<input type="hidden" name="cheerComment_num" id="cheerComment_num" value="'+cheerComment_num+'">';
		   modifyUI += '<textarea rows="1" cols="70" name="cheerComm_title" id="mcheerComm_title" class="cheerComm-title">'+cheerComm_title+'</textarea>';
		   modifyUI += '<textarea rows="4" cols="70" name="cheerComm_content" id="mcheerComm_content" class="cheerComm-content">'+cheerComm_content+'</textarea>';
		   modifyUI += '<div id="mcheerComm_second" class="align-right">';
		   modifyUI += ' <input type="submit" value="수정">';
		   modifyUI += ' <input type="button" value="취소" class="cheerComm-reset">';
		   modifyUI += '</div>';
		   modifyUI += '<div id="mcheerComm_first"><span class="letter-count">300/300</span></div>';
		   modifyUI += '<hr size="1" noshade width="96%">';
		   modifyUI += '</form>';
		   
		   // 이전에 수정한 타임라인이 있을 경우 수정버튼을 클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화시킨다.
		   initModifyForm();
		   
		   // 지금 클릭해서 수정하고자 하는 데이터는 감추기
		   // 수정버튼을 감싸고 있는 div : 수정버튼(this)의 부모(parent)를 감추기(hide)
		   // sub-item을 숨긴다.
		   $(this).parent().hide();
		   
		   // 수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		   $(this).parents('.item').append(modifyUI);
		   
		   // 입력한 글자수 셋팅
		   let inputLength = $('#mcheerComm_content').val().length;
		   let remain = 300 - inputLength;
		   remain += '/300';
		   
		   // 문서 객체에 반영
		   $('#mcheerComm_first .letter-count').text(remain);
	});
	// 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	$(document).on('click','.cheerComm-reset',function(){
		initModifyForm();
	});
	// 타임라인 수정
	$(document).on('submit','#mcheerComm_form',function(event){ // 미래($(document).on())의 태그에 동적으로 연결
		// 기본 이벤트 제거 : 주소가 바뀌면 안 되기 때문에
		event.preventDefault();
		
		if($('#mcheerComm_title').val().trim()==''){
			alert('Talk 제목을 작성하세요.');
			$('#mcheerComm_title').val('').focus();
			return false;
		}
		if($('#mcheerComm_content').val().trim()==''){
			alert('Talk 내용을 작성하세요.');
			$('#mcheerComm_content').val('').focus();
			return false;
		}
		
		// 폼에 입력한 데이터 반환 = serialize() : 폼 이하의 태그들을 한번에 읽어온다.
		// serialize() : parameter name과 value의 쌍으로 불러온다.
		// this : textarea를 감싸고 있는 form
		let form_data = $(this).serialize();
		
		// 서버와 통신
		$.ajax({
			url:'cheerCommentUpdate.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 타임라인을 수정할 수 있습니다.');
				}else if(param.result=='success'){ // 화면 제어
					$('#mcheerComm_form').parent().find('b').html($('#mcheerComm_title').val().replace(/</g,'&lt;')
																						   	  .replace(/>/g,'&gt;')
																						  	   .replace(/\n/g,'<br>'));
					$('#mcheerComm_form').parent().find('p').html($('#mcheerComm_content').val().replace(/</g,'&lt;')
																						   	 	.replace(/>/g,'&gt;')
																						  	    .replace(/\n/g,'<br>'));
					initModifyForm(); // 수정폼 삭제 및 초기화 → 바로 화면 갱신
				}else if(param.result=='wrongAccess'){
					alert('타인의 타임라인을 수정할 수 없습니다.');
				}else{
					alert('타임라인 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}); 
	*/
	/*
	// 타임라인 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mcheerComm_form').remove();
	}
	// 타임라인 수정 버튼 클릭 시 수정폼 노출 : 동적으로 코드 만들기
	$(document).on('click','.modify-btn',function(){
		// 타임라인 번호
		let cheerComment_num = $(this).attr('data-cheerCommentnum');
		// 타임라인 내용 : 문서내의 모든 br태그 검색
		// g : 지정문자열 모두, i : 대소문자 무시
		// 수정버튼.부모태그로이동.부모태그의하위p태그.(내용중에br이있다, \n으로변경)
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		
		// 타임라인 수정 폼 UI : 동적으로 생성
		let modifyUI = '<form id="mcheerComm_form">';
		   modifyUI += '<input type="hidden" name="cheerComment_num" id="cheerComment_num" value="'+cheerComment_num+'">';
		   modifyUI += '<textarea rows="5" cols="70" name="cheerComm_content" id="mcheerComm_content" class="cheerComm-content">'+content+'</textarea>';
		   modifyUI += '<div id="mcheerComm_second">';
		   modifyUI += ' <input type="submit" value="수정">';
		   modifyUI += ' <input type="button" value="취소" class="cheerComm-reset">';
		   modifyUI += '</div>';
		   modifyUI += '<div id="mcheerComm_first"><span class="letter-count">300/300</span></div>';
		   modifyUI += '<hr size="1" noshade width="96%">';
		   modifyUI += '</form>';
		   
		   // 이전에 수정한 타임라인이 있을 경우 수정버튼을 클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화시킨다.
		   initModifyForm();
		   
		   $(this).parent().hide();
		   
		   // 수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		   $(this).parents('.item').append(modifyUI);
		   
		   // 입력한 글자수 셋팅
		   let inputLength = $('#mcheerComm_content').val().length;
		   let remain = 300 - inputLength;
		   remain += '/300';
		   
		   // 문서 객체에 반영
		   $('#mcheerComm_first .letter-count').text(remain);
	});
	// 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	$(document).on('click','.cheerComm-reset',function(){
		initModifyForm();
	});
	
	// 타임라인 수정
	$(document).on('submit','#mcheerComm_form',function(event){ // 미래($(document).on())의 태그에 동적으로 연결
		event.preventDefault(); // 기본 이벤트 제거 : 주소가 바뀌면 안 되기 때문에
		
		if($('#mcheerComm_content').val().trim()==''){
			alert('본인만의 타임라인을 함께 나눠보세요.');
			$('#mcheerComm_content').val('').focus();
			return false;
		}
		
		// 폼에 입력한 데이터 반환 = serialize() : 폼 이하의 태그들을 한번에 읽어온다.
		// serialize() : parameter name과 value의 쌍으로 불러온다.
		// this : textarea를 감싸고 있는 form
		let form_data = $(this).serialize();
		
		// 서버와 통신
		$.ajax({
			url:'cheerCommentUpdate.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 타임라인을 수정할 수 있습니다.');
				}else if(param.result=='success'){ // 화면 제어
					$('#mcheerComm_form').parent().find('p').html($('#mcheerComm_content').val().replace(/</g,'&lt;')
																				  		  .replace(/>/g,'&gt;')
																						  .replace(/\n/g,'<br>'));
					initModifyForm(); // 수정폼 삭제 및 초기화 → 바로 화면 갱신
				}else if(param.result=='wrongAccess'){
					alert('타인의 타임라인을 수정할 수 없습니다.');
				}else{
					alert('타임라인 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}); 
	*/
	
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