<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글·응원 목록</title>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/requestBoardHeader.jsp"/>
	<h2> 댓글 하나! 응원 하나!가 큰 힘이 됩니다.</h2>
	<jsp:include page="/WEB-INF/views/requestboard/commentwriteForm.jsp"/>
	<div class="content-main">
		<!-- 댓글 시작 -->
			<div id="comment_div">
				
			</div> <!-- end of .comment_div -->
			<!-- 댓글 끝 -->
			<!-- 댓글 목록 출력 시작 -->
			<!-- 댓글 목록 출력 끝 -->
	</div> <!-- end of .content-main -->
</div> <!-- end of .page-main -->
</body>
</html>