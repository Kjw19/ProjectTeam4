<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 게시판 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
<div>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div>
		<div>
			<h2>공지사항 수정</h2>
		</div>
		<div>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="noti_num" value="${noticeboard.noti_num}">
				<ul>
					<li>
						<label for="noti_title">제목</label>
						<input type="text" name="noti_title" id="noti_title" value="${noticeboard.noti_title}" maxlength="50">
					</li>
					<li>
						<label for="noti_content">내용</label>
						<textarea rows="5" cols="30" name="noti_content" id="noti_content">${noticeboard.noti_content}</textarea>
					</li>
					<li>
						<label for="noti_filename">이미지 첨부</label>
						<input type="file" name="noti_filename" id="noti_filename" accept="image/gif,image/png,image/jpeg">
						<c:if test="${!empty noticeboard.noti_filename}">
						<div id="file_detail">
							(${noticeboard.noti_filename})파일이 등록되어 있습니다.
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
											data:{noti_num:${noticeboard.noti_num}},
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
				<div class="align-center">
					<input type="submit" value="수정">
					<input type="button" value="글보기" onclick="location.href='detail.do?noti_num=${noticeboard.noti_num}'">
				</div>                      
			</form>
		</div>
	</div>
</div>
</body>
</html>