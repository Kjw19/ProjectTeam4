<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 글 상세정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeboard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/noticeBoard.comment.js"></script>

</head>
<body>
<div class="ntpage-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="ntdetail-main">
		<div class="detail-padding1">
			<h2>${noticeboard.noti_title}</h2>
		</div>
		<ul class="ib">
			<li>
				${noticeboard.id}<br>
				<fmt:formatDate value="${noticeboard.noti_reg_date}" pattern="yyyy.MM.dd"/> &nbsp;&nbsp;
				조회수 : ${noticeboard.noti_hit}
			</li>
		</ul>
		<c:if test="${!empty noticeboard.noti_filename}">
		<div class="detail-padding2">
			<img src="${pageContext.request.contextPath}/upload/${noticeboard.noti_filename}">
		</div>
		</c:if>
		<p class="detail-padding2">
			${noticeboard.noti_content}
		</p>
		<ul class="ntdetail-sub">
			<li>
				<%-- 좋아요 시작 --%>
				<img id="output_fav" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
		    	좋아요
		    	<span id="output_fcount"></span>
				<%-- 좋아요 끝 --%>
			</li>
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
			<li>
				<c:if test="${!empty noticeboard.noti_modify_date}">
					최근 수정일 : ${noticeboard.noti_modify_date}
				</c:if>
				<c:if test="${user_num == noticeboard.mem_num}">
				
				<input type="button" value="수정" onclick="location.href='updateForm.do?noti_num=${noticeboard.noti_num}'">
				<input type="button" value="삭제" id="delete_btn">
					<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						//이벤트 연결
						delete_btn.onclick=function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								location.replace('delete.do?noti_num=${noticeboard.noti_num}');
							}
						};
					</script>
				</c:if>
			</li>
		</ul>
		<!-- 댓글 시작  -->
		<div id="comment_div">
			<span class="comm-title">댓글 달기</span>
			<form id="comm_form">
				<input type="hidden" name="noti_num" 
				       value="${noticeboard.noti_num}" id="noti_num">
				<textarea rows="3" cols="50" name="comm_content" 
				  id="comm_content" class="comm-content"
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
		
		<!--  댓글 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!--  댓글 목록 출력 끝 -->
		<!--  댓글 끝 -->
		
		
	</div>
</div>
</body>
</html>