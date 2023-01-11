<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeboard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#ntsearch_form').submit(function(){
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
<div class="ntpage-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="ntcontent-main">
		<!-- 공지사항 메인 글씨 -->
		<div class="noticeboard-title">
			<h1>공지사항</h1>
		</div>
		
		<!-- 공지사항 카테고리 글씨 -->
		<div class="noticeboard-subtitle">
			<h3><a href="list.do">공지사항</a></h3>
		</div>
		<hr size="1" width="100%" noshade="noshade" color="#4B71DE">
		
		<!-- 게시물이 하나도 없을 때 -->
		<c:if test="${count == 0}">
		<div class="notice-emptybox">
			<span>표시할 게시물이 없습니다.</span>
		</div>
		</c:if>
		
		<!-- 표시할 게시물이 있을 때 -->
		<c:if test="${count > 0}">
			<c:forEach var="noticeboard" items="${list}">
			<div class="notice-box">
				<div class="noti-pre">
					<h2><a href="detail.do?noti_num=${noticeboard.noti_num}">${noticeboard.noti_title}</a></h2>
					<p class="noti-text">
						<span>${noticeboard.id}</span>
						<span><fmt:formatDate value="${noticeboard.noti_reg_date}" pattern="yyyy.MM.dd"/></span>
						<span>${noticeboard.noti_hit}</span>
					</p>
				</div>
				<c:if test="${!empty noticeboard.noti_filename}">
					<div class="notice-imagebox">
					<img src="${pageContext.request.contextPath}/upload/${noticeboard.noti_filename}">
					</div>
				</c:if>
			</div>
			<hr size="1" width="100%" noshade="noshade" color="#E8E8E8">
			</c:forEach>
		<div class="page-number">${page}</div>
		</c:if>
		
		<!-- 글쓰기/목록 버튼 -->
		<div class="align-right">
			<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
			   <c:if test="${empty user_num}">disabled="disabled"</c:if>/>
			<input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		
		<!-- 검색 폼 -->
		<form id="ntsearch_form" action="list.do" method="get">
			<ul class="ib">
				<li>
					<select class="ntsc1" name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input class="ntsc2" type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input class="ntsc3" type="submit" value="검색">
				</li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>