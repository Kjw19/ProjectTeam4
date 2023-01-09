<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${auth == 1}">    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>회원 정보</h2>
		<div class="result-display">
			<div class="align-center">
				정지된 회원 ID입니다.<p>
				<input type="button" value="홈으로"
				 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</div>
	</div>
</div>
</body>
</html>
	</c:when>
	<%-- auth가 1또는 5가 아닌 경우 --%>
	<c:otherwise>
	<script>
		alert('아이디 또는 비밀번호가 불일치합니다.');
		history.go(-1);
	</script>
	</c:otherwise>
</c:choose>	
	
	