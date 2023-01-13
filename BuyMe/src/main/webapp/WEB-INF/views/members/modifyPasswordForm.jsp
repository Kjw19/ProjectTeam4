<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// 아이디, 비밀번호 유효성 체크
	$(function(){
		// 아이디, 비밀번호 유효성 체크
		$('#simple_form').submit(function(){
			if($('#origin_passwd').val().trim()==''){
				alert('현재 비밀번호를 입력하세요.');
				$('#origin_passwd').val('').focus();
				return false;
			}
			if($('#origin_passwd').val()!=$('#check_passwd').val()){
				alert('현재 비밀번호를 잘못 입력하셨습니다.');
				$('#origin_passwd').val('').focus();
				$('#message_origin_passwd').text('불일치');
				return false;
			}
			if($('#passwd').val().trim()==''){
				alert('새 비밀번호를 입력하세요.');
				$('#passwd').val('').focus();
				return false;
			}
			if($('#re_passwd').val().trim()==''){
				alert('새 비밀번호 확인을 입력하세요.');
				$('#re_passwd').val('').focus();
				return false;
			}
			if($('#passwd').val()!=$('#re_passwd').val()){
				alert('새 비밀번호와 새 비밀번호 확인이 불일치합니다.');
				$('#passwd').val('').focus();
				$('#re_passwd').val('');
				$('#message_re_passwd').text('불일치');
				return false;
			}
			
		});
		
		$('#simple_form').keydown(function(){
			$('#message_origin_passwd').text('');
		});
		$('#simple_form').keydown(function(){
			$('#message_re_passwd').text('');
		});
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
									width="200" height="200" class="my-photo">
							</c:if>
							<c:if test="${!empty member.photo}">
								<img src="${pageContext.request.contextPath}/upload/${member.photo}"
									 width="200" height="200" class="my-photo">
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
					<li><a href="${pageContext.request.contextPath}/members/deleteUserForm.do">회원탈퇴</a></li>
					<%-- 
					<li><a href="#">서포터</a></li>
					<li><a href="#">메이커</a></li>
					--%>
				</ul>
			</div> <!-- end of mypage-menu, 마이페이지 메뉴 끝 -->
			</div> <!--  end of mypage-first -->
			
			<div class="mypage-second">
			<form id="simple_form" action="modifyPassword.do" method="post">
			<div class="passwd-input"> <!-- 간단 내 정보 시작 -->
				<ul>
					<li>
						<h2>비밀번호 수정</h2>
					</li>
					<li>
						<input type="hidden" value="${member.passwd}" id="check_passwd">
					</li>
					<li>
						<label for="email" class="passwd-email">이메일</label>
						<input type="email" name="email" value="${member.email}" id="email" maxlength="50" readonly/>
					</li>
					<li>
						<label for="id" class="passwd-id">아이디</label>
						<input type="text" name="id" value="${member.id}" id="id" maxlength="12" autocomplete="off" readonly/>
					</li>
					<li>
						<label for="origin_passwd" class="passwd-origin_passwd">현재 비밀번호</label>
						<input type="password" name="origin_passwd" id="origin_passwd" maxlength="12">
						<span id="message_origin_passwd"></span>
					</li>
					<li>
						<label for="passwd" class="passwd-passwd">새 비밀번호</label>
						<input type="password" name="passwd" id="passwd" maxlength="12">
						<span id="message_passwd"></span>
					</li>
					<li>
						<label for="re_passwd" class="passwd-re_passwd">비밀번호 확인</label>
						<input type="password" name="re_passwd" id="re_passwd" maxlength="12">
						<span id="message_re_passwd"></span>
					</li>
				</ul>
			</div> <!--  end of mypage-simple, 간단 내 정보 끝 -->
			<div class="passwd-btn"><!-- 버튼 추가 -->
				<ul>
					<li>
						<input type="submit" value="비밀번호 수정 확인" class="detail-lookup" id="modify_passwd">
					</li>
					<li>
						<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/members/myPage.do'" class="detail-lookup">
					</li>
				</ul>
			</div><!-- 버튼 종료 -->
			</form>
			</div> <!--  end of mypage-second -->
		
		</div> <!-- end of .mypage-div -->
	</div> <!-- end of .content-main -->
	<div class="clear-both"></div> <!-- float해제, end of .mypage-end -->
</div> <!-- end of .page-main -->
</body>
</html>