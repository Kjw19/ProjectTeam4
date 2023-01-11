<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<div class="header">
	<header>
		<span class="logo"><a href="${pageContext.request.contextPath}/main/main.do">Buy-Me</a></span>
		<span id="main_menu">
			<ul>
				<li class="noti"><a href="${pageContext.request.contextPath}/noticeboard/list.do">공지사항</a></li>
				<li class="fund"><a href="#">펀딩게시판</a></li>
				<li class="req"><a href="${pageContext.request.contextPath}/requestBoard/list.do">커뮤니티</a></li>
			</ul>
		</span>	
		<span id="search_menu">
			<ul>
				<form id="search_form" action="#" method="get">
					<li>
						<input type="search" size="16" name="search_item"
					       	id="search_item" value="" placeholder="새로운 것을 찾으시나요?">
						<input type="submit" id="search_item_button" value="검색">
					</li>
				</form>
			</ul>
		</span>
		<span id="login_menu">
			<ul>
				<%-- 
				<c:if test="${!empty user_num && auth == 1}">
					<li>[<span>${mem_id}</span>]</li>
					<li><a href="#">로그아웃</a></li>
				</c:if>
				
				<c:if test="${!empty user_num && auth == 5}">
					<li>[<span>admin</span>]</li>
					<li><a href="#">로그아웃</a></li>
				</c:if>
				--%> 
				<c:if test="${!empty user_num}">
					<li class="myPage">[<span><a href="${pageContext.request.contextPath}/members/myPage.do">id=?</a></span>]</li>
					<li class="logout"><a href="${pageContext.request.contextPath}/members/logout.do">로그아웃</a></li>
				</c:if>
				<c:if test="${empty user_num}">
					<li class="mem_join"><a href="${pageContext.request.contextPath}/members/registerUserForm.do">회원가입</a></li>
					<li class="signin"><a href="${pageContext.request.contextPath}/members/loginForm.do">로그인</a></li>
				</c:if>
			</ul>
		</span>	
		<%-- 
		<span id="login_menu">
			<ul>
				<c:if test="${!empty user_num && auth == 1}">
				<li class="myPage"><a href="#">MY페이지</a></li>
				</c:if>
				<c:if test="${!empty user_num && auth == 5}">
				<li class="managerPage"><a href="#">관리자페이지</a></li>
				</c:if>

				<c:if test="${!empty user_num}">
				<li class="logout">
					[<span>${mem_id}</span>]
					<a href="#">로그아웃</a>
				</li>
				</c:if>
				<c:if test="${empty user_num}">
				<li class="mem_join"><a href="#">회원가입</a></li>
				<li class="signin"><a href="${pageContext.request.contextPath}/members/loginForm.do">로그인</a></li>
				</c:if>
			</ul>
		</span>	
		--%>
	</header>
</div>
<hr size="1" noshade="noshade" width="100%">
<!-- header 끝 -->






