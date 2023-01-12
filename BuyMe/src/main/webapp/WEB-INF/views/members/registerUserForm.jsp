<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	let idChecked = 0; // 0: id 중복체크 미실시 혹은 id 중복 / 1: id 미중복
	// 아이디 중복 체크
	$('#id_check').click(function(){
		if($('#id').val().trim()==''){
			alert('아이디를 입력하세요!');
			$('#id').val('').focus();
			return; // click
		}
		
		// 서버와 통신
		$.ajax({
			url:'checkDuplicatedIdMember.do',
			type:'post',
			data:{id:$('#id').val()},
			dataType:'json',
			success:function(param){
				if(param.result=='idNotFound'){
					idChecked = 1;
					$('#message_id').css('color','#2B478E').text('등록 가능 ID');
				}else if(param.result=='idDuplicated'){
					idChecked = 0;
					$('#message_id').css('color','#4B71DE').text('등록된 ID');
					$('#id').val('').focus();
				}else{
					idChecked = 0;
					alert('아이디 중복 체크 오류 발생');
				}
			},
			error:function(){
				idChecked = 0;
				alert('네트워크 오류 발생');
			}
		});
	}); // end of click  
	
	// 아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
	$('#register_form #id').keydown(function(){
		idChecked = 0;
		$('#message_id').text('');
	}); // end of keydown
	
	// 회원 정보 등록 유효성 체크
	$('#register_form').submit(function(){
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
			return false; // submit
		}
		if(idChecked==0){
			alert('아이디 중복 체크 필수!');
			return false;
		}
		if($('#passwd').val().trim()==''){
			alert('비밀번호를 입력하세요.');
			$('#passwd').val('').focus();
			return false;
		}
		if($('#re_passwd').val().trim()==''){
			alert('비밀번호를 확인하세요!');
			$('#re_passwd').val('').focus();
			return false;
		}
		if($('#passwd').val()!=$('#re_passwd').val()){
			alert('비밀번호와 비밀번호 확인이 불일치합니다.');
			$('#passwd').val('').focus();
			$('#re_passwd').val('');
			return false;
		}
	}); // end of submit
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="myContent-main">
	<div class="regis-main">
		<h2>회원가입</h2>
		<form id="register_form" action="registerUser.do" method="post">
			<ul>
				<li>
					<label for="email">이메일</label>
					<input type="email" name="email" id="email" maxlength="50" placeholder="이메일 입력">
				</li>
				<li>
					<label for="name">이름</label>
					<input type="text" name="name" id="name" maxlength="10" placeholder="이름 입력">
				</li>
				<li>
					<label for="id">ID</label>
					<input type="text" name="id" id="id" maxlength="12" autocomplete="off" placeholder="아이디 입력">
					<input type="button" value="ID 중복체크" id="id_check">
					<span id="message_id"></span>
				</li>
				<li>
					<label for="passwd">비밀번호</label>
					<input type="password" name="passwd" id="passwd" maxlength="12" placeholder="비밀번호 입력">
				</li>
				<li>
					<label for="re_passwd">비밀번호 확인</label>
					<input type="password" name="re_passwd" id="re_passwd" maxlength="12" placeholder="비밀번호 확인 입력">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="회원가입">
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</form>
	</div>
	</div>
</div>
</body>
</html>