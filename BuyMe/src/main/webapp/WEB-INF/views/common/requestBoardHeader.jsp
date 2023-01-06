<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<!-- requestBoardHeader 시작 -->
<div class="header">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/requestBoardComment/writeCommentForm.do">댓글·응원</a>
		</li>
		
		<li>
			<a>자주 묻는 질문</a>
		</li>
		
		<!-- 일반회원, 관리자만 사용 가능한 메뉴 / 비회원, 탈퇴, 정지회원에게는 보이지 않음 -->
		<!-- c:if test="${!empty user_num && user_auth==1 && user_auth==5}" --> 
		<li>
			<a>내 문의</a>
		</li>
		<!-- /c:if -->
	</ul>
</div>
<!-- requestBoardHeader 끝 -->