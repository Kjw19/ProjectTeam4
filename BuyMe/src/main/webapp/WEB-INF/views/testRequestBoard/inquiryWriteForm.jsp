<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 문의 - 문의글 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/requestBoardHeader.jsp"/>
	<div class="content-main">
		<h2>1:1 문의하기</h2>
		<form id="inquiryWrite_form" action="inquiryWrite.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<label for="title">문의 제목</label>
				<input type="text" name="inqu_title" id="title" maxlength="50">
			</li>
			<li>
				<label for="content">문의 내용</label>
				<textarea rows="5" cols="50" name="inqu_content" id="content"></textarea>
			</li>
			<li>
				<label for="filename">파일</label>
				<input type="file" name="inqu_filename" id="filename" accept="image/gif,image/png,image/jpeg,image/jpg">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="문의 등록">
			<input type="button" value="문의 목록으로" onclick="location.href='myList.do'">
		</div>
		</form>
	</div> <!-- end of .content-main -->
</div> <!-- end of .page-main -->
</body>
</html>