<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>응원게시판 메인 글 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>댓글·응원(메인:응원게시판)</h2>
		<%-- enctype="multipart/form-data" : 파일업로드가 있을 경우 --%>
		<form id="cheerWrite_form" action="cheerWrite.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<label for="cheer_content">내용</label>
				<textarea rows="5" cols="30" name="cheer_content" id="cheer_content"></textarea>
			</li>
			<li>
				<label for="cheer_filename">파일</label>
				<input type="file" name="cheer_filename" id="cheer_filename" accept="image/gif,image/png,image/jpeg">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록" onclick="location.href='cheerList.do'">
		</div>
		</form>
	</div>
</div>
</body>
</html>