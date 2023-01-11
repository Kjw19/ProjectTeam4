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
					<li><b><a href="#">마이페이지</a></b></li>
					<li><a href="#">서포터</a></li>
					<li><a href="#">메이커</a></li>
				</ul>
			</div> <!-- end of mypage-menu, 마이페이지 메뉴 끝 -->
			</div> <!--  end of mypage-first -->
			
			<div class="mypage-second">
			
			<form id="simple_form" action="modifyMyInfoDetail.do" method="post">
			<div class="mypage-simple"> <!-- 간단 내 정보 시작 -->
			<span id="explain">*아이디는 수정 불가능</span>
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
						<input type="password" name="passwd" id="passwd" maxlength="12">
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