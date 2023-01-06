<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 문의 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>${inquiry.inqu_title}</h2>
	<ul class="detail-info">
		<li>
			<!-- 프로필 사진 삽입  없으면 코드 삭제 -->
		</li>
		<li>
			아이디 : ${inquiry.id}
		</li>
		<li>
			작성일 : ${inquiry.inqu_reg_date}
		</li>
		<li>
			${myInquiry.id}<br>
			답변 여부 : 수정해야함
		</li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${!empty inquiry.inqu_filename}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${inquiry.inqu_filename}" class="detail-img">
	</div>
	</c:if>
	<p>
		${inquiry.inqu_content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<ul class="detail-sub">
		<li>
			<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 + 관리자 불가능 --%>
			<c:if test="${user_num==inquiry.mem_num}">
			<input type="button" value="수정" onclick="location.href='inquiryUpdateForm.do?inquiry_num=${inquiry.inquiry_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				// 이벤트 연결
				delete_btn.onclick = function(){
					let choice = confirm('문의 내역을 삭제하시겠습니까?');
					if(choice){
						location.replace('delete.do?inquiry_num=${inquiry.inquiry_num}'); /* replace : 히스토리 정보를 지워버린다. */
					}
				};
			</script>
			</c:if>
		</li>
	</ul>
	</div> <!-- end of .content-main -->
</div> <!-- end of .page-main -->
</body>
</html>