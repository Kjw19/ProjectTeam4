<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>응원 게시판 - 메인글 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>게시판 글 상세</h2>
	<ul>
		<li>메인글내용 : ${cheerBoardVO.cheer_content}</li>
	</ul>
	<hr size="1" noshade width="100%">
	<p>
		${boardVO.content}
	</p>
	<hr size="1" noshade width="100%">
	<div class="align-right">
		작성일 : ${cheerBoardVO.cheer_reg_date}
		<input type="button" value="수정" onclick="location.href='cheerModifyForm.do?num=${cheerBoardVO.cheer_num}'">
		<input type="button" value="삭제" onclick="location.href='cheerDeleteForm.do?num=${cheerBoardVO.cheer_num}'">
		<input type="button" value="목록" onclick="location.href='cheerList.do'">        
	</div>
</div>
</body>
</html>