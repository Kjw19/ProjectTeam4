<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// 아이디, 비밀번호 유효성 체크
	$(function(){
		// 아이디, 비밀번호 유효성 체크 - 상세정보 수정
		$('#simple_form').submit(function(){
			if($('#email').val().trim()==''){
				alert('이메일을 입력하세요.');
				$('#email').val('').focus();
				return false;
			}
			if($('#name').val().trim()==''){
				alert('이름을 입력하세요.');
				$('#name').val('').focus();
				return false;
			}
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요.');
				$('#id').val('').focus();
				return false;
			}
			if($('#passwd').val().trim()==''){
				alert('비밀번호를 입력하세요.');
				$('#passwd').val('').focus();
				return false;
			}
			if($('#passwd').val()!=${member.passwd}){
				$('#passwd').val('').focus();
				$('#message_passwd').css('color','#4B71DE').text('불일치');
				return false;
			}
		});
	});
	
	$(function(){
		/*
		$('#photo_btn').click(function(){
			$('#photo_choice').show();
			$(this).hide(); // 수정 버튼 감추기
		});
		*/
		
		
		// 이미지 미리보기
		let photo_path = $('.my-photo').attr('src'); // 처음 화면에 보여지는 이미지 읽기
		let my_photo;
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
		
		$('#photo_submit').click(function(){
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			$.ajax({
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false, // 데이터 객체를 문자열로 바꿀지에 대한 설정 : true면 일반문자, false면 file
				processData:false, // 해당 타입을 true로 하면 일반 text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result=='logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('프로필 사진이 수정되었습니다.');
						photo_path = $('.my-photo').attr('src');
						photo_path = $('.my-photo2').attr('src');
						$('#photo').val('');
						//$('#photo_choice').hide();
						//$('#photo_btn').show();
					}else{
						alert('파일 전송 오류 발생');
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
			$('#photo').val('');
		}); // end of click_reset (이미지 미리보기 취소)
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="myContent-main">
		<div class="mypage-div">
		
			<div class="mypage-first">
			<div class="mypage-profile"> <!-- 마이페이지 프로필 사진 시작 -->
				<ul>
					<li>
						<div>
							<c:if test="${empty member.photo}">
								<img src="${pageContext.request.contextPath}/images/face.png"
									width="200" height="200" class="my-photo2">
							</c:if>
							<c:if test="${!empty member.photo}">
								<img src="${pageContext.request.contextPath}/upload/${member.photo}"
									 width="200" height="200" class="my-photo2">
							</c:if>
						</div>
					</li>
					<li>
						<div><b>${member.name}</b>님</div>
					</li>
					<li>
						<div>개인회원</div>
					</li>
				</ul>
			</div> <!-- end of mypage-profile, 마이페이지 프로필 사진 끝-->
			
			<div class="mypage-menu"> <!-- 마이페이지 메뉴 시작 -->
				<ul>
					<li><b><a href="${pageContext.request.contextPath}/members/myPage.do">마이페이지</a></b></li>
					<%-- 
					<li><a href="#">서포터</a></li>
					<li><a href="#">메이커</a></li>
					--%>
				</ul>
			</div> <!-- end of mypage-menu, 마이페이지 메뉴 끝 -->
			</div> <!--  end of mypage-first -->
			
			<div class="mypage-second">
			
			<form id="simple_form" action="modifyMyInfoDetail.do" method="post">
			<div class="mypage-simple"> <!-- 간단 내 정보 시작 -->
				<ul>
					<li>
						<h2>내 상세 정보 수정</h2>
						<span>*아이디, 비밀번호는 수정 불가능</span>
					</li>
					<li>
						<c:if test="${empty member.photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="100" height="100" class="my-photo">
						</c:if>
						<c:if test="${!empty member.photo}">
							<img src="${pageContext.request.contextPath}/upload/${member.photo}" width="100" height="100" class="my-photo">
						</c:if>
					</li>
					<li>
						<div id="photo_choice">
							<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
							<input type="button" value="프로필 수정" id="photo_submit">
							<input type="button" value="취소" id="photo_reset">
						</div>
					</li>
				</ul>
				<ul>
					<li>
						<label for="email">이메일</label>
						<input type="email" name="email" id="email" value="${member.email}" maxlength="50">
					</li>
					<li>
						<label for="name">이름</label>
						<input type="text" name="name" value="${member.name}" id="name" maxlength="12" autocomplete="off">
					</li>
					<li>
						<label for="id">아이디</label>
						<input type="text" name="id" value="${member.id}" id="id" maxlength="12" autocomplete="off" readonly/>
					</li>
					<li>
						<label for="passwd">비밀번호</label>
						<input type="password" name="passwd" id="passwd" value="${member.passwd}" maxlength="12" readonly/>
						<span id="message_passwd"></span>
					</li>
					<li class="detail-li">
						<input type="submit" value="내 상세 정보 수정" class="detail-lookup">
						<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/members/myPage.do'" class="detail-lookup">
					</li>
				</ul>
			</div> <!--  end of mypage-simple, 간단 내 정보 끝 -->
			</form>
			</div> <!--  end of mypage-second -->
		
		</div> <!-- end of .mypage-div -->
	</div> <!-- end of .content-main -->
	<div class="clear-both"></div> <!-- float해제, end of .mypage-end -->
</div> <!-- end of .page-main -->
</body>
</html>