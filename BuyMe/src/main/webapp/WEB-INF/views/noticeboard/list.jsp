<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
<div>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div>
		<!-- 공지사항 메인 글씨 -->
		<div>
			<h1>공지사항</h1>
		</div>
		
		<!-- 공지사항 카테고리 글씨 -->
		<div>
			<h3>공지사항</h3>
		</div>
		<hr size="1" width="100%" noshade="noshade">
		
		<!-- 게시물이 하나도 없을 때 -->
		<c:if test="${count == 0}">
		<div>
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		
		<!-- 표시할 게시물이 있을 때 -->
		<c:if test="${count > 0}">
		<table>
			<c:forEach var="noticeboard" items="${list}">
			<div>
				<h2><a href="detail.do?noti_num=${noticeboard.noti_num}">${noticeboard.noti_title}</a></h2>
				<br>
				<ul>
					<li>${noticeboard.id}</li>
					<li>${noticeboard.noti_reg_date}</li>
					<li>${noticeboard.noti_hit}</li>
				</ul>
			</div>
			<c:if test="${!empty noticeboard.noti_filename}">
				<div>
					<img src="${pageContext.request.contextPath}/upload/${noticeboard.noti_filename}">
				</div>
			</c:if>
			<hr size="1" width="100%" noshade="noshade">
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
		
		<!-- 글쓰기/목록 버튼 -->
		<div>
			<!-- 글쓰기버튼 관리자만 보이게 해야함(아직X) -->
			<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
			   <c:if test="${empty user_num}">disabled="disabled"</c:if>/>
			<input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		
		<!-- 검색 폼 -->
		<form id="search_form" action="list.do" method="get">
			<ul>
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>