<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeboard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#noti_title').val().trim()==''){
				alert('제목을 입력하세요');
				$('#noti_title').val('').focus();
				return false;
			}
			if($('#noti_content').val().trim()==''){
				alert('내용을 입력하세요');
				$('#noti_content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="ntpage-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="ntwrite-title">
		<h2>공지사항 작성</h2>
	</div>
	<div class="ntwrite-main">
		<div class="ntwrite-form">
			<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li>
						<label for="noti_title">제목</label>
						<input type="text" name="noti_title" id="noti_title" maxlength="50">
					</li>
					<li>
						<label for="noti_content">내용</label><br>
						<textarea rows="5" cols="30" name="noti_content" id="noti_content"></textarea>
					</li>
					<li>
						<label for="noti_filename">
							<div class="file-deco">이미지 첨부</div>
						</label>
						<input type="file" name="noti_filename" id="noti_filename" accept="image/gif,image/png,image/jpeg">
					</li>
				</ul> 
				<div class="align-right">
					<input type="submit" value="작성">
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>