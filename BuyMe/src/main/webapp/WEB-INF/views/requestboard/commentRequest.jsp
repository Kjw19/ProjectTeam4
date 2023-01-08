<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 댓글</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/requestBoard.comment.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="page-main">
	<!-- 댓글 내용 시작 -->
	<div id="comment_div">
		<span class="comm-title">댓글 달기</span>
		<form id="comm_form">
			<input type="hidden" name="req_num" value="${req.req_num}" id="req_num">
			<textarea rows="5" cols="50" name="comm_content" id="comm_content" class="comment-content"
			<c:if test="${empty user_num}">disabled="disabled"(비활성화)</c:if>
			><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<c:if test="${!empty user_num}">
			<div id="comm_first">
				<span class="letter-count">100/100</span>
			</div>
			<div id="comm_second" class="align-right">
				<input type="submit" value="전송">
			</div>
			</c:if>
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

</body>
</html>