<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<div class="rqwrite-title">
		<h2>게시판 글쓰기</h2>
	</div>	
	<div class="rqwrite-main">
		<div class="rqwrite-form">
		<form id="write_form" action="write.do" method="post"
		                       enctype="multipart/form-data">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="req_title" id="title"
					                          maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" 
					name="req_content" id="content"></textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="req_filename" 
					            id="filename" 
					 accept="image/gif,image/png,image/jpeg">
				</li>
			</ul> 
			<div class="submit-list">
			<ul>
				<li>
					<input type="submit" value="등록">
				</li>
				<li>
					<input type="button" value="목록"
				            onclick="location.href='list.do'">
				</li>
			</ul>
			</div>                                          
		</form>
	</div>
	</div>
	</div>
</div>
</body>
</html> 
