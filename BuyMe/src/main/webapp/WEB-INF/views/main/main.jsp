<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="slideshow-container">
	
	<div class="mySlides fade">
	  	<div class="numbertext">1 / 3</div>
	  	<a href="#">
	  		<img src="../images/landscape.jpg" style="width:100%">
	  	</a>
	  <div class="text">첫번재 캡션</div>
	</div>
	
	<div class="mySlides fade">
	  <div class="numbertext">2 / 3</div>
	  	<a href="#">
	  		<img src="../images/landscape.jpg" style="width:100%">
	  	</a>
	  <div class="text">두번째 캡션</div>
	</div>
	
	<div class="mySlides fade">
	  <div class="numbertext">3 / 3</div>
	  	<a href="#">
	  		<img src="../images/landscape.jpg" style="width:100%">
	  	</a>
	  <div class="text">세번째 캡션</div>
	</div>
	
	</div>
	<br>
	
	<div style="text-align:center">
	  <span class="dot"></span> 
	  <span class="dot"></span> 
	  <span class="dot"></span> 
	</div>
<script>
let slideIndex = 0;
showSlides();

function showSlides() {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  let dots = document.getElementsByClassName("dot");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  slideIndex++;
  if (slideIndex > slides.length) {slideIndex = 1}    
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
  setTimeout(showSlides, 2000); // Change image every 2 seconds
}
</script>

</body>
</html> 