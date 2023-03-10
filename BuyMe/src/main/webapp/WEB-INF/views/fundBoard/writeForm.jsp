<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펀딩 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fundBoard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#fund_title').val().trim()==''){
				alert('제목을 입력하세요!');
				$('#fund_title').val('').focus();
				return false;
			}
			if($('#category').val().trim()==''){
				alert('카테고리을 입력하세요!');
				$('#category').val('').focus();
				return false;
			}
			if($('#fund_content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#fund_content').val('').focus();
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
	<div class="fdwrite-title">
		<h2>게시판 글쓰기</h2>
	</div>
	<div class="fdwrite-main">
		<div class="fdwrite-form">	
		<form id="write_form" action="write.do" method="post"
		                       enctype="multipart/form-data">
			<ul>
				<li>
					<label for="fund_title">제목</label>
					<input type="text" name="fund_title" id="fund_title"
					                          maxlength="50">
				</li>
				<li>
					<label for="category_num">카테고리</label>
					<select name="category_num" id="category_num">
						<option value="1">가전</option>
						<option value="2">패션</option>
						<option value="3">푸드</option>
					</select>
				</li>
				<li>
					<label for="fund_content">내용</label>
					<textarea rows="5" cols="30" 
					name="fund_content" id="fund_content"></textarea>
				</li>
				<li>
					<label for="fund_filename">파일</label>
					<input type="file" name="fund_filename" 
					            id="fund_filename" 
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







