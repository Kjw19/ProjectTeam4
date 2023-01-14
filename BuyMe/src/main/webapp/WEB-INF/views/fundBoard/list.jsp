<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펀딩게시판 목록</title>
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
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 목록</h2>
		<!-- 카테고리 검색 -->
	<div class="fund_menu">
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/fundBoard/list.do"><img src="../images/all.png"><br>전체</a></li>
				<li><a href="${pageContext.request.contextPath}/fundBoard/list.do?keyfield=4&keyword=1"><img src="../images/tv.png"><br>가전</a></li>
				<li><a href="${pageContext.request.contextPath}/fundBoard/list.do?keyfield=4&keyword=2"><img src="../images/fashion.png"><br>패션</a></li>
				<li><a href="${pageContext.request.contextPath}/fundBoard/list.do?keyfield=4&keyword=3"><img src="../images/food.png"><br>푸드</a></li>
			</ul>
		</nav>
	</div>
	<!-- 카테고리 검색 끝 -->
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
		</form>
		<!-- 검색 폼 끝 -->
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>글번호</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			<c:forEach var="fund" items="${list}">
			<tr>
				<td>${fund.fund_num}</td>
				<c:if test="${fund.category_num == 1}"><td>가전</td></c:if>
				<c:if test="${fund.category_num == 2}"><td>패션</td></c:if>
				<c:if test="${fund.category_num == 3}"><td>식료품</td></c:if>
				<td><a href="detail.do?fund_num=${fund.fund_num}">${fund.fund_title}</a></td>
				<td>${fund.id}</td>
				<td>${fund.fund_reg_date}</td>
				<td>${fund.fund_hit}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="list-space align-right">
			<input type="button" value="글쓰기"
			       onclick="location.href='${pageContext.request.contextPath}/fundBoard/writeForm.do'"
			   <c:if test="${empty user_num}">disabled="disabled"</c:if>/>
			<input type="button" value="목록"
			    onclick="location.href='list.do'">
			<input type="button" value="홈으로"
			  onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>





