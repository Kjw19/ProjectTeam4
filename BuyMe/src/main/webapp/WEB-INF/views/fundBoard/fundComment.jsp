<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펀딩게시판 댓글</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fundBoardCommInqu.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fund.comment.js"></script>
</head>
<body>

<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="sub-header">
	<ul>
		<li>
			<a href="#">펀딩게시글</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/fundBoard/detailComment.do?fund_num=${fund.fund_num}">댓글</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/fundBoard/detailInquiry.do?fund_num=${fund.fund_num}">문의</a>
		</li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
</div>
	<div class="content-main">
		<h2 class="fund_title">${fund.fund_title}</h2>
	<!-- 댓글 시작 -->
		<div id="comment_div">
			<span class="comm_title">댓글 작성</span>
			<form id="comm_form">
				<div class="inqu-input">
					<input type="hidden" name="fund_num" 
					       value="${fund.fund_num}" id="fund_num">
					<label for="comm_content" class="comm_content">내용</label>		       
					<textarea rows="3" cols="50" name="comm_content" 
					  id="comm_content" class="comm-content" placeholder="내용을 입력해주세요."
					  <c:if test="${empty user_num}">disabled="disabled"</c:if>
					  ></textarea>       
					<c:if test="${!empty user_num}">
					<div id="comm_first">
						<span class="letter-count">300/300</span>
					</div>
					</c:if>
				</div>
				<div id="comm_second" class="align-right">
					<input type="submit" value="전송">
				</div>
			</form>
		</div>
		<!-- 댓글 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 댓글 목록 출력 끝 -->
		<!-- 댓글 끝 -->
	</div>
</div>

</body>
</html>