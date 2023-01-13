<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestBoard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<%-- script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/requestBoard.comment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/requestBoard.inquiry.js"></script>
</head> 
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/requestBoardMenu.jsp"/>
<div class="content-main">
		<h2>${request_board.req_title}</h2>
		<ul class="detail-info">
			<li>
				${request_board.id}<br>
				조회 : ${request_board.req_hit}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty request_board.req_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${request_board.req_filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${request_board.req_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
		   
			<li>
				<c:if test="${!empty request_board.req_modify_date}">
				최근 수정일 : ${request_board.req_modify_date}
				</c:if>
				작성일 : ${request_board.req_reg_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${user_num == request_board.mem_num}">
				<input type="button" value="수정" 
				onclick="location.href='updateForm.do?req_num=${request_board.req_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?req_num=${request_board.req_num}');
						}
					};
				</script>
				</c:if>
			</li>
		</ul>
	
	
	<!-- requestBoardHeader 시작 -->
	<p>
	<div class="sub-header">
		<ul>
			<li>
				<span id="commnet"><a href="#comment_div">댓글·문의</a></span>
			</li>
		</ul>
	</div>
	<div class="clear-both"></div>
	<hr size="1" noshade="noshade" width="100%" color="#4B71DE">
	<!-- requestBoardHeader 끝 -->
	
	<!-- 댓글 내용 시작 -->
	<div id="comment_div">
		<form id="comm_form">
			<span class="comm-title"></span><br>
			<input type="hidden" value="${request_board.mem_num}">
			<input type="hidden" name="req_num" value="${request_board.req_num}" id="req_num">
			<textarea rows="5" cols="100" name="comm_content" id="comm_content" class="comm-content"
			<c:if test="${empty user_num || user_auth<5}">disabled="disabled"(비활성화)</c:if>
			><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if><c:if test="${user_auth<5}">관리자가 아닙니다.</c:if></textarea>
			<c:if test="${!empty user_num && user_auth==5}">
			<div id="comm_second" class="align-right">
				<input type="submit" value="등록">
			</div>
			<div id="comm_first">
				<span class="letter-count">300/300</span>
			</div>
			</c:if>
		</form>
	</div> <!-- end of comment_div -->
	<!-- 댓글 내용 끝 -->
	<!-- 댓글 목록 시작 -->
	<div id="commentList_div">
	<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="more">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
	</div>
	</div>
	<!-- 댓글 목록 끝 -->
	
</div> <!-- end of content-main -->
</div>
</body>
</html>