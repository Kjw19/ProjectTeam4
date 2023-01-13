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
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요.');
				$('#id').val('').focus();
				return false;
			}
			if($('#id').val()!=$('#check_id').val()){
				alert('아이디를 잘못 입력하셨습니다.');
				$('#id').val('').focus();
				$('#message_id').text('불일치');
				return false;
			}
			if($('#passwd').val().trim()==''){
				alert('비밀번호를 입력하세요.');
				$('#passwd').val('').focus();
				return false;
			}
			if($('#passwd').val()!=$('#check_passwd').val()){
				alert('비밀번호를 잘못 입력하셨습니다.');
				$('#passwd').val('').focus();
				$('#message_passwd').text('불일치');
				return false;
			}
		});
		
		$('#simple_form #id').keydown(function(){
			$('#message_id').text('');
		});
		$('#simple_form #passwd').keydown(function(){
			$('#message_passwd').text('');
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
						<span style="color:#2B478E"><b>${member.name}</b></span>님
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
				</ul>
			</div> <!-- end of mypage-menu, 마이페이지 메뉴 끝 -->
			</div> <!--  end of mypage-first -->
			
			<div class="mypage-second">
			<form id="simple_form" action="deleteUser.do" method="post">
			<div class="unregis-input"> <!-- 간단 내 정보 시작 -->
				<ul>
					<li class="second-title">
						<h2>회원 탈퇴</h2>
					</li>
					<li>
						<input type="hidden" value="${member.id}" id="check_id">
					</li>
					<li>
						<label for="id" class="unregis-id">아이디</label>
						<input type="text" name="id" id="id" maxlength="12" autocomplete="off">
						<span id="message_id"></span>
					</li>
					<li>
						<label for="passwd" class="unregis-passwd">비밀번호</label>
						<input type="password" name="passwd" id="passwd" maxlength="12">
						<span id="message_passwd"></span>
					</li>
					<li>
						<input type="hidden" value="${member.passwd}" id="check_passwd">
					</li>
				
					<li class="detail-li">
						<input type="submit" value="회원탈퇴" class="detail-lookup" id="detail_lookup">
						<input type="button" value="마이페이지"  onclick="location.href='${pageContext.request.contextPath}/members/myPage.do'" class="detail-lookup" id="modify_passwd">
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