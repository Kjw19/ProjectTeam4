<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
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
		<h2>게시판 글수정</h2>
		<form action="update.do" method="post" id="update_form"
		              enctype="multipart/form-data">
			<input type="hidden" name="fund_num" 
			                       value="${fund.fund_num}">
			<ul>
				<li>
					<label for="fund_title">제목</label>
					<input type="text" name="fund_title" id="fund_title"
					    value="${fund.fund_title}" maxlength="50">
				</li>
				<li>
					<label for="category_num">카테고리</label>
					<select name="category_num" id="category_num">
						<option value="1">가전</option>
						<option value="2">패션</option>
						<option value="3">식료품</option>
					</select>
				</li>
				<li>
					<label for="fund_content">내용</label>
					<textarea rows="5" cols="30" name="fund_content" 
					   id="fund_content">${fund.fund_content}</textarea>
				</li>
				<li>
					<label for="fund_filename">파일</label>
					<input type="file" name="fund_filename" id="fund_filename"
					     accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty fund.fund_filename}">
					<div id="file_detail">
						(${fund.fund_filename})파일이 등록되어 있습니다.
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
										data:{fund_num:${fund.fund_num}},
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
				<input type="button" value="글상세"
				  onclick="location.href='detail.do?fund_num=${fund.fund_num}'">
			</div>                     
		</form>
	</div>
</div>
</body>
</html>




