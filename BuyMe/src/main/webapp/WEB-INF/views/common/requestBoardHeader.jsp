<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/testRequestBoard.css">
<!-- requestBoardHeader 시작 -->
<div class="sub-header">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/requestboard/mainCommentDetailList.do">댓글·응원</a>
		</li>
		<li>
			<a>자주 묻는 질문</a>
		</li>
		<!-- /c:if -->
	</ul>
</div>
<div class="clear-both"></div>
<hr size="1" noshade="noshade" width="70%">
<!-- requestBoardHeader 끝 -->