<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 문의 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/requestBoardHeader.jsp"/>
	<div class="content-main">
		<h2>내 문의 목록</h2>
		<!-- 검색 폼 시작 -->
		<form id="myInqu_search_form" action="myList.do" method="get"> <%-- get : 인코딩 자동 처리 --%>
			<ul class="myInqu-search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>문의 제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>문의 내용</option>
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
		<!-- 검색 폼 끝 -->
		<div class="myInquList-space align-right">
			<input type="button" value="글쓰기" onclick="location.href='inquiryWriteForm.do'"
				<%-- 로그인 됐을 경우에만 활성화되게 
				<c:if test="${empty user_num}">disabled="disabled"</c:if>--%>/>
			<input type="button" value="내 문의 목록" onclick="location.href='myList.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div> <!-- end of .myInquList-space -->
		<c:if test="${count==0}">
		<div class="result-display">
			등록한 문의가 없습니다.
		</div> <!-- end of .result-display -->
		</c:if>
		<c:if test="${count>0}">
		<table>
			<tr>
				<th>문의 제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>답변 여부</th>
			</tr>
			<c:forEach var="inquiry" items="${list}">
			<tr>
				<td><a href="inquiryDetail.do?inquiry_num=${inquiry.inquiry_num}">${inquiry.inqu_title}</a></td>
				<td><a href="inquiryDetail.do?inquiry_num=${inquiry.inquiry_num}">${inquiry.id}</a></td>
				<td>${inquiry.inqu_reg_date}</td>
				<td>${inquiry.re_inqu_is_ok}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>