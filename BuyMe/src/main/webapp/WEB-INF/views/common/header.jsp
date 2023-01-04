<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<div class="header">
	<header>
		<h1 class="logo">Buy-Me</h1>
		<nav>
			<ul>
				<li class="noti"><a href="#">공지게시판</a></li>
				<li class="fund"><a href="#">펀딩게시판</a></li>
				<li class="req"><a href="#">문의게시판</a></li>
				<form id="search_form" action="#" method="get">
					<li>
						<input type="search" size="16" name="search_item"
					       	id="search_item" value="">
					</li>
					<li>
						<input type="submit" id="search_item_button" value="검색">
					</li>
				</form>
				<c:if test="${!empty mem_num && auth == 1}">
				<li class="myPage"><a href="#">MY페이지</a></li>
				</c:if>
				<c:if test="${!empty mem_num && auth == 5}">
				<li class="managerPage"><a href="#">관리자페이지</a></li>
				</c:if>

				<c:if test="${!empty mem_num}">
				<li class="logout">
					[<span>${mem_id}</span>]
					<a href="#">로그아웃</a>
				</li>
				</c:if>
				<c:if test="${empty mem_num}">
				<li class="mem_join"><a href="#">회원가입</a></li>
				<li class="signin"><a href="#">로그인</a></li>
				</c:if>
			</ul>	
		</nav>
	</header>
</div>
<!-- header 끝 -->





