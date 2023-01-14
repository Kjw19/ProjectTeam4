<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fundBoard.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="sub-header">
	<ul>
		<li>
			<a href="detail.do?fund_num=${fund.fund_num}">펀딩게시글</a>
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
		<h2>${fund.fund_title}</h2>
		<div class="category_name">	
			<c:if test="${fund.category_num == 1}"><p>항목: 가전</p></c:if>	
			<c:if test="${fund.category_num == 2}"><p>항목: 패션</p></c:if>
			<c:if test="${fund.category_num == 3}"><p>항목: 푸드</p></c:if>
		</div>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty fund.fund_photo}">
				<img src="${pageContext.request.contextPath}/upload/${fund.fund_photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty fund.fund_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${fund.id}<br>
				조회 : ${fund.fund_hit}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty fund.fund_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${fund.fund_filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${fund.fund_content}
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
				<c:if test="${!empty fund.fund_modify_date}">
				최근 수정일 : ${fund.fund_modify_date}
				</c:if>
				작성일 : ${fund.fund_reg_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<input type="button" value="목록" 
				onclick="location.href='${pageContext.request.contextPath}/fundBoard/list.do'">
				<c:if test="${user_num == fund.mem_num}">
				<input type="button" value="수정" 
				onclick="location.href='${pageContext.request.contextPath}/fundBoard/upateForm.do?fund_num=${fund.fund_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<input type="button" value="목록"
			  		onclick="location.href='${pageContext.request.contextPath}/fundBoard/list.do'">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?fund_num=${fund.fund_num}');
						}
					};
				</script>
				</c:if>
			</li>
		</ul>
	</div>
</div>
</body>
</html>






