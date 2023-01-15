<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
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
		<h2>게시판 글수정</h2>
	</div>
	<div class="rqwrite-main">
		<div class="rqwrite-form">	
		<form action="update.do" method="post" id="update_form"
		              enctype="multipart/form-data">
			<input type="hidden" name="req_num" 
			                       value="${request_board.req_num}">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="req_title" id="title"
					    value="${request_board.req_title}" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="req_content" 
					   id="content">${request_board.req_content}</textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="req_filename" id="filename"
					     accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty request_board.req_filename}">
					<div id="file_detail">
						(${request_board.req_filename})파일이 등록되어 있습니다.
						<input type="button" value="파일삭제" id="file_del">
					</div>	
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{req_num:${request_board.req_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요!');
											}else if(param.result == 'success'){
												$('#file_detail').hide();
											}else if(param.result == 'wrongAccess'){
												alert('잘못된 접속입니다.');
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						});
					</script>
					</c:if>     
				</li>
			</ul>
			<div class="submit-list">
			<ul>
				<li>
					<input type="submit" value="등록">
				</li>
				<li>
					<input type="button" value="글상세"
				  onclick="location.href='detail.do?req_num=${request_board.req_num}'">
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