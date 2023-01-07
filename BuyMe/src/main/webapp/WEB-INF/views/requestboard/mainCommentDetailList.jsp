<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글·응원 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/requestBoard.mainComment.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<jsp:include page="/WEB-INF/views/common/requestBoardHeader.jsp" />
		<h2>댓글 하나, 응원 하나가 큰 힘이 됩니다.</h2>
		<div class="content-main">
			<!-- 댓글 폼 시작 -->
			<div id="mainComment_div">
				<span class="mainComm-title">댓글달고 응원하기</span>
				<form id="mainComm_form">
					<!-- 로그인체크 넣지 않음 -->
					<textarea rows="3" cols="100" name="mainComm_content"
						id="mainComm_content" class="mainComm-content"></textarea>
					<div id="mainComm_first">
						<span class="letter-count">300/300</span>
					</div>
					<div id="mainComm_second" class="aligh-right">
						<input type="submit" value="전송">
					</div>
				</form>
			</div>
			<!-- end of .comment_div -->
			<!-- 댓글 폼 끝 -->
			<!-- 댓글 목록 출력 시작 -->
			<c:if test="${count==0}">
				<div class="result-display">등록된 댓글이 없습니다.</div>
			</c:if>
			<c:if test="${count>0}">
				<ul>
					<c:forEach var="requestMainCommentBoard" items="${list}">
						<!-- ListAction의 ArrayList를 가져온다. -->
						<li>
							<span>작성자 : ${requestMainCommentBoard.id}</span>
						</li>
						<li>
							<span>작성일 : ${requestMainCommentBoard.mainComm_reg_date}</span>
						</li>
						<li>
							<span>(댓글 내용) ${requestMainCommentBoard.mainComm_content}</span>
						</li>
						<li>
						<!-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 -->
						<%-- c:if test="${user_num==requestMainCommentBoard.num}">-</c:if --%>
							<input type="button" value="수정" id='modify_btn'>
							<input type="button" value="삭제" id='delete_btn'>
							<script type="text/javascript">
								let delete_btn = document.getElementById('delete_btn');
								// 이벤트 연결
								delete_btn.onclick = function(){
									let choice = confirm('댓글을 삭제하시겠습니까?');
									if(choice){
										// replace : 히스토리 정보를 지워버린다.
										location.replace('mainCommenDelete.do?mainComment_num=${requestMainCommentBoard.mainComment_num}');
									}
								};
							</script>
						</li>
						<p>
					</c:forEach>
				</ul>
				<div class="align-center">
					${pagingHtml} <!-- 페이지 번호 -->
				</div>
			</c:if>
			<!-- 댓글 목록 출력 끝 -->
		</div> <!-- end of .content-main -->
	</div> <!-- end of .page-main -->
</body>
</html>