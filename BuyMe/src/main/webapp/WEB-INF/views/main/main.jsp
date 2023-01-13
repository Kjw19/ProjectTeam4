<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<body>
<div id="wrap">
	<section>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="slideshow-container">
	
	<div class="mySlides fade">
	  	<a href="#">
	  		<img src="../images/camera.jpg" style="width:100%; height:300px">
	  	</a>
	  <div class="text">새로운 카메라 펀딩!</div>
	</div>
	
	<div class="mySlides fade">
	  	<a href="#">
	  		<img src="../images/github.png" style="width:100%; height:300px">
	  	</a>
	  <div class="text">새로운 VCS 펀딩!</div>
	</div>
	
	<div class="mySlides fade">
	  	<a href="#">
	  		<img src="../images/laptop.png" style="width:100%; height:300px">
	  	</a>
	  <div class="text">새로운 Laptop 펀딩!</div>
	</div>
	
	</div>
	<br>
	
	<div style="text-align:center">
	  <span class="dot"></span> 
	  <span class="dot"></span> 
	  <span class="dot"></span> 
	</div>
	<div class="fund_menu">
		<nav>
			<ul>
				<li><a href="#"><img src="../images/tv.png"><br>가전</a></li>
				<li><a href="#"><img src="../images/fashion.png"><br>패션</a></li>
				<li><a href="#"><img src="../images/food.png"><br>식료품</a></li>
			</ul>
		</nav>
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
</section>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>

</body>
</html> 