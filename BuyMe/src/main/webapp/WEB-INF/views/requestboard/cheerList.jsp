<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의게시판 내 응원게시판 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/requestBoardMenu.jsp"/>
	<h2>게시판 목록</h2>
	<div class="align-right">
		<input type="button" value="응원 메인 글쓰기" onclick="location.href='cheerWriteForm.do'">
	</div>
	
	<c:if test="${count==0}">
	<div class="result-display">
		등록된 게시물이 없습니다.
	</div>
	</c:if>

	<c:if test="${count>0}">
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="cheerBoard" items="${list}"> <!-- ListAction의 ArrayList를 가져온다. -->
		<tr>
			<td>${cheerBoard.cheer_num}</td> <!-- 글번호 -->
			<td><a href="cheerDetail.do?cheer_num=${cheerBoard.cheer_num}">${cheerBoard.cheer_content}</a></td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">
		${pagingHtml} <!-- 페이지 번호 -->
	</div>
	</c:if>
</div>
</body>
</html>