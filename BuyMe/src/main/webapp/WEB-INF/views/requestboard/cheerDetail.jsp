<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>응원 게시판 - 메인글 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<%-- script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/requestBoard.cheerComment.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="requestboard-title">
			<h1>이야기 한마당</h1>
	</div>
	<jsp:include page="/WEB-INF/views/common/requestBoardMenu.jsp"/>
	<div class="content-main">
		<h2>오늘은 함께 어떤 이야기를 나눠볼까요?</h2>
		<span><b>회원님의 오늘을 들려주세요!</b> (작성 시, 제목과 내용은 필수로 입력하셔야 합니다! 사진은 등록하지 않으셔도 됩니다.)</span>
	
	<!-- 댓글 내용 시작 -->
	<div id="cheerComment_div">
		<form id="cheerComm_form">
			<ul>
				<li>
					<input type="hidden" name="cheerComment_num" value="${cheerBoardVO.cheer_num}" id="cheerComment_num">
				</li>
				<li>
					<textarea rows="1" cols="70" name="cheerComm_title" id="cheerComm_title" class="cheerComm_title" placeholder="나의 이야기 제목"
					<c:if test="${empty user_num}">disabled="disabled"(비활성화)</c:if>
					><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				</li>
				<li>
					<textarea rows="5" cols="70" name="cheerComm_content" id="cheerComm_content" class="cheerComm-content" placeholder="나의 이야기 내용"
					<c:if test="${empty user_num}">disabled="disabled"(비활성화)</c:if>
					><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				</li>
				<li>
					<c:if test="${empty cheerComment.photo}">
						<img src="${pageContext.request.contextPath}/images/blank.png" width="513" height="200" class="my-photo">
					</c:if>
					<c:if test="${!empty cheerComment.photo}">
						<img src="${pageContext.request.contextPath}/upload/${cheerBoardVO.photo}" width="200" height="200" class="my-photo">
					</c:if>
				</li>
				<li>
					<c:if test="${!empty user_num}">
					<div id="cheerComm_first">
						<span class="letter-count">300/300</span>
					</div>
					</c:if>
					<div id="photo_choice">
						<input type="file" name="photo" id="photo" accept="image/gif,image/png,image/jpeg"><br>
						<input type="button" value="취소" id="photo_reset">
					</div>
					<div id="cheerComm_second">
						<input type="button" value="이야기 작성" id="cheer_btn">
					</div>
				</li>
			</ul>
		</form>
	</div> <!-- end of comment_div -->
	<!-- 댓글 내용 끝 -->
	<!-- 댓글 목록 시작 -->
	<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음 댓글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
	</div>
	<!-- 댓글 목록 끝 -->
	</div>
</div>
</body>
</html>