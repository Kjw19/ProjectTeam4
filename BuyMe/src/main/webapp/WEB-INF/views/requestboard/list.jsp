<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>질문 목록</title> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="requestboard-title">
			<h1>문의게시판</h1>
	</div>
	<jsp:include page="/WEB-INF/views/common/requestBoardMenu.jsp"/>
	<div class="content-main">
		<h2>문의 목록</h2>
		
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			<c:forEach var="board" items="${list}">
			<tr>
				<td>${board.req_num}</td>
				<td><a href="detail.do?req_num=${board.req_num}">${board.req_title}</a></td>
				<td>${board.id}</td>
				<td>${board.req_reg_date}</td>
				<td>${board.req_hit}</td>
			</tr>
			</c:forEach>
		</table>
		</c:if>
	<!-- 검색 폼 시작 -->
		<form id="search_form" action="list.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword"
					       id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
								<div class="align-center">${page}</div>
		</form>
		<!-- 검색 폼 끝 -->
		<div class="list-space align-right">
			<input type="button" value="문의작성"
			       onclick="location.href='writeForm.do'"
			   <c:if test="${empty user_num}">disabled="disabled"</c:if>/>
			<input type="button" value="목록"
			    onclick="location.href='list.do'">
			<input type="button" value="홈으로"
			  onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
			</div>
</div>
</body>
</html> 