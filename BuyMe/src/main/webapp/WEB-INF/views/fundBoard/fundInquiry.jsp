<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펀딩게시판 문의</title>
</head>
<body>
	<!-- 문의 시작 -->
		<div id="inquiry_div">
			<span class="inqu-title">문의 작성</span>
			<form id="inqu_form">
				<input type="hidden" name="fund_num"
				       value="${fund_Board.fund_num}" id="fund_num">
				<input type="text" id="inqu-title" name="inqu-title">
				<textarea rows="3" cols="50" name="comm_content"
				  id="inqu_content" class="inqur-content"
				  <c:if test="${empty user_num}">disabled="disabled"</c:if>
				  ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
				<div id="inqu_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="inqu_second" class="align-right">
					<input type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>
		<!-- 문의 목록 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 문의 목록 출력 끝 -->
		<!-- 문의 끝 -->


</body>
</html>