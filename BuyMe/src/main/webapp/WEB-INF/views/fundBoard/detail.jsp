<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${board.fund_title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty board.fund_photo}">
				<img src="${pageContext.request.contextPath}/upload/${board.fund_photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty board.fund_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${board.id}<br>
				조회 : ${board.hit}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty board.fund_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${board.fund_filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${board.fund_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
		    <li>
		    	<%-- 좋아요 --%>
		    	<img id="output_like" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
		    	좋아요
		    	<span id="output_likecount"></span>
		    </li>
			<li>
				<c:if test="${!empty board.fund_modify_date}">
				최근 수정일 : ${board.fund_modify_date}
				</c:if>
				작성일 : ${board.fund_reg_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${user_num == board.mem_num}">
				<input type="button" value="수정" 
				onclick="location.href='updateForm.do?board_num=${board.fund_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?fund_num=${board.fund_num}');
						}
					};
				</script>
				</c:if>
			</li>
		</ul>
		<!-- 댓글 시작 -->
		<div id="comm_div">
			<span class="re-title">댓글 달기</span>
			<form id="comm_form">
				<input type="hidden" name="fund_num" 
				       value="${board.fund_num}" id="fund_num">
				<textarea rows="3" cols="50" name="comm_content" 
				  id="comm_content" class="commp-content"
				  <c:if test="${empty user_num}">disabled="disabled"</c:if>
				  ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>       
				<c:if test="${!empty user_num}">
				<div id="comm_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="comm_second" class="align-right">
					<input type="submit" value="전송">
				</div>
				</c:if>
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






